package com.zjdex.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author lindj
 * @create 2018/10/8
 * @desc 功能描述
 **/
public class LogUtil {

    private static Logger logger =  null;
    public static Logger getLogger(){
        if (null == logger){
            String className = Thread.currentThread().getStackTrace()[1].getClassName();
            logger = LoggerFactory.getLogger(className);
            logger.info("调用者类名"+className);
        }
        return logger;
    }

    public static void main(String[] args){
        LogUtil.getLogger().debug("rqwer");
    }
}
