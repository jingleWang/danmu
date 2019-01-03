package com.douyu.danmu.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class DouyuProtocolMessage {
    private int[] messageLength;
    private int[] code;
    private int[] end;
    private ByteArrayOutputStream byteArrayOutputStream;
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static Logger logger = LoggerFactory.getLogger(DouyuProtocolMessage.class);

    public DouyuProtocolMessage() {
        byteArrayOutputStream = new ByteArrayOutputStream();
    }

    public byte[] sendMessageContent(String content) throws IOException {
        this.messageLength = new int[]{calcMessageLength(content), 0x00, 0x00, 0x00};
        this.code = new int[]{0xb1, 0x02, 0x00, 0x00};
        this.end = new int[]{0x00};

        byteArrayOutputStream.reset();
        for (int i : messageLength)
            byteArrayOutputStream.write(i);
        for (int i : messageLength)
            byteArrayOutputStream.write(i);
        for (int i : code)
            byteArrayOutputStream.write(i);
        byteArrayOutputStream.write(content.getBytes("UTF-8"));
        for (int i : end)
            byteArrayOutputStream.write(i);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Be careful about the length of content, because Chinese's char is not 1 length,
     * so you should encode it first.
     *
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     */
    private int calcMessageLength(String content) throws UnsupportedEncodingException {
        return 4 + 4 + (content == null ? 0 : content.getBytes("UTF-8").length) + 1;
    }

    public void receivedMessageContent(byte[] receiveMsg) {
        // Copy from stackoverflow
        try {
            String message = bytesToHex(receiveMsg);

            List<Map<String, String>> messageList = hexStringToStringList(message);
            for (Map<String, String> msgMap : messageList) {
                MessageClassification.classification(msgMap);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info("消息处理出现问题！");
        }

    }


    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static String transferStr(String str) {
        str = str.replace("@S", "/");
        str = str.replace("@A", "@");
        return str;
    }

    private static List<Map<String, String>> hexStringToStringList(String s) {
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        if (s == null || s.equals("")) {
            return resultList;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        for (String firstStr : s.split("\0")) {
            if (firstStr.split("type").length == 2) {
                Map<String, String> map = new LinkedHashMap<String, String>();
                for (String secondStr : firstStr.split("/")) {
                    String str[] = secondStr.split("@=");
                    if (str.length > 1) {
                        String key = transferStr(str[0]);
                        String value = transferStr(str[1]);
                        map.put(key, value);
                    }
                }
                if (map.size() > 0)
                    resultList.add(map);
            }
        }
        return resultList;
    }
}
