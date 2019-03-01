package com.zjdex.framework.service;


import com.zjdex.framework.modle.SysConstant;

/**
 * @author lindj
 * @date 2019/2/26
 * @description
 */
@FunctionalInterface
public interface SysConstantService{

    /**
     * 根据名称获取配置信息
     *
     * @return SysConstant
     */
    SysConstant getContent(String name);
}
