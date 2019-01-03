package com.douyu.danmu.util;

import com.alibaba.fastjson.JSONObject;
import com.douyu.Application;
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
public class ZeroMQUtil implements Runnable {

    private final ZMQ.Socket push;
    private static final Logger logger = LoggerFactory.getLogger(ZeroMQUtil.class);

    public ZeroMQUtil() {
        ZMQ.Context context = ZMQ.context(1);
        push = context.socket(ZMQ.PUSH);
        push.bind("tcp://*:10002");
    }

    private void sendZeroMQMsg(String msg) {
        logger.info("sendZeroMQMsg " + msg);
        push.send(msg.getBytes());
    }

    @Override
    public void run() {
        while (true) {
            if (Application.concurrentLinkedQueue.size() > 0) {
                try {
                    sendZeroMQMsg(JSONObject.toJSONString(Application.concurrentLinkedQueue.take()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.info(e.getMessage());
                }
            }
        }
    }
}


