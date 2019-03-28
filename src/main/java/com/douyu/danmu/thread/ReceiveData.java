package com.douyu.danmu.thread;

import com.douyu.danmu.util.TcpSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program sendmessage
 * @description:
 * @author: luhx
 * @create: 2018/09/26 15:57
 */
public class ReceiveData implements Runnable {
    private TcpSocketClient tcpSocketClient;
    private Logger logger = LoggerFactory.getLogger(ReceiveData.class);

    public ReceiveData(TcpSocketClient tcpSocketClient) {
        this.tcpSocketClient = tcpSocketClient;
    }

    @Override
    public void run() {
        while (Danmu.runState) {
            try {
                if (Danmu.runState) {
                    ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                    InputStream inputStream = tcpSocketClient.getSocket().getInputStream();
                    byte[] msg = new byte[1024000];
                    int line = 0;
                    line = inputStream.read(msg);
                    logger.info("msgLength = " + msg.length + " line = " + line);
                    if (line > 0) {
                        byteOutput.write(msg, 0, line);
                        byte[] receiveMsg = byteOutput.toByteArray();
                        tcpSocketClient.getDouyuProtocolMessage().receivedMessageContent(receiveMsg);
                    }
                }
            } catch (Exception e) {
                Danmu.runState = false;
                logger.info("Receive IO or NullPoint error!");
                e.printStackTrace();
            }
        }
    }
}
