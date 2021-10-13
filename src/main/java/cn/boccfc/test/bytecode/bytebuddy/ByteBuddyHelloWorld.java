package cn.boccfc.test.bytecode.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class ByteBuddyHelloWorld {
    public static void main(String[] args) {
        System.out.println("ByteBuddyHelloWorld = " + args);
    }

    public void test() throws IllegalAccessException, InstantiationException {
        String helloWorld = new ByteBuddy()
                .subclass(Object.class)
                .method(named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .toString();
        System.out.println(helloWorld); // Hello World!
    }

}
