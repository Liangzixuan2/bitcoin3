package com.lzx.bitcoin.controller;

import com.lzx.bitcoin.dto.ImportStateDTO;
import com.lzx.bitcoin.mapper.TransactionMapper;
import com.lzx.bitcoin.service.MiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/misc")
@EnableAutoConfiguration
public class MiscController {

    @Autowired
    private MiscService miscService;

    @Autowired
    private TransactionMapper transactionMapper;

    @GetMapping("/search")
    public Object search(@RequestParam String keyword){
        return null;
    }

    @GetMapping("/importFromHeight")
    public void importFromHeight(@RequestParam Integer blockHeight,
                                 @RequestParam(required = false, defaultValue = "false") Boolean isClean){
        miscService.importFromHeight(blockHeight, isClean);
    }

    @GetMapping("/importFromHash")
    public void importFromHash(@RequestParam String blockhash, Boolean isClean) throws Throwable {
        miscService.importFromHash(blockhash, isClean);
    }

    @GetMapping("/getImportState")
    public List<ImportStateDTO> getImportState(){
        List<ImportStateDTO> list = transactionMapper.getImportState();
        return list;
    }
}
