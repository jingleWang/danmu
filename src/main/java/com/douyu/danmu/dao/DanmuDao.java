package com.douyu.danmu.dao;

import com.douyu.danmu.base.DanmuBase;
import org.springframework.stereotype.Repository;

/**
 * @program danmu
 * @description: 弹幕存储dao层
 * @author: luhx
 * @create: 2018/09/27 14:14
 */
public interface DanmuDao {
    public void insertMsg(DanmuBase danmuBase);
}
