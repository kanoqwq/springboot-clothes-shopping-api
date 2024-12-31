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
import java.util.Optional;

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

        List<HashMap<String,Object>> newList = new ArrayList<>();

        //合并
        for (int i = 0; i < records.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            //获取BatchId
            String BatchId = records.get(i).getBatchId();
            Optional<ProductionBatch> batchRecord = batchService.getProductionBatchById(BatchId);

            if (batchRecord.isPresent()) {
                map.put("quantity",records.get(i).getCurrentQuantity());
                map.put("WarehouseDate",records.get(i).getLastUpdated());

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