package com.douyu;

import com.douyu.danmu.base.DanmuBase;
import com.douyu.danmu.service.DanmuService;
import com.douyu.danmu.thread.Danmu;
import net.dongliu.requests.Requests;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program danmu
 * @description:
 * @author: luhx
 * @create: 2018/09/27 11:49
 */
public class Application {
    public static void main(String[] args) {
        Danmu danmu = new Danmu();
        danmu.run();
    }
}
