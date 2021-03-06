package com.douyu.danmu.thread;

import com.douyu.danmu.util.TcpSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program danmu
 * @description: 弹幕启动
 * @author: luhx
 * @create: 2018/09/27 17:22
 */
public class Danmu {
    private Logger logger = LoggerFactory.getLogger(Danmu.class);
    public static volatile boolean runState = false;
    private String roomID = "265438";
    //    private String roomID = "4466101";
    private TcpSocketClient tcpSocketClient;
    private String danmu_server = "openbarrage.douyutv.com";

    private Integer danmu_port = 8601;

    private void sendKeepalive(KeepaliveSender keepaliveSender) {
        Thread thread = new Thread(keepaliveSender);
        thread.setName("DanmuServerKeepaliveThread");
        thread.start();
        logger.info("心跳线程启动成功！");
    }

    private void receiveData(ReceiveData receiveData) {
        Thread thread = new Thread(receiveData);
        thread.setName("DanmuServerReceiveThread");
        thread.start();
        logger.info("消息接收线程启动成功！");
    }

    public void run() throws InterruptedException {
        while (true) {
            try {
                if (Danmu.runState == false) {
                    if (tcpSocketClient != null && tcpSocketClient.getSocket() != null) {
                        tcpSocketClient.closeSocket();
                        tcpSocketClient = null;
                        logger.info("Danmu restart succefully!");
                    }
                    tcpSocketClient = new TcpSocketClient(danmu_server, danmu_port);
                    while (tcpSocketClient.openSocket() == false) {
                        Thread.sleep(3000);
                    }
                    Danmu.runState = true;
                    KeepaliveSender keepaliveSender = new KeepaliveSender(tcpSocketClient);
                    ReceiveData receiveData = new ReceiveData(tcpSocketClient);
                    receiveData(receiveData);
                    tcpSocketClient.sendData("type@=loginreq/roomid@=" + roomID + "/");
                    tcpSocketClient.sendData("type@=joingroup/rid@=" + roomID + "/gid@=-9999/");
                    sendKeepalive(keepaliveSender);
                    logger.info("Danmu start succefully!");
                }
                Thread.sleep(85000);
            } catch (Exception e) {
                Danmu.runState = false;
                Thread.sleep(5000);
            }
        }
    }
}
