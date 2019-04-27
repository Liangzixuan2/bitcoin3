package com.lzx.bitcoin.dto;

import java.util.Date;
import java.util.List;

public class TransactionInBlockDTO {
    private String txid;

    private String txhash;

    private Long size;

    private Date time;
    
    private  Double zong;

    private List<TxDetailInTxInfo> txDetailInTxInfos;

    public Double getZong() {
        return zong;
    }

    public void setZong(Double zong) {
        this.zong = zong;
    }

    public List<TxDetailInTxInfo> getTxDetailInTxInfos() {
        return txDetailInTxInfos;
    }

    public void setTxDetailInTxInfos(List<TxDetailInTxInfo> txDetailInTxInfos) {
        this.txDetailInTxInfos = txDetailInTxInfos;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getTxhash() {
        return txhash;
    }

    public void setTxhash(String txhash) {
        this.txhash = txhash;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}


