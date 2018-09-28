package com.douyu.danmu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @program sendmessage
 * @description: Socket客户端
 * @author: luhx
 * @create: 2018/09/26 15:19
 */
public class TcpSocketClient {
    private Logger logger = LoggerFactory.getLogger(TcpSocketClient.class);
    private InetAddress host;
    private int port;
    private Socket socket;
    private DouyuProtocolMessage douyuProtocolMessage;

    public TcpSocketClient(String server, int port) {
        try {
            this.host = InetAddress.getByName(server);
            this.port = port;
            logger.info("Connect to Server {}:{}.", host.getHostAddress(), port);
            this.socket = new Socket(this.host, this.port);
            logger.info("Open Socket successfully");
        } catch (IOException e) {
            logger.info("Open socket fail");
            logger.info(e.getMessage());
        }
        douyuProtocolMessage = new DouyuProtocolMessage();
    }

    public Socket getSocket() {
        return socket;
    }

    public void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    public void sendData(String content) {
        byte[] messageContent = null;
        try {
            messageContent = douyuProtocolMessage.sendMessageContent(content);
        } catch (IOException e1) {
            logger.info(e1.getMessage());
        }
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(messageContent);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    public DouyuProtocolMessage getDouyuProtocolMessage() {
        return douyuProtocolMessage;
    }
}
