package com.douyu.danmu.util;

import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;

/**
 * @program danmu
 * @description:ZeroMQUtil
 * @author: luhx
 * @create: 2018/12/27 16:11
 */
@Component("zeroMQUtil")
public class ZeroMQUtil {

    private final ZMQ.Socket push;

    public ZeroMQUtil() {
        ZMQ.Context context = ZMQ.context(1);
        push = context.socket(ZMQ.PUSH);
        push.bind("tcp://127.0.0.1:10002");
    }

    public void sendZeroMQMsg(String msg) {
//        push.send(msg.getBytes());
    }

}


