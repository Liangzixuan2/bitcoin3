package com.lzx.bitcoin.mapper;

import com.lzx.bitcoin.bean.Blockchain;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.Type;

public interface BlockchainMapper {
    int deleteByPrimaryKey(Integer blockchainId);

    int insert(Blockchain record);

    int insertSelective(Blockchain record);

    Blockchain selectByPrimaryKey(Integer blockchainId);

    int updateByPrimaryKeySelective(Blockchain record);

    int updateByPrimaryKey(Blockchain record);

    Integer getblockchainId(@Param("name") String name, @Param("type")String type);
}