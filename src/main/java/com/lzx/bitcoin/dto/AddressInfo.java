package com.lzx.bitcoin.dto;

import java.util.List;

public class AddressInfo {
    //地址
    private String address;
    //hash
    private String hash160;
    //交易条数
    private Integer txSize;
    //收到的总金额
    private Double receiveAmount;
    //剩余金额
    private Double finalBalance;
    //该地址的所有交易记录
    private List<TransactionInBlockDTO> transactions;

    public Double getFinalBalance() {
        return finalBalance;
    }

    public List<TransactionInBlockDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionInBlockDTO> transactions) {
        this.transactions = transactions;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHash160() {
        return hash160;
    }

    public void setHash160(String hash160) {
        this.hash160 = hash160;
    }

    public Integer getTxSize() {
        return txSize;
    }

    public void setTxSize(Integer txSize) {
        this.txSize = txSize;
    }

    public Double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Double getFinalBalance(double v) {
        return finalBalance;
    }

    public void setFinalBalance(Double finalBalance) {
        this.finalBalance = finalBalance;
    }
}
