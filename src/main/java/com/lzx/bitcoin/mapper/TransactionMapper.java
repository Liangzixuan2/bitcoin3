package com.lzx.bitcoin.mapper;

import com.lzx.bitcoin.bean.Transaction;
import com.lzx.bitcoin.dto.ImportStateDTO;
import com.lzx.bitcoin.dto.TransactionInfoDTO;
import com.lzx.bitcoin.dto.TransactionListDTO;

import java.util.List;

public interface TransactionMapper {
    int deleteByPrimaryKey(String txid);

    int insert(Transaction record);

    int insertSelective(Transaction record);

    Transaction selectByPrimaryKey(String txid);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);

    void truncate();

    List<ImportStateDTO> getImportState();

    List<TransactionListDTO> getRecentTransactionsById(Integer blockchainId);

    TransactionInfoDTO getTransactionInfoByTxid(String txid);

    TransactionInfoDTO getTransactionInfoByTxhash(String txhash);
}