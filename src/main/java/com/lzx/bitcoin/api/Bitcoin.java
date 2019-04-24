package com.lzx.bitcoin.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="bitcoin",url = "http://localhost:18332")
public interface Bitcoin {

    //查询区块链
    @GetMapping("/rest/chaininfo.json")
    JSONPObject getchaininfo();

    //查询区块信息
    @GetMapping("/rest/block/{hash}.json")
    JSONObject getblockbyhash(@PathVariable("hash") String hash);

    //查询无交易的区块信息
    @GetMapping("/rest/block/notxdetails/{hash}.json")
    JSONObject getnotxblock(@PathVariable("hash") String hash);

    //获取交易信息
    @GetMapping("/rest/tx/{txhash}.json")
    JSONObject getTransaction(@PathVariable("txhash") String txhash);

    //获取此区块的前count个交易数据
    @GetMapping("/rest/headers/{count}/{blockhash}.json")
    JSONArray getBlockHeaders(@PathVariable("count") Integer count, @PathVariable("blockhash") String blockhash);

    //查询内存池
    @GetMapping("/rest/mempool/info.json")
    JSONObject getMempoolInfo();

    //返回TX mempool中的事务
    @GetMapping("/rest/mempool/contents.json")
    JSONObject getMempoolContents();


}
