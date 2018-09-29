package com.douyu.danmu.thread;

import com.douyu.danmu.util.TcpSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program sendmessage
 * @description: 维持心跳信息
 * @author: luhx
 * @create: 2018/09/26 15:16
 */
public class KeepaliveSender implements Runnable {
    private TcpSocketClient tcpSocketClient;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KeepaliveSender(TcpSocketClient tcpSocketClient) {
        this.tcpSocketClient = tcpSocketClient;
    }

    @Override
    public void run() {
        Boolean runState = true;
        while (runState) {
            this.tcpSocketClient.sendData("type@=mrkl/");
            try {
                Thread.sleep(40000);
            } catch (Exception e) {
//                runState = false;
                logger.info("Sleep interrupted!");
                e.printStackTrace();
            }
        }
    }
}
