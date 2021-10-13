package cn.boccfc.trace;

public class TraceContext {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public static void clear(){
        threadLocal.remove();
    }

    public static String getTraceId(){
        return threadLocal.get();
    }

    public static void setTraceId(String traceId){
        threadLocal.set(traceId);
    }

}
