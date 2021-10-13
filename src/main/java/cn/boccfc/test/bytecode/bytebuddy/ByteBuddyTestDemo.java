package cn.boccfc.test.bytecode.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class ByteBuddyTestDemo {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        testRunMethodDelegation();



    }

    /**
     * 创建类信息
     */
    public static void generateClass(){
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("cn.boccfc.bytecode.bytebuddy.TestGenerateClass")
                .make();
        outputClazz(dynamicType.getBytes());
    }

    /**
     * 创建main方法
     */
    public static void generateMainMethod(){
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("cn.boccfc.bytecode.bytebuddy.TestGenerateClass")
                .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)
                .withParameter(String[].class, "args")
                .intercept(FixedValue.value("Hello World!"))
                .make();
        outputClazz(dynamicType.getBytes());
    }

    /**
     * 委托函数使用
     */
    public static void generateWithMethodDelegation(){
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("cn.boccfc.bytecode.bytebuddy.TestMethodDelegation")
                .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)
                .withParameter(String[].class, "args")
                .intercept(MethodDelegation.to(ByteBuddyHelloWorld.class))
                .make();
        outputClazz(dynamicType.getBytes());
    }

    /**
     * 运行测算
     */
    public static void testRunMethodDelegation() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("cn.boccfc.bytecode.bytebuddy.TestMethodDelegation")
                .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)
                .withParameter(String[].class, "args")
                .intercept(MethodDelegation.to(ByteBuddyHelloWorld.class))
                .make();

        //类加载
        Class<?> clazz = dynamicType.load(ByteBuddyHelloWorld.class.getClassLoader()).getLoaded();
        //反射调用
        clazz.getMethod("main",String[].class)
                .invoke(clazz.newInstance(),(Object)new String[1]);

    }



    /**
     * 输出字节码到文件存储
     * @param bytes
     */
    private static void outputClazz(byte[] bytes) {
        FileOutputStream out = null;
        try {
            String pathName = ByteBuddyTestDemo.class.getResource("/").getPath() +
                    "TestMethodDelegation.class";
            out = new FileOutputStream(new File(pathName));
            System.out.println("类输出路径：" + pathName);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
