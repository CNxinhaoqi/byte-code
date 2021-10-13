package cn.boccfc.test.intercept;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class MethodCostTime {
    @RuntimeType
    public static Object intercept(@Origin Method method, @SuperCall
            Callable<?> callable, @AllArguments Object[] args) throws Exception {
        long start = System.currentTimeMillis();
        try {
            //原有函数执行
            return callable.call();
        } finally {
            System.out.println("方法" + method.getName()+"，入参类型："+method.getParameterTypes());
            System.out.println("方法" + method.getName()+"，入参内容："+ Arrays.toString(args));
            System.out.println("方法"+method.getName()+"耗时： " +
                    (System.currentTimeMillis() - start) + "ms");
        }
    }
}
