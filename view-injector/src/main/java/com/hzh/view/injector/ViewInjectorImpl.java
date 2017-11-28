package com.hzh.view.injector;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzh.view.injector.anno.ContentView;
import com.hzh.view.injector.anno.OnClick;
import com.hzh.view.injector.anno.OnLongClick;
import com.hzh.view.injector.anno.ViewInject;
import com.hzh.view.injector.inter.ViewInjector;
import com.hzh.view.injector.util.ViewFinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;

/**
 * Package: com.hzh.view.injector
 * FileName: ViewInjectorIml
 * Date: on 2017/11/23  下午4:05
 * Auther: zihe
 * Descirbe:View注入实现类
 * Email: hezihao@linghit.com
 */

public class ViewInjectorImpl implements ViewInjector {
    private static final String TAG = ViewInjectorImpl.class.getSimpleName();
    //Android原生的基类不注入，因为无法修改，而且方法和变量巨多，遍历到时忽略掉。
    private static final HashSet<Class<?>> IGNORED_TYPE = new HashSet<Class<?>>();

    static {
        IGNORED_TYPE.add(Object.class);
        IGNORED_TYPE.add(Activity.class);
        IGNORED_TYPE.add(android.app.Fragment.class);
        try {
            IGNORED_TYPE.add(Class.forName("android.support.v4.app.Fragment"));
            IGNORED_TYPE.add(Class.forName("android.support.v4.app.FragmentActivity"));
        } catch (Throwable ignored) {
        }
    }

    private ViewInjectorImpl() {
    }

    private static final class SingletonHolder {
        private static final ViewInjectorImpl instance = new ViewInjectorImpl();
    }

    public static ViewInjectorImpl getInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public void inject(View view) {
        injectObject(view, view.getClass(), new ViewFinder(view));
    }

    @Override
    public void inject(Activity activity) {
        //获取ContentView的注解
        Class<?> handlerType = activity.getClass();
        try {
            ContentView contentView = findContentView(handlerType);
            if (contentView != null) {
                int viewId = contentView.value();
                if (viewId > 0) {
                    Method setContentViewMethod = handlerType.getMethod("setContentView", int.class);
                    setContentViewMethod.invoke(activity, viewId);
                }
            }
        } catch (Throwable ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
        injectObject(activity, handlerType, new ViewFinder(activity));
    }

    @Override
    public void inject(Object target, View view) {
        injectObject(target, target.getClass(), new ViewFinder(view));
    }

    @Override
    public View inject(Object fragment, LayoutInflater inflater, ViewGroup container) {
        View layout = null;
        Class<?> clazz = fragment.getClass();
        try {
            ContentView contentView = findContentView(clazz);
            if (contentView != null) {
                //取出布局id
                int layoutId = contentView.value();
                if (layoutId > 0) {
                    layout = inflater.inflate(layoutId, container, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        injectObject(fragment, clazz, new ViewFinder(layout));
        return layout;
    }

    /**
     * 查找ContentView注解
     *
     * @param clazz 类的Class
     * @return ContentView注解
     */
    private static ContentView findContentView(Class<?> clazz) {
        if (clazz == null || IGNORED_TYPE.contains(clazz)) {
            return null;
        }
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        //如果没有，递归去父类找
        if (contentView == null) {
            return findContentView(clazz.getSuperclass());
        }
        return contentView;
    }

    /**
     * 最后调入的inject方法
     *
     * @param target
     * @param finder View查找者
     */
    public void injectObject(final Object target, Class<?> handlerType, ViewFinder finder) {
        if (target == null || IGNORED_TYPE.contains(handlerType)) {
            return;
        }
        injectObject(target, handlerType.getSuperclass(), finder);

        //绑定View控件
        try {
            //1.获取所有的成员变量
            Class<?> clazz = target.getClass();
            //2.遍历所有成员变量，找到使用了ViewInject注解的成员变量（所有类型，包括private）
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Class<?> fieldType = field.getType();
                //不注入静态变量、final变量，基本数据类型，数组类型变量
                if (Modifier.isStatic(field.getModifiers())
                        || Modifier.isFinal(field.getModifiers())
                        || fieldType.isPrimitive()
                        || fieldType.isArray()) {
                    continue;
                }
                //设置允许访问
                field.setAccessible(true);
                ViewInject annotation = field.getAnnotation(ViewInject.class);
                if (annotation != null) {
                    //3.将使用了注解的成员变量上标记的id值取出
                    int id = annotation.value();
                    //4.调用activity的findViewById查找控件
                    if (id > 0) {
                        View view = finder.findViewById(id);
                        if (view != null) {
                            //5.将控件设置给成员变量
                            field.set(target, view);
                        }
                    } else {
                        throw new RuntimeException("ViewInject annotation must have view value");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //绑定onClick和onLongClick
        //1.获取所有的方法
        Class<?> clazz = target.getClass();
        //2.遍历所有的方法
        Method[] methods = clazz.getDeclaredMethods();
        //3.获取标记了OnClick注解的方法
        for (final Method method : methods) {
            //不注入静态方法
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }

            OnClick onClickAnnotation = method.getAnnotation(OnClick.class);
            if (onClickAnnotation != null) {
                //4.取出id，查找View
                int id = onClickAnnotation.value();
                View view = finder.findViewById(id);
                //5.给View绑定onClick，点击时执行
                if (view != null) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Class<?>[] parameterTypes = method.getParameterTypes();
                                int paramsCount = parameterTypes.length;
                                if (paramsCount == 0) {
                                    method.invoke(target, new Object[]{});
                                } else {
                                    method.invoke(target, v);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            //长按事件
            OnLongClick onLongAnnotation = method.getAnnotation(OnLongClick.class);
            if (onLongAnnotation != null) {
                int id = onLongAnnotation.value();
                View view = finder.findViewById(id);
                if (view != null) {
                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            try {
                                Class<?>[] parameterTypes = method.getParameterTypes();
                                int paramsCount = parameterTypes.length;
                                Object object;
                                if (paramsCount == 0) {
                                    object = method.invoke(target, new Object[]{});
                                } else {
                                    object = method.invoke(target, new Object[]{v});
                                }
                                return (boolean) object;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return true;
                        }
                    });
                }
            }
        }
    }
}