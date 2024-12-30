package com.kano.springbootmongoclothesapi.controller.PurchaseRecord;

import com.kano.springbootmongoclothesapi.model.PurchaseRecord;
import com.kano.springbootmongoclothesapi.service.PurchaseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-records")
public class PurchaseRecordController {

    @Autowired
    private PurchaseRecordService service;

    // 添加采购记录
    @PostMapping
    public ResponseEntity<PurchaseRecord> createPurchaseRecord(
            @Validated @RequestBody PurchaseRecord record) {
        PurchaseRecord createdRecord = service.addPurchaseRecord(record);
        return ResponseEntity.ok(createdRecord);
    }

    // 列出所有采购记录
    @GetMapping
    public ResponseEntity<List<PurchaseRecord>> getAllRecords() {
        List<PurchaseRecord> records = service.getAllPurchaseRecords();
        return ResponseEntity.ok(records);
    }
}