package cn.boccfc.test.bytecode.bytebuddy;

import cn.boccfc.test.bytecode.bytebuddy.abstractimpl.Repository;
import cn.boccfc.test.bytecode.bytebuddy.abstractimpl.UserRepositoryInterceptor;
import cn.boccfc.test.bytecode.bytebuddy.annotation.RpcGatewayClazz;
import cn.boccfc.test.bytecode.bytebuddy.annotation.RpcGatewayMethod;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class TestByteByddy {

    @Test
    public void testMonitorMethodExecuteTime() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(BizMethod.class)
                .method(ElementMatchers.named("queryUserInfo"))
                .intercept(MethodDelegation.to(MonitorMethodExecuteTime.class))
                .make();

        //类加载
        Class<?> clazz = dynamicType.load(ByteBuddyHelloWorld.class.getClassLoader()).getLoaded();
        //反射调用
        clazz.getMethod("queryUserInfo",String.class,String.class)
                .invoke(clazz.newInstance(),"10001", "Adhl9dkl");
    }

    @Test
    public void testAbstractClassImpl() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                //复杂类型的泛型注解
                .subclass(TypeDescription.Generic.Builder.parameterizedType(Repository.class,String.class).build())
                .name(Repository.class.getPackage().getName().concat(".").concat("UserRepository"))
                //匹配处理方法
                .method(ElementMatchers.nameMatches("queryData"))
                //交给委托函数
                .intercept(MethodDelegation.to(UserRepositoryInterceptor.class))
                //添加自定义方法注解
                .annotateMethod(AnnotationDescription.Builder
                        .ofType(RpcGatewayMethod.class)
                        .define("methodName","queryData")
                        .define("methodDesc", "查询数据").build())
                //添加自定义类注解
                .annotateType(AnnotationDescription.Builder
                        .ofType(RpcGatewayClazz.class)
                        .define("alias","dataapi")
                        .define("clazzDesc","查询数据信息")
                        .define("timeOut",350L).build())
                .make();

        //输出类信息到目标文件夹下
        dynamicType.saveIn(new File(TestByteByddy.class.getResource("/").getPath()));

    }

    @Test
    public void outputDiyAnnotationInfo() throws ClassNotFoundException, NoSuchMethodException {
        Class<Repository<String>> repositoryClass = (Class<Repository<String>>) Class.forName("cn.boccfc.bytecode.bytebuddy.abstractimpl.UserRepository");

        //获取类注解
        RpcGatewayClazz rpcGatewayClazz = repositoryClass.getAnnotation(RpcGatewayClazz.class);
        System.out.println("alias = " + rpcGatewayClazz.alias());
        System.out.println("clazzDesc = " + rpcGatewayClazz.clazzDesc());
        System.out.println("timeOut = " + rpcGatewayClazz.timeOut());

        RpcGatewayMethod rpcGatewayMethod = repositoryClass.getMethod("queryData",int.class).getAnnotation(RpcGatewayMethod.class);
        System.out.println("methodDesc = " + rpcGatewayMethod.methodDesc());
        System.out.println("methodName = " + rpcGatewayMethod.methodName());

    }

    @Test
    public void runAbstractImplClass() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<Repository<String>> repositoryClass = (Class<Repository<String>>) Class.forName("cn.boccfc.bytecode.bytebuddy.abstractimpl.UserRepository");
        Repository<String> repository = repositoryClass.newInstance();
        System.out.println("repository.queryData(1000) = " + repository.queryData(1000));
    }


}
