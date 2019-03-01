package com.zjdex.framework.service.impl;

import com.zjdex.framework.mapper.SysConstantMapper;
import com.zjdex.framework.modle.SysConstant;
import com.zjdex.framework.modle.SysConstantExample;
import com.zjdex.framework.service.SysConstantService;
import com.zjdex.framework.util.data.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
        SysConstantExample example = new SysConstantExample();
        example.createCriteria().andNameEqualTo(name);
        List<SysConstant> list = this.sysConstantMapper.selectByExample(example);
        if(!StringUtil.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

}