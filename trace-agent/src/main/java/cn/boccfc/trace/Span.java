package cn.boccfc.trace;

import java.util.Date;

public class Span {

    private String traceId;  //链路ID
    private Date enterTime; //方法进入时间

    public Span(){}

    public Span(String traceId){
        this.traceId = traceId;
        this.enterTime = new Date();
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }
}
