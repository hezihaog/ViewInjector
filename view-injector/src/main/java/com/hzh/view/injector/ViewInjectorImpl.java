package com.hzh.view.injector;

import android.app.Activity;
import android.view.View;

import com.hzh.view.injector.anno.OnClick;
import com.hzh.view.injector.anno.OnLongClick;
import com.hzh.view.injector.anno.ViewInject;
import com.hzh.view.injector.inter.ViewInjector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Package: com.hzh.view.injector
 * FileName: ViewInjectorIml
 * Date: on 2017/11/23  下午4:05
 * Auther: zihe
 * Descirbe:View注入实现类
 * Email: hezihao@linghit.com
 */

public class ViewInjectorImpl implements ViewInjector {
    private ViewInjectorImpl() {
    }

    private static final class SingletonHolder {
        private static final ViewInjectorImpl instance = new ViewInjectorImpl();
    }

    public static ViewInjectorImpl getInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public void inject(Activity activity) {
        if (activity == null) {
            return;
        }
        bindViewId(activity);
        bindViewEvent(activity);
    }

    /**
     * 绑定View的Id
     */
    private void bindViewId(Activity activity) {
        try {
            //1.获取所有的成员变量
            Class<? extends Activity> clazz = activity.getClass();
            //2.遍历所有成员变量，找到使用了ViewInject注解的成员变量（所有类型，包括private）
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                //设置允许访问
                field.setAccessible(true);
                ViewInject annotation = field.getAnnotation(ViewInject.class);
                if (annotation != null) {
                    //3.将使用了注解的成员变量上标记的id值取出
                    int id = annotation.value();
                    //4.调用activity的findViewById查找控件
                    if (id > 0) {
                        View view = activity.findViewById(id);
                        //5.将控件设置给成员变量
                        field.set(activity, view);
                    } else {
                        throw new RuntimeException("ViewInject annotation must have view value");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定View的OnClick事件
     *
     * @param activity
     */
    private void bindViewEvent(final Activity activity) {
        //1.获取所有的方法
        Class<? extends Activity> clazz = activity.getClass();
        //2.遍历所有的方法
        Method[] methods = clazz.getDeclaredMethods();
        //3.获取标记了OnClick注解的方法
        for (final Method method : methods) {
            OnClick onClickAnnotation = method.getAnnotation(OnClick.class);
            if (onClickAnnotation != null) {
                //4.取出id，查找View
                int id = onClickAnnotation.value();
                View view = activity.findViewById(id);
                //5.给View绑定onClick，点击时执行
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            int paramsCount = parameterTypes.length;
                            if (paramsCount == 0) {
                                method.invoke(activity, new Object[]{});
                            } else {
                                method.invoke(activity, v);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            //长按事件
            OnLongClick onLongAnnotation = method.getAnnotation(OnLongClick.class);
            if (onLongAnnotation != null) {
                int id = onLongAnnotation.value();
                View view = activity.findViewById(id);
                if (view != null) {
                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            try {
                                Class<?>[] parameterTypes = method.getParameterTypes();
                                int paramsCount = parameterTypes.length;
                                Object o;
                                if (paramsCount == 0) {
                                    o = method.invoke(activity, new Object[]{});
                                } else {
                                    o = method.invoke(activity, new Object[]{v});
                                }
                                return (boolean) o;
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
