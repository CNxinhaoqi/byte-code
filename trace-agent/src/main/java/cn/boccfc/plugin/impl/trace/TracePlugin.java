package cn.boccfc.plugin.impl.trace;

import cn.boccfc.plugin.IPlugin;
import cn.boccfc.plugin.InterceptPoint;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

public class TracePlugin implements IPlugin {
    @Override
    public String name() {
        return "trace";
    }

    @Override
    public InterceptPoint[] buildInterceptPoint() {
        return new InterceptPoint[]{
                new InterceptPoint() {
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {
                        return ElementMatchers.nameStartsWith("cn.boccfc.test");
                    }

                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.isMethod()
                                .and(ElementMatchers.any())
                                .and(ElementMatchers.not(ElementMatchers.nameStartsWith("main")));
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return TraceAdvice.class;
    }
}
