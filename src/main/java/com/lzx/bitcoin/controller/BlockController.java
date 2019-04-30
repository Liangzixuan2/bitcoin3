package com.lzx.bitcoin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzx.bitcoin.api.Bitcoin;
import com.lzx.bitcoin.api.BitcoinJsonRpcClient;
import com.lzx.bitcoin.bean.Transaction;
import com.lzx.bitcoin.dto.BlockDetailDTO;
import com.lzx.bitcoin.dto.BlockListDTO;
import com.lzx.bitcoin.dto.TransactionInBlockDTO;
import com.lzx.bitcoin.dto.TxDetailInTxInfo;
import com.lzx.bitcoin.mapper.BlockMapper;
import com.lzx.bitcoin.mapper.BlockchainMapper;
import com.lzx.bitcoin.mapper.TransactionDetailMapper;
import com.lzx.bitcoin.mapper.TransactionMapper;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/block")
public class BlockController {

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private BlockchainMapper blockchainMapper;

    @Autowired
    private BitcoinJsonRpcClient bitcoinJsonRpcClient;

    @Autowired
    private Bitcoin bitcoin;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionDetailMapper transactionDetailMapper;


    //通过blockchainId获取所有块信息
    @GetMapping("/getRecentBlocks")
    public PageInfo<BlockListDTO> getRecentBlocks(@RequestParam(defaultValue = "1")Integer pageNUm) throws Throwable {
//        String hash = bitcoinJsonRpcClient.getBestBlockhash();
//        //创建list容器
//        List<BlockListDTO> blockListDtos=new LinkedList<>();
//        String tempBlockhash=hash;
//
//        for (int i=0;i<5;i++){
//            JSONObject block = bitcoin.getnotxblock(tempBlockhash);
//            BlockListDTO blockListDTO = new BlockListDTO();
//            blockListDTO.setHeight(block.getInteger("height"));
//            Long time = block.getLong("time");
//            Date date = new Date(time * 1000);
//            blockListDTO.setTime(date);
//            blockListDTO.setTxSize(block.getJSONArray("tx").size());
//            blockListDTO.setSizeOnDisk(block.getLong("size"));
//            blockListDtos.add(blockListDTO);
//            tempBlockhash = block.getString("previousblockhash");
//        }
        PageHelper.startPage(pageNUm,5);
        Page<BlockListDTO> recentBlocks = blockMapper.getRecentBlocks();
        PageInfo<BlockListDTO> blockListDTOPageInfo = recentBlocks.toPageInfo();
        return blockListDTOPageInfo;
    }

    //通过name和type获取当前块信息
    @GetMapping("/getRecentBlocksByNameType")
    public List<BlockListDTO> getRecentBlocksByNameType(@RequestParam String name,@RequestParam String type){
        Integer blockchainid = blockchainMapper.getblockchainId(name,type);
        List<BlockListDTO> BlockListDTO = blockMapper.getRecentBlocksById(blockchainid);
        return BlockListDTO;
    }

    //通过blockhash查询块信息
    @GetMapping("/getBlockDetailByHash")
    public BlockDetailDTO getBlockDetailByHash(@RequestParam String hash){
        JSONObject block = bitcoin.getnotxblock(hash);
        BlockDetailDTO blockDetailDTO = new BlockDetailDTO();
        blockDetailDTO.setBlockhash(block.getString("hash"));
        blockDetailDTO.setDifficulty(block.getDouble("difficulty"));
        blockDetailDTO.setHeight(block.getInteger("height"));
        blockDetailDTO.setMerkleRoot(block.getString("merkleroot"));
        blockDetailDTO.setNextBlockhash(block.getString("nextblockhash"));
        blockDetailDTO.setPrevBlockhash(block.getString("previousblockhash"));
        blockDetailDTO.setSizeOnDisk(block.getLong("size"));
        Long time = block.getLong("time");
        Date date = new Date(time * 1000);
        blockDetailDTO.setTime(date);
        blockDetailDTO.setTxSize(block.getInteger("nTx"));
        JSONArray tx = block.getJSONArray("tx");
        ArrayList<TransactionInBlockDTO> list = new ArrayList<>();
        Double outzong=0.0;
        for (Object txid : tx) {
            Double outsum=0.0;
            Transaction transaction = transactionMapper.getTransaction((String) txid);
            TransactionInBlockDTO transactionInBlockDTO = new TransactionInBlockDTO();
            transactionInBlockDTO.setSize(transaction.getSize());
            transactionInBlockDTO.setTxid(transaction.getTxid());
            transactionInBlockDTO.setTxhash(transaction.getTxhash());
            transactionInBlockDTO.setTime(transaction.getTime());
            List<TxDetailInTxInfo> txDetailInTxInfo = transactionDetailMapper.get((String)txid);
            transactionInBlockDTO.setTxDetailInTxInfos(txDetailInTxInfo);
            for (TxDetailInTxInfo detailInTxInfo : txDetailInTxInfo) {
                if (detailInTxInfo.getType()==1){
                    outsum+=detailInTxInfo.getAmount();
                }
            }
            list.add(transactionInBlockDTO);
            outzong+=outsum;
        }
        blockDetailDTO.setOutputTotal(outzong);
        blockDetailDTO.setTransactions(list);
        return blockDetailDTO;
    }

    //通过blockheight查询块信息
    @GetMapping("/getBlockDetailByHeight")
    public BlockDetailDTO getBlockDetailByHeight(@RequestParam Integer blockheight) throws Throwable {
        String hash = bitcoinJsonRpcClient.getBlockHashByHeight(blockheight);
        BlockDetailDTO blockDetailDTO = this.getBlockDetailByHash(hash);
        return blockDetailDTO;
    }
}
