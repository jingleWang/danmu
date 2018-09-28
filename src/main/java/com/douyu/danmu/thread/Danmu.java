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
    private TcpSocketClient tcpSocketClient;
    private KeepaliveSender keepaliveSender;
    private ReceiveData receiveData;
    private Thread receiveThread = null;
    private Thread aliveThread = null;

    private String roomID = "265438";

    private String danmu_server = "openbarrage.douyutv.com";

    private Integer danmu_port = 8601;

    private Thread sendKeepalive() {
        Thread thread = new Thread(keepaliveSender);
        thread.setName("DanmuServerKeepaliveThread");
        thread.start();
        return thread;
    }

    private Thread receiveData() {
        Thread thread = new Thread(receiveData);
        thread.setName("DanmuServerReceiveThread");
        thread.start();
        return thread;
    }

    public void run() {
        tcpSocketClient = new TcpSocketClient(danmu_server, danmu_port);
        keepaliveSender = new KeepaliveSender(tcpSocketClient);
        receiveData = new ReceiveData(tcpSocketClient);
        receiveThread = receiveData();
        tcpSocketClient.sendData("type@=loginreq/roomid@=" + roomID + "/");
        tcpSocketClient.sendData("type@=joingroup/rid@=" + roomID + "/gid@=-9999/");
        aliveThread = sendKeepalive();
        logger.info("Danmu start succefully!");
        while (true) {
            if (receiveThread != null && !receiveThread.isAlive()) {
                receiveThread = receiveData();
                logger.info("receiveThread restart succefully!");
            } else if (aliveThread != null && !aliveThread.isAlive()) {
                aliveThread = sendKeepalive();
                logger.info("aliveThread restart succefully!");
            }

        }

    }
}
