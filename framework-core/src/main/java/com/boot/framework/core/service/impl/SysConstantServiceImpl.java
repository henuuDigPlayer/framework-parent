package com.boot.framework.core.service.impl;

import com.boot.framework.core.mapper.SysConstantMapper;
import com.boot.framework.core.model.SysConstant;
import com.boot.framework.core.service.SysConstantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author lindj
 * @date 2019/2/26
 * @description
 */
@Service
public class SysConstantServiceImpl implements SysConstantService {


    @Autowired
    private SysConstantMapper sysConstantMapper;

    /**
     * 根据名称获取配置信息
     *
     * @return SysConstant
     */
    @Override
    public SysConstant getContent(String name) {
        return this.sysConstantMapper.selectByName(name);
    }

}