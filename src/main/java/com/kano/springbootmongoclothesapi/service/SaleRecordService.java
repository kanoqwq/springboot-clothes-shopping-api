package com.kano.springbootmongoclothesapi.service;

import com.kano.springbootmongoclothesapi.model.InventoryRecord;
import com.kano.springbootmongoclothesapi.model.SaleRecord;
import com.kano.springbootmongoclothesapi.repository.InventoryRecordRepository;
import com.kano.springbootmongoclothesapi.repository.SaleRecordRepository;
import com.kano.springbootmongoclothesapi.utils.RequestJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class SaleRecordService {

    @Autowired
    private SaleRecordRepository repository;
    private InventoryRecordRepository inventoryRepository;

    public SaleRecordService(InventoryRecordRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    // 添加销售记录，并减少库存
    public SaleRecord addSaleRecord(SaleRecord record) throws Exception {
        //拿到batchId和count
        String batchId = record.getBatchId();
        int count = record.getCount();
        if(count<0) throw new Exception("数量不合法");

        //查找库存
        Optional<InventoryRecord> inventroyRecord=  inventoryRepository.findByBatchId(batchId);
        if(inventroyRecord.isEmpty()) throw  new Exception("没有该商品信息");

        if(inventroyRecord.get().getCurrentQuantity() == 0)  throw  new Exception("库存不足");

        //减少库存
        int curCount = inventroyRecord.get().getCurrentQuantity() - count;
        if(curCount<0)  throw  new Exception("库存不足");
        inventroyRecord.get().setCurrentQuantity(curCount);

        //添加id
        HashMap<String,String> map =  RequestJWT.getUserInfo();
        String userId = map.get("userId");
        record.setSellerId(userId);
        record.setSaleDate(Date.from(Instant.now()));

        inventoryRepository.save(inventroyRecord.get());

        return repository.save(record);
    }

    // 列出所有销售记录
    public List<SaleRecord> getAllSaleRecords() {
        return repository.findAll();
    }

    //列出特定的销售记录
}