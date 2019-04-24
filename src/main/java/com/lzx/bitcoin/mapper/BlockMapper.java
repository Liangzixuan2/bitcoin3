package com.lzx.bitcoin.mapper;

import com.github.pagehelper.Page;
import com.lzx.bitcoin.bean.Block;
import com.lzx.bitcoin.dto.BlockDetailDTO;
import com.lzx.bitcoin.dto.BlockListDTO;

import java.util.List;

public interface BlockMapper {
    int deleteByPrimaryKey(String blockhash);

    int insert(Block record);

    int insertSelective(Block record);

    Block selectByPrimaryKey(String blockhash);

    int updateByPrimaryKeySelective(Block record);

    int updateByPrimaryKey(Block record);

    void truncate();

    List<BlockListDTO> getRecentBlocksById(Integer blockchainId);

    BlockDetailDTO getBlockDetailByHash(String blockhash);

    BlockDetailDTO getBlockDetailByHeight(Integer blockheight);

    Page<BlockListDTO> getRecentBlocks();
}