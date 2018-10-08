package com.douyu.danmu.service.impl;

import com.douyu.danmu.base.DanmuBase;
import com.douyu.danmu.dao.DanmuDao;
import com.douyu.danmu.service.DanmuService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Map;

/**
 * @program danmu
 * @description: 弹幕Service
 * @author: luhx
 * @create: 2018/09/27 17:07
 */
@Service
public class DanmuServiceImpl implements DanmuService {

    @Autowired
    private DanmuDao danmuDao;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void insertMsg(Map<String, String> msgMap) {
        DanmuBase danmuBase = new DanmuBase();
        danmuBase.setUserId(msgMap.get("uid"));
        danmuBase.setUserName(msgMap.get("nn"));
        danmuBase.setUserLevel(msgMap.get("level"));
        danmuBase.setFansName(msgMap.get("bnn"));
        danmuBase.setFansLevel(msgMap.get("bl"));
        danmuBase.setMsg(msgMap.get("txt"));
        danmuBase.setMsgColor(getMessageColor(msgMap.get("col")));
        danmuBase.setMsgNoble(msgMap.get("nc"));
        danmuBase.setCreateTime(new Date());
        String id = DigestUtils.md5DigestAsHex((danmuBase.getUserId() + danmuBase.getCreateTime() + danmuBase.getMsg()).getBytes());
        danmuBase.setMsgID(id);
        logger.info(danmuBase.toString());
        try {
            danmuDao.insertMsg(danmuBase);
        } catch (Exception e) {
            logger.info("message = " + e.getMessage());
        }
    }

    private static String getMessageColor(String colorMark) {
        if (colorMark == null) {
            return "#4C4C4C";
        }
        String colorStr = null;
        int colorIndex = Integer.parseInt(colorMark);
        switch (colorIndex) {
            case 1:
                colorStr = "#EB3223";
                break;
            case 2:
                colorStr = "#3A88E9";
                break;
            case 3:
                colorStr = "#8DC55D";
                break;
            case 4:
                colorStr = "#F08533";
                break;
            case 5:
                colorStr = "#8E48EB";
                break;
            case 6:
                colorStr = "#EE74B2";
                break;
        }
        return colorStr;
    }
}
