package com.kano.springbootmongoclothesapi.service;

import com.kano.springbootmongoclothesapi.model.InventoryRecord;
import com.kano.springbootmongoclothesapi.model.WarehouseRecord;
import com.kano.springbootmongoclothesapi.model.PurchaseRecord;
import com.kano.springbootmongoclothesapi.repository.InventoryRecordRepository;
import com.kano.springbootmongoclothesapi.repository.WarehouseRecordRepository;
import com.kano.springbootmongoclothesapi.repository.PurchaseRecordRepository;
import com.kano.springbootmongoclothesapi.utils.RequestJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class WarehouseRecordService {

    @Autowired
    private WarehouseRecordRepository repository;
    private InventoryRecordRepository inventoryRepository;
    private final PurchaseRecordRepository purchaseRecordRepository;

    public WarehouseRecordService(InventoryRecordRepository inventoryRepository, PurchaseRecordRepository purchaseRecordRepository) {
        this.inventoryRepository = inventoryRepository;
        this.purchaseRecordRepository = purchaseRecordRepository;
    }

    // 添加入库记录，并计入库存Inventory
    public WarehouseRecord addWarehouseRecordAndInventory(WarehouseRecord record) throws Exception {

        //查找入库记录
        String batchId  = record.getBatchId();

        Optional<PurchaseRecord> foundPurchaseRecord =  purchaseRecordRepository.findPurchaseRecordByBatchId(batchId);

        if(foundPurchaseRecord.isEmpty()) throw new Exception("没有找到该采购记录");

        //更改采购记录的状态为”已入库“
        PurchaseRecord purchase = foundPurchaseRecord.get();
        if(Objects.equals(purchase.getStatus(), "已入库")) throw new Exception("该订单已经入库过了");
        purchase.setStatus("已入库");

        //同步数量
        Date now = Date.from(Instant.now());
        record.setCurrentQuantity(purchase.getQuantity());
        purchaseRecordRepository.save(purchase);
        record.setLastUpdated(now);//设置入库日期

        //添加id
        HashMap<String,String> map =  RequestJWT.getUserInfo();
        String userId = map.get("userId");
        record.setWarehouseManagerId(userId);

        //添入库存
        InventoryRecord inventoryRecord = new InventoryRecord();
        inventoryRecord.setBatchId(batchId);
        inventoryRecord.setLastUpdated(now);
        inventoryRecord.setCurrentQuantity(purchase.getQuantity());
        inventoryRepository.save(inventoryRecord);

        //创建
        return repository.save(record);
    }

    // 列出所有入库记录
    public List<WarehouseRecord> getAllWarehouseRecords() {
        return repository.findAll();
    }
}