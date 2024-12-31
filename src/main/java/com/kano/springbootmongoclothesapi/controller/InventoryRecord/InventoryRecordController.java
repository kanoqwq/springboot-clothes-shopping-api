package com.kano.springbootmongoclothesapi.controller.InventoryRecord;

import com.kano.springbootmongoclothesapi.model.InventoryRecord;
import com.kano.springbootmongoclothesapi.model.ProductionBatch;
import com.kano.springbootmongoclothesapi.model.WarehouseRecord;
import com.kano.springbootmongoclothesapi.service.InventoryRecordService;
import com.kano.springbootmongoclothesapi.service.ProductionBatchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory-records")
public class InventoryRecordController {

    @Autowired
    private InventoryRecordService service;
    private ProductionBatchRecordService batchService;

    public InventoryRecordController(ProductionBatchRecordService batchService) {
        this.batchService = batchService;
    }

    // 添加库存记录
    @PostMapping
    public ResponseEntity<InventoryRecord> createInventoryRecord(
            @Validated @RequestBody InventoryRecord record) {
        InventoryRecord createdRecord = service.addInventoryRecord(record);
        return ResponseEntity.ok(createdRecord);
    }

    // 列出所有库存记录
    @GetMapping
    public ResponseEntity<List<HashMap<String,Object>>> getAllRecords() {
        List<InventoryRecord> records = service.getAllInventoryRecords();

        List<HashMap<String,Object>> newList = new ArrayList<>();

        //合并
        for (int i = 0; i < records.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            //获取BatchId
            String BatchId = records.get(i).getBatchId();
            Optional<ProductionBatch> batchRecord = batchService.getProductionBatchById(BatchId);

            if (batchRecord.isPresent()) {
                map.put("quantity",records.get(i).getCurrentQuantity());
                map.put("InventoryDate",records.get(i).getLastUpdated());

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

    //修改库存数量
}