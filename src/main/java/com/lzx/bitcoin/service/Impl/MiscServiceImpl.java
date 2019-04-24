package com.lzx.bitcoin.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzx.bitcoin.api.Bitcoin;
import com.lzx.bitcoin.api.BitcoinJsonRpcClient;
import com.lzx.bitcoin.bean.Block;
import com.lzx.bitcoin.bean.Transaction;
import com.lzx.bitcoin.bean.TransactionDetail;
import com.lzx.bitcoin.enumeration.TransactionDetailType;
import com.lzx.bitcoin.mapper.BlockMapper;
import com.lzx.bitcoin.mapper.TransactionDetailMapper;
import com.lzx.bitcoin.mapper.TransactionMapper;
import com.lzx.bitcoin.service.MiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Logger;

@Service
public class MiscServiceImpl implements MiscService {

    @Autowired
    private Bitcoin bitcoin;
    @Autowired
    private BlockMapper blockMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TransactionDetailMapper transactionDetailMapper;
    @Autowired
    private BitcoinJsonRpcClient bitcoinJsonRpcClient;


    @Async
    @Override
    public void importFromHeight(Integer blockHeight, Boolean isClean) {

    }

    @Async
    @Override
    public void importFromHash(String blockHash, Boolean isClean) throws Throwable {
        if (isClean){
            blockMapper.truncate();
            transactionMapper.truncate();
            transactionDetailMapper.truncate();
        }

        String temphash = blockHash;

        while (temphash!=null && !temphash.isEmpty()){
            JSONObject blockOrigin = bitcoin.getblockbyhash(temphash);
            Block block = new Block();
            block.setBlockhash(blockOrigin.getString("hash"));
            block.setBlockchainId(2);
            block.setHeight(blockOrigin.getInteger("height"));
            Long time = blockOrigin.getLong("time");
            Date date = new Date(time * 1000);
            block.setTime(date);
            JSONArray txes = blockOrigin.getJSONArray("tx");
            for (int i = 0; i < txes.size(); i++) {
                importTx(txes.getJSONObject(i),temphash,date);
            }
            block.setTxSize(txes.size());
            block.setSizeOnDisk(blockOrigin.getLong("size"));
            block.setDifficulty(blockOrigin.getDouble("difficulty"));
            block.setPrevBlockhash(blockOrigin.getString("previousblockhash"));
            block.setNextBlockhash(blockOrigin.getString("nextblockhash"));
            block.setMerkleRoot(blockOrigin.getString("merkleroot"));
            blockMapper.insert(block);
            temphash = blockOrigin.getString("nextblockhash");
        }
    }

    public void importTx(JSONObject tx, String blockhash, Date time) throws Throwable {
        Transaction transaction = new Transaction();
        String txid=tx.getString("txid");
        transaction.setTxid(txid);
        transaction.setTxhash(tx.getString("hash"));
        transaction.setBlockhash(blockhash);
        transaction.setSize(tx.getLong("size"));
        transaction.setWeight(tx.getInteger("weight"));
        transaction.setTime(time);
        transactionMapper.insert(transaction);

        JSONArray vouts = tx.getJSONArray("vout");
        for (int i = 0; i < vouts.size(); i++) {
            importVoutDetail(vouts.getJSONObject(i),txid);
        }

        JSONArray vins = tx.getJSONArray("vin");

        //todo vin0 coinbase tx

        for (int i = 1; i < vins.size(); i++) {
            importVinDetail(vins.getJSONObject(i),txid);
        }


    }

    private void importVinDetail(JSONObject vout, String txid) {
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setTxid(txid);
        JSONObject scriptPubKey = vout.getJSONObject("scriptPubKey");
        JSONArray addresses = scriptPubKey.getJSONArray("addresses");
        //todo check whether sync addresses
        if (addresses != null && !addresses.isEmpty()){
            String address = addresses.getString(0);
            transactionDetail.setAddress(address);
        }
        Double amount = vout.getDouble("value");
        transactionDetail.setAmount(amount);
        transactionDetail.setType((byte) TransactionDetailType.Receive.ordinal());

        transactionDetailMapper.insert(transactionDetail);
    }


    public void importVoutDetail(JSONObject vin, String txidOrigin) throws Throwable {
        String txid = vin.getString("txid");
        Integer vout = vin.getInteger("vout");

        JSONObject rawTransaxtion = bitcoinJsonRpcClient.getRawTransaxtion(txid);
        JSONArray vouts = rawTransaxtion.getJSONArray("vout");
        JSONObject jsonObject = vouts.getJSONObject(vout);
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setTxid(txid);
        JSONObject scriptPubKey = jsonObject.getJSONObject("scriptPubKey");
        JSONArray addresses = scriptPubKey.getJSONArray("addresses");
        if (addresses != null && !addresses.isEmpty()){
            String address = addresses.getString(0);

            transactionDetail.setAddress(address);
        }
        Double amount = jsonObject.getDouble("value");
        transactionDetail.setAmount(amount);
        transactionDetail.setType((byte) TransactionDetailType.Receive.ordinal());

        transactionDetailMapper.insert(transactionDetail);
    }




}
