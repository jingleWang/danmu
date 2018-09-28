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
public class ReceiveData implements Runnable{
    private TcpSocketClient tcpSocketClient;
    private Logger logger = LoggerFactory.getLogger(ReceiveData.class);

    public ReceiveData(TcpSocketClient tcpSocketClient) {
        this.tcpSocketClient = tcpSocketClient;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                InputStream inputStream = tcpSocketClient.getSocket().getInputStream();

                byte[] msg = new byte[1024000];
                int line = 0;
                line = inputStream.read(msg);
                byteOutput.write(msg, 0, line);
                byte[] receiveMsg = byteOutput.toByteArray();
                tcpSocketClient.getDouyuProtocolMessage().receivedMessageContent(receiveMsg);
            } catch (Exception e) {
                logger.info("Receive IO or NullPoint error!");
                logger.info(e.getMessage());
            }
        }
    }
}
