package cn.boccfc.test.bytecode.bytebuddy;

import java.util.Random;

public class BizMethod {

    public String queryUserInfo(String uid, String token) throws
            InterruptedException {
        Thread.sleep(new Random().nextInt(500));
        return "监控方法执行时间";
    }

}
