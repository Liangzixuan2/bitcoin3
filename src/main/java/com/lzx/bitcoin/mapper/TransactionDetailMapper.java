package com.lzx.bitcoin.mapper;

import com.lzx.bitcoin.bean.TransactionDetail;
import com.lzx.bitcoin.dto.TransactionInBlockDTO;
import com.lzx.bitcoin.dto.TxDetailInTxInfo;
import feign.Param;

import java.util.List;

public interface TransactionDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TransactionDetail record);

    int insertSelective(TransactionDetail record);

    TransactionDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TransactionDetail record);

    int updateByPrimaryKey(TransactionDetail record);

    void truncate();

    List<TransactionInBlockDTO> selectbyadress(@Param("address") String address);

    List<TxDetailInTxInfo> get(Object txid);
}