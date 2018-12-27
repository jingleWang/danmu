package com.douyu.danmu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ZeroMQUtil.class);

    public ZeroMQUtil() {
        ZMQ.Context context = ZMQ.context(1);
        push = context.socket(ZMQ.PUB);
        push.bind("tcp://*:10002");
    }

    public void sendZeroMQMsg(String msg) {
        logger.info("sendZeroMQMsg " + msg);
        push.send(msg.getBytes());
    }

}


