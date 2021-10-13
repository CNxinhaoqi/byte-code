package cn.boccfc.test.bytecode.bytebuddy.abstractimpl;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.reflect.Method;

public class UserRepositoryInterceptor {

    public static String intercept(@Origin Method method, @AllArguments
            Object[] arguments) {
        return "抽象方法实现" +
                arguments[0];
    }
}
