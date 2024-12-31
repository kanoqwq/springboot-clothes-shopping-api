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
import java.util.Optional;

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

        List<PurchaseRecord> records = service.getAllPurchaseRecords();

        List<HashMap<String,Object>> newList = new ArrayList<>();

        //合并
        for (int i = 0; i < records.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            //获取BatchId
            String BatchId = records.get(i).getBatchId();
            Optional<ProductionBatch> batchRecord = batchService.getProductionBatchById(BatchId);

            if (batchRecord.isPresent()) {
                map.put("status",records.get(i).getStatus());
                map.put("quantity",records.get(i).getQuantity());
                map.put("purchaseDate",records.get(i).getPurchaseDate());

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