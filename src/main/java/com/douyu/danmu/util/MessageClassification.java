package com.douyu.danmu.util;

import com.douyu.danmu.service.DanmuService;

import net.dongliu.requests.Requests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    private static Logger logger = LoggerFactory.getLogger(MessageClassification.class);
    private static Integer roomState = 0;

    public static void classification(Map<String, String> msgMap) {

        String type = msgMap.get("type");
        if (type.equals("chatmsg")) {
            chatmsgHandle(msgMap);
        } else if (type.equals("uenter")) {
            uenterHandle(msgMap);
        } else if (type.equals("rss")) {
            rssHandle(msgMap);
        }

    }

    private static void chatmsgHandle(Map<String, String> msgMap) {
        if (msgMap.containsKey("cid")) {
            danmuService.insertMsg(msgMap);
        }

    }

    private static void uenterHandle(Map<String, String> msgMap) {
        if (msgMap.get("nn").equals("刘飞儿faye") && roomState == 0) {
            logger.info("url = http://127.0.0.1:9000/message/intoroom");
            String url = "http://127.0.0.1:9000/message/intoroom";
            Requests.get(url).send();
        }
    }

    private static void rssHandle(Map<String, String> msgMap) {
        if (msgMap.get("ss").equals("1")) {
            logger.info("url = http://127.0.0.1:9000/message/startlive" );
            roomState = 1;
            String url = "http://127.0.0:19000/message/startlive";
            Requests.get(url).send();
        } else {
            logger.info("url = http://127.0.0.1:9000/message/stoplive" );
            roomState = 0;
            String url = "http://127.0.0.1:9000/message/stoplive";
            Requests.get(url).send();
        }
    }

}
