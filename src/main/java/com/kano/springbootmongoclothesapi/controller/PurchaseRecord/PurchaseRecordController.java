package com.kano.springbootmongoclothesapi.controller.PurchaseRecord;

import com.kano.springbootmongoclothesapi.model.ProductionBatch;
import com.kano.springbootmongoclothesapi.model.PurchaseRecord;
import com.kano.springbootmongoclothesapi.service.ProductionBatchRecordService;
import com.kano.springbootmongoclothesapi.service.PurchaseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-records")
public class PurchaseRecordController {

    @Autowired
    private PurchaseRecordService service;
    private ProductionBatchRecordService batchService;

    public PurchaseRecordController(ProductionBatchRecordService batchService) {
        this.batchService = batchService;
    }

    // 添加采购记录
    @PostMapping
    public ResponseEntity<PurchaseRecord> createPurchaseRecord(
            @Validated @RequestBody PurchaseRecord record) throws Exception {
        PurchaseRecord createdRecord = service.addPurchaseRecord(record);
        return ResponseEntity.ok(createdRecord);
    }

    // 列出所有采购记录
    @GetMapping
    public ResponseEntity<List<HashMap<String,Object>>> getAllRecords() {
        List<PurchaseRecord> purchaseRecords = service.getAllPurchaseRecords();
        List<ProductionBatch> batchRecords = batchService.getAllProductionBatches();

        List<HashMap<String,Object>> newPurchaseRecords = new ArrayList<>();

        //合并
        for (int i = 0; i < purchaseRecords.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("status",purchaseRecords.get(i).getStatus());
            map.put("quantity",purchaseRecords.get(i).getQuantity());
            map.put("purchaseDate",purchaseRecords.get(i).getPurchaseDate());

            map.put("batchNumber",batchRecords.get(i).getBatchNumber());
            map.put("style",batchRecords.get(i).getStyle());
            map.put("color",batchRecords.get(i).getColor());
            map.put("size",batchRecords.get(i).getSize());
            map.put("producerId",batchRecords.get(i).getProducerId());
            map.put("productionDate",batchRecords.get(i).getProductionDate());

            newPurchaseRecords.add(map);
        }


        return ResponseEntity.ok(newPurchaseRecords);
    }
}