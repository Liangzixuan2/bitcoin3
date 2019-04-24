package com.lzx.bitcoin.controller;

import com.lzx.bitcoin.dto.TransactionInfoDTO;
import com.lzx.bitcoin.dto.TransactionListDTO;
import com.lzx.bitcoin.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionMapper transactionMapper;

    @GetMapping("/getRecentTransactionsById")
    public List<TransactionListDTO> getRecentTransactionsById(@RequestParam Integer blockchainId){
        List<TransactionListDTO> list = transactionMapper.getRecentTransactionsById(blockchainId);
        return list;
    }

    @GetMapping("/getRecentTransactionsByNameType")
    public List<TransactionListDTO> getRecentTransactionsByNameType(@RequestParam String name,
                                                                    @RequestParam String type){
        return null;
    }

    @GetMapping("/getTransactionInfoByTxid")
    public TransactionInfoDTO getTransactionInfoByTxid(@RequestParam String txid){
        TransactionInfoDTO transactionInfoDTO = transactionMapper.getTransactionInfoByTxid(txid);
        return transactionInfoDTO;
    }

    @GetMapping("/getTransactionInfoByTxhash")
    public TransactionInfoDTO getTransactionInfoByTxhash(@RequestParam String txhash){
        TransactionInfoDTO transactionInfoDTO = transactionMapper.getTransactionInfoByTxhash(txhash);
        return transactionInfoDTO;
    }

}
