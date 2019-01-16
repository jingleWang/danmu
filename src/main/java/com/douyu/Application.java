package com.douyu;

import com.douyu.danmu.base.DanmuBase;
import com.douyu.danmu.service.DanmuService;
import com.douyu.danmu.thread.Danmu;
import com.douyu.danmu.util.ZeroMQUtil;
import net.dongliu.requests.Requests;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program danmu
 * @description:
 * @author: luhx
 * @create: 2018/09/27 11:49
 */
public class Application {
//    public static LinkedBlockingQueue<Map<String, String>> concurrentLinkedQueue = new LinkedBlockingQueue<Map<String, String>>();

    public static void main(String[] args) throws InterruptedException {
//        Thread thread = new Thread(new ZeroMQUtil());
//        thread.start();
        Danmu danmu = new Danmu();
        danmu.run();
    }
}
