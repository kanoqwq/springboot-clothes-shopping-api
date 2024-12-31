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
        List<ProductionBatch> batchRecords = batchService.getAllProductionBatches();

        List<HashMap<String,Object>> newWarehouseRecords = new ArrayList<>();

        //合并
        for (int i = 0; i < records.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("count",records.get(i).getCount());
            map.put("saleDate",records.get(i).getSaleDate());

            map.put("batchNumber",batchRecords.get(i).getBatchNumber());
            map.put("style",batchRecords.get(i).getStyle());
            map.put("color",batchRecords.get(i).getColor());
            map.put("size",batchRecords.get(i).getSize());
            map.put("producerId",batchRecords.get(i).getProducerId());
            map.put("productionDate",batchRecords.get(i).getProductionDate());

            newWarehouseRecords.add(map);
        }

        return ResponseEntity.ok(newWarehouseRecords);
    }
}