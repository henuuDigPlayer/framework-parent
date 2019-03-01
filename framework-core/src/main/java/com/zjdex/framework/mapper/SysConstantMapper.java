package com.zjdex.framework.mapper;

import com.zjdex.framework.modle.SysConstant;
import com.zjdex.framework.modle.SysConstantExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: null
 * @date: 2019-02-26 18:13:13
 * @description:  
 */
public interface SysConstantMapper {
    /**
     * countByExample
     * 
     * @param example SysConstantExample 
     * @return long 
     */
    long countByExample(SysConstantExample example);

    /**
     * deleteByExample
     * 
     * @param example SysConstantExample 
     * @return int 
     */
    int deleteByExample(SysConstantExample example);

    /**
     * deleteByPrimaryKey
     * 
     * @param id Integer 
     * @return int 
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert
     * 
     * @param record SysConstant 
     * @return int 
     */
    int insert(SysConstant record);

    /**
     * insertSelective
     * 
     * @param record SysConstant 
     * @return int 
     */
    int insertSelective(SysConstant record);

    /**
     * selectByExample
     * 
     * @param example SysConstantExample 
     * @return List<SysConstant> 
     */
    List<SysConstant> selectByExample(SysConstantExample example);

    /**
     * selectByPrimaryKey
     * 
     * @param id Integer 
     * @return SysConstant 
     */
    SysConstant selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     * 
     * @param record SysConstant 
     * @param example SysConstantExample 
     * @return int 
     */
    int updateByExampleSelective(@Param("record") SysConstant record, @Param("example") SysConstantExample example);

    /**
     * updateByExample
     * 
     * @param record SysConstant 
     * @param example SysConstantExample 
     * @return int 
     */
    int updateByExample(@Param("record") SysConstant record, @Param("example") SysConstantExample example);

    /**
     * updateByPrimaryKeySelective
     * 
     * @param record SysConstant 
     * @return int 
     */
    int updateByPrimaryKeySelective(SysConstant record);

    /**
     * updateByPrimaryKey
     * 
     * @param record SysConstant 
     * @return int 
     */
    int updateByPrimaryKey(SysConstant record);
}