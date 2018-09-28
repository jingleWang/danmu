package com.douyu.danmu.service;

import com.douyu.danmu.base.DanmuBase;
import com.douyu.danmu.dao.DanmuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program danmu
 * @description: 弹幕数据处理
 * @author: luhx
 * @create: 2018/09/27 14:14
 */
public interface DanmuService {
    public void insertMsg(Map<String, String> msgMap);
}
