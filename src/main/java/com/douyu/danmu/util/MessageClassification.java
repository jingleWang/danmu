package com.douyu.danmu.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.douyu.danmu.service.DanmuService;

import net.dongliu.requests.Requests;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @program danmu
 * @description: 弹幕分类处理
 * @author: luhx
 * @create: 2018/09/27 17:42
 */
public class MessageClassification {

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
    private static DanmuService danmuService = (DanmuService) applicationContext.getBean("danmuServiceImpl");
    private static ZeroMQUtil zeroMQUtil = (ZeroMQUtil) applicationContext.getBean("zeroMQUtil");
    private static Logger logger = LoggerFactory.getLogger(MessageClassification.class);
    public static Integer roomState = 0;

    public static void classification(Map<String, String> msgMap) {
        try {
            if (msgMap.containsKey("type")) {
                String type = msgMap.get("type");
                if (type.equals("chatmsg")) {
                    chatmsgHandle(msgMap);
                } else if (type.equals("uenter")) {
                    uenterHandle(msgMap);
                } else if (type.equals("rss")) {
                    rssHandle(msgMap);

                } else if (type.equals("dgb")) {
                    dgbHandle(msgMap);
                } else if (type.equals("blab")) {
                    blabHandle(msgMap);
                }
            }
        } catch (Exception e) {
            logger.info("数据分发出现异常！！！");
            e.printStackTrace();
        }

    }

    private static void blabHandle(Map<String, String> msgMap) {
        zeroMQUtil.sendZeroMQMsg("恭喜" + msgMap.get("nn") + "粉丝牌升级到" + msgMap.get("bl") + "级！！！");
    }

    //赠送礼物
    private static void dgbHandle(Map<String, String> msgMap) {
        if (msgMap.get("hits").equals(msgMap.get("gfcnt"))) {
            if (roomState == 0)
                zeroMQUtil.sendZeroMQMsg("感谢" + msgMap.get("nn") + "赠送的礼物！！");
            else {
                if (msgMap.containsKey("bg")) {
                    zeroMQUtil.sendZeroMQMsg("感谢" + msgMap.get("nn") + "赠送的礼物！！");
                }
            }
        }
    }

    private static void chatmsgHandle(Map<String, String> msgMap) {
        logger.info("chatmsgHandle");
        if (msgMap.containsKey("cid")) {
            danmuService.insertMsg(msgMap);
        }

    }

    private static void uenterHandle(Map<String, String> msgMap) {
        logger.info("uenterHandle");
        if (msgMap.get("nn").equals("刘飞儿faye") && roomState == 0) {
            logger.info("url = http://127.0.0.1:9000/message/intoroom");
            String url = "http://127.0.0.1:9000/message/intoroom";
            zeroMQUtil.sendZeroMQMsg("欢迎小仙女进入直播间！！！");
            sendGetRequest(url);
        } else {
            if (roomState == 0)
                zeroMQUtil.sendZeroMQMsg("欢迎" + msgMap.get("nn") + "进入直播间！您的光临使本直播间蓬荜生辉！");
        }
    }

    private static void rssHandle(Map<String, String> msgMap) {
        if (msgMap.get("ss").equals("1")) {
            logger.info("url = http://127.0.0.1:9000/message/startlive");
            roomState = 1;
            String url = "http://127.0.0.1:9000/message/startlive";
            zeroMQUtil.sendZeroMQMsg("#refresh");
            sendGetRequest(url);
        } else {
            logger.info("url = http://127.0.0.1:9000/message/stoplive");
            roomState = 0;
            String url = "http://127.0.0.1:9000/message/stoplive";
            sendGetRequest(url);
        }
    }

    private static void sendGetRequest(String url) {
        int retry = 2;
        while (retry > 0) {
            try {
                JSONObject jb = JSON.parseObject(Requests.get(url).timeout(120000, 120000).send().readToText());
                logger.info(jb.toString());
                if (StringUtils.equals(jb.getString("code"), "1000")) {
                    break;
                }
                retry--;
            } catch (Exception e) {
                retry--;
                logger.info(url + "请求出现异常");
                logger.info(e.getMessage());
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
