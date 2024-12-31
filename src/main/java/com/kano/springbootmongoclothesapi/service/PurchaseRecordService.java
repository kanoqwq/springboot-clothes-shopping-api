package com.kano.springbootmongoclothesapi.service;

import com.kano.springbootmongoclothesapi.model.ProductionBatch;
import com.kano.springbootmongoclothesapi.model.PurchaseRecord;
import com.kano.springbootmongoclothesapi.repository.ProductionBatchRepository;
import com.kano.springbootmongoclothesapi.repository.PurchaseRecordRepository;
import com.kano.springbootmongoclothesapi.utils.RequestJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PurchaseRecordService {

    @Autowired
    private PurchaseRecordRepository repository;
    private ProductionBatchRepository batchRepository;

    public PurchaseRecordService(ProductionBatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    // 添加采购记录，并从productionBatches表中注解为已签收
    public PurchaseRecord addPurchaseRecord(PurchaseRecord record) throws Exception{
        //查找生产记录
        String batchId  = record.getBatchId();
        Optional<ProductionBatch> foundBatchRecord =  batchRepository.findById(batchId);
        if(foundBatchRecord.isEmpty()) throw new Exception("没有找到该生产记录");

        //更改生产记录的状态为”已签收“
        ProductionBatch batch = foundBatchRecord.get();

        if(Objects.equals(batch.getStatus(), "已签收")) throw new Exception("该订单已被签收");

        batch.setStatus("已签收");
        record.setQuantity(batch.getQuantity());//同步数量
        batchRepository.save(batch);
        record.setCreatedAt(Instant.now());//设置入库日期
        //默认设为未入库
        record.setStatus("未入库");

        //添加id
        HashMap<String,String> map =  RequestJWT.getUserInfo();
        String userId = map.get("userId");
        record.setSellerId(userId);

        //创建
        return repository.save(record);
    }

    // 列出所有采购记录
    public List<PurchaseRecord> getAllPurchaseRecords() {
        return repository.findAll();
    }
}