package com.lzx.bitcoin.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.lzx.bitcoin.bean.Transaction;
import com.lzx.bitcoin.dto.AddressInfo;
import com.lzx.bitcoin.dto.TransactionInBlockDTO;
import com.lzx.bitcoin.dto.TxDetailInTxInfo;
import com.lzx.bitcoin.mapper.TransactionDetailMapper;
import com.lzx.bitcoin.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/address")
public class AddressController {
    
    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionDetailMapper transactionDetailMapper;

    @GetMapping("/getAddressInfo")
    public AddressInfo getAddressInfo(@RequestParam String address){

        return null;
    }

    //通过地址查询该地址的所有信息
    @GetMapping("/getAddressTransactions")
    public AddressInfo getAddressTransactions(@RequestParam String address,
                                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum){
        List<String> txids = transactionDetailMapper.gettxid(address);
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setAddress(address);
        addressInfo.setTxSize(txids.size());
        ArrayList<TransactionInBlockDTO> list = new ArrayList<>();
        Double intzong=0.0;
        int count = 0;
        for (String txid : txids) {
            Double intsum=0.0;
            Transaction transaction = transactionMapper.getTransaction(txid);
            TransactionInBlockDTO transactionInBlockDTO = new TransactionInBlockDTO();
            transactionInBlockDTO.setSize(transaction.getSize());
            transactionInBlockDTO.setTxid(transaction.getTxid());
            transactionInBlockDTO.setTxhash(transaction.getTxhash());
            transactionInBlockDTO.setTime(transaction.getTime());
            List<TxDetailInTxInfo> txDetailInTxInfo = transactionDetailMapper.get(txid);
            transactionInBlockDTO.setTxDetailInTxInfos(txDetailInTxInfo);
            for (TxDetailInTxInfo detailInTxInfo : txDetailInTxInfo) {
                if (detailInTxInfo.getType()==2){
                    intsum+=detailInTxInfo.getAmount();
                }
            }
            list.add(transactionInBlockDTO);
            intzong+=intsum;
            count++;
        }
        System.out.println(count+"`````````````````````````");
        addressInfo.setReceiveAmount(intzong);
        addressInfo.setTransactions(list);
        return addressInfo;
    }

}

