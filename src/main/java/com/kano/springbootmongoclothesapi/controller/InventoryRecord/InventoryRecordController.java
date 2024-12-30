package com.kano.springbootmongoclothesapi.controller.InventoryRecord;

import com.kano.springbootmongoclothesapi.model.InventoryRecord;
import com.kano.springbootmongoclothesapi.service.InventoryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-records")
public class InventoryRecordController {

    @Autowired
    private InventoryRecordService service;

    // 添加库存记录
    @PostMapping
    public ResponseEntity<InventoryRecord> createInventoryRecord(
            @Validated @RequestBody InventoryRecord record) {
        InventoryRecord createdRecord = service.addInventoryRecord(record);
        return ResponseEntity.ok(createdRecord);
    }

    // 列出所有库存记录
    @GetMapping
    public ResponseEntity<List<InventoryRecord>> getAllRecords() {
        List<InventoryRecord> records = service.getAllInventoryRecords();
        return ResponseEntity.ok(records);
    }
}