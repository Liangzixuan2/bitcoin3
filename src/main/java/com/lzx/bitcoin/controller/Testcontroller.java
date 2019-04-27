package com.lzx.bitcoin.controller;

import com.alibaba.fastjson.JSONObject;
import com.lzx.bitcoin.api.Bitcoin;
import com.lzx.bitcoin.api.BitcoinJsonRpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/test")
public class Testcontroller {

    @Autowired
    private BitcoinJsonRpcClient bitcoinJsonRpcClient;


    @Autowired
    private Bitcoin bitcoin;

    @GetMapping("getBestBlockhash")
    public String getBestBlockhash() throws Throwable {
        String bestBlockhash = bitcoinJsonRpcClient.getBestBlockhash();
        return bestBlockhash;
    }

    @GetMapping("getBalance")
    public Double getBalance(String address) throws Throwable {
        Double bestBlockhash = bitcoinJsonRpcClient.getBalance(address);
        return bestBlockhash;
    }


    @GetMapping("getnotxblock")
    public JSONObject getnotxblock(String hash) throws Throwable {
        JSONObject getnotxblock = bitcoin.getnotxblock(hash);
        return getnotxblock;
    }


    @GetMapping("getBlockHashByHeight")
    public String getBlockHashByHeight(Integer blockHeight) throws Throwable {
        String blockHashByHeight = bitcoinJsonRpcClient.getBlockHashByHeight(blockHeight);
        return blockHashByHeight;
    }


    @GetMapping("getTransaction")
    public JSONObject getTransaction(String txhash) throws Throwable {
        JSONObject transaction = bitcoin.getTransaction(txhash);
        return transaction;
    }


}
