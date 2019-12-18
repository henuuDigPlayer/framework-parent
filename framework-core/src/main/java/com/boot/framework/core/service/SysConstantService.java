package com.boot.framework.core.service;


import com.boot.framework.core.model.SysConstant;

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
