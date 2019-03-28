package com.douyu.danmu.util;

import com.douyu.danmu.thread.Danmu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

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
    private Socket socket = null;
    private DouyuProtocolMessage douyuProtocolMessage;

    public TcpSocketClient(String server, int port) {
        try {
            this.host = InetAddress.getByName(server);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = port;
        douyuProtocolMessage = new DouyuProtocolMessage();
    }

    public Boolean openSocket() {
        try {
            logger.info("Connect to Server {}:{}.", host.getHostAddress(), port);

            SocketAddress socketAddress = new InetSocketAddress(this.host, this.port);
            this.socket = new Socket();
            this.socket.connect(socketAddress,10000);
            logger.info("Open Socket successfully");
            return true;
        } catch (IOException e) {
            logger.info("Open socket fail");
            logger.info(e.getMessage());
        }
        return false;
    }


    public Socket getSocket() {
        return socket;
    }

    public void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            logger.info("closeSocket: " + e.getMessage());
        }
    }

    public void sendData(String content) {
        logger.info("sendData: " + content);
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
            logger.info("sendData: " + e.getMessage());
            Danmu.runState = false;
        }
    }

    public DouyuProtocolMessage getDouyuProtocolMessage() {
        return douyuProtocolMessage;
    }
}
