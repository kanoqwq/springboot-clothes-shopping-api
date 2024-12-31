package com.kano.springbootmongoclothesapi.controller.WarehouseRecord;

import com.kano.springbootmongoclothesapi.model.ProductionBatch;
import com.kano.springbootmongoclothesapi.model.PurchaseRecord;
import com.kano.springbootmongoclothesapi.model.WarehouseRecord;
import com.kano.springbootmongoclothesapi.service.ProductionBatchRecordService;
import com.kano.springbootmongoclothesapi.service.WarehouseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/warehouse-records")
public class WarehouseRecordController {

    @Autowired
    private WarehouseRecordService service;
    private ProductionBatchRecordService batchService;

    public WarehouseRecordController(ProductionBatchRecordService batchService) {
        this.batchService = batchService;
    }

    // 添加入库记录,并计入库存
    @PostMapping
    public ResponseEntity<WarehouseRecord> createWarehouseRecord(
            @Validated @RequestBody WarehouseRecord record) throws Exception{
        WarehouseRecord createdRecord = service.addWarehouseRecordAndInventory(record);
        return ResponseEntity.ok(createdRecord);
    }

    // 列出所有入库记录
    @GetMapping
    public ResponseEntity<List<HashMap<String,Object>>> getAllRecords() {
        List<WarehouseRecord> records = service.getAllWarehouseRecords();
        List<ProductionBatch> batchRecords = batchService.getAllProductionBatches();

        List<HashMap<String,Object>> newWarehouseRecords = new ArrayList<>();

        //合并
        for (int i = 0; i < records.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("quantity",records.get(i).getCurrentQuantity());
            map.put("WarehouseDate",records.get(i).getLastUpdated());

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