package cn.boccfc.trace;

import java.util.Stack;

public class TraceManager {

    private static final ThreadLocal<Stack<Span>> stackThreadLocal = new ThreadLocal<Stack<Span>>();

    private static Span createSpan() {
        Stack<Span> stack = stackThreadLocal.get();
        if (stack == null) {
            stack = new Stack<Span>();
            stackThreadLocal.set(stack);
        }
        String traceId;
        if (stack.isEmpty()) {
            traceId = TraceContext.getTraceId();
            if (traceId == null) {
                traceId = "nvl";
                TraceContext.setTraceId(traceId);
            }
        } else {
            Span span = stack.peek();
            traceId = span.getTraceId();
            TraceContext.setTraceId(traceId);
        }
        return new Span(traceId);
    }

    public static Span createEntrySpan() {
        Span span = createSpan();
        Stack<Span> stack = stackThreadLocal.get();
        stack.push(span);
        return span;
    }


    public static Span getExitSpan() {
        Stack<Span> stack = stackThreadLocal.get();
        if (stack == null || stack.isEmpty()) {
            TraceContext.clear();
            return null;
        }
        return stack.pop();
    }

    public static Span getCurrentSpan() {
        Stack<Span> stack = stackThreadLocal.get();
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }


}
