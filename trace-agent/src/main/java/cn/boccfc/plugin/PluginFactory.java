package cn.boccfc.plugin;

import cn.boccfc.plugin.impl.jvm.JvmPlugin;
import cn.boccfc.plugin.impl.trace.TracePlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * 插件工厂
 */
public class PluginFactory {

    public static List<IPlugin> pluginGroup = new ArrayList<IPlugin>();

    static {
        //链路监控
        pluginGroup.add(new TracePlugin());
        //Jvm监控
        pluginGroup.add(new JvmPlugin());
    }

}
