package com.kano.springbootmongoclothesapi.controller.SaleRecord;

import com.kano.springbootmongoclothesapi.model.ProductionBatch;
import com.kano.springbootmongoclothesapi.model.PurchaseRecord;
import com.kano.springbootmongoclothesapi.model.SaleRecord;
import com.kano.springbootmongoclothesapi.model.WarehouseRecord;
import com.kano.springbootmongoclothesapi.service.ProductionBatchRecordService;
import com.kano.springbootmongoclothesapi.service.SaleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sale-records")
public class SaleRecordController {

    @Autowired
    private SaleRecordService service;
    private ProductionBatchRecordService batchService;

    public SaleRecordController(ProductionBatchRecordService batchService) {
        this.batchService = batchService;
    }

    // 添加销售记录
    @PostMapping
    public ResponseEntity<SaleRecord> createSaleRecord(
            @Validated @RequestBody SaleRecord record) throws Exception {
        SaleRecord createdRecord = service.addSaleRecord(record);
        return ResponseEntity.ok(createdRecord);
    }

    // 列出所有销售记录
    @GetMapping
    public ResponseEntity<List<HashMap<String,Object>>> getAllRecords() {
        List<SaleRecord> records = service.getAllSaleRecords();


        List<HashMap<String,Object>> newList = new ArrayList<>();

        //合并
        for (int i = 0; i < records.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            //获取BatchId
            String BatchId = records.get(i).getBatchId();
            Optional<ProductionBatch> batchRecord = batchService.getProductionBatchById(BatchId);

           if (batchRecord.isPresent()) {
               map.put("count",records.get(i).getCount());
               map.put("saleDate",records.get(i).getSaleDate());
               map.put("batchNumber",batchRecord.get().getBatchNumber());
               map.put("style",batchRecord.get().getStyle());
               map.put("color",batchRecord.get().getColor());
               map.put("size",batchRecord.get().getSize());
               map.put("producerId",batchRecord.get().getProducerId());
               map.put("productionDate",batchRecord.get().getProductionDate());
               newList.add(map);
           }
        }

        return ResponseEntity.ok(newList);
    }
}