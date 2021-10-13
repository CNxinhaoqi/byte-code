package cn.boccfc.plugin;

/**
 * 监控组件
 */
public interface IPlugin {

    //名称
    String name();

    //监控点
    InterceptPoint[] buildInterceptPoint();

    //拦截器类
    Class adviceClass();

}
