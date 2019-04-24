package com.lzx.bitcoin.controller;

import com.lzx.bitcoin.dto.AddressInfo;
import com.lzx.bitcoin.dto.TransactionInBlockDTO;
import com.lzx.bitcoin.mapper.TransactionDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private TransactionDetailMapper transactionDetailMapper;

    @GetMapping("/getAddressInfo")
    public AddressInfo getAddressInfo(@RequestParam String address){

        return null;
    }

    @GetMapping("/getAddressTransactions")
    public List<TransactionInBlockDTO> getAddressTransactions(@RequestParam String address,
                                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum){
        //List<TransactionInBlockDTO> list = transactionDetailMapper.selectbyadress(address);
        return null;
    }

}
