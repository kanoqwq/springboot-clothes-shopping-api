package com.kano.springbootmongoclothesapi.controller.SaleRecord;

import com.kano.springbootmongoclothesapi.model.SaleRecord;
import com.kano.springbootmongoclothesapi.service.SaleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale-records")
public class SaleRecordController {

    @Autowired
    private SaleRecordService service;

    // 添加销售记录
    @PostMapping
    public ResponseEntity<SaleRecord> createSaleRecord(
            @Validated @RequestBody SaleRecord record) {
        SaleRecord createdRecord = service.addSaleRecord(record);
        return ResponseEntity.ok(createdRecord);
    }

    // 列出所有销售记录
    @GetMapping
    public ResponseEntity<List<SaleRecord>> getAllRecords() {
        List<SaleRecord> records = service.getAllSaleRecords();
        return ResponseEntity.ok(records);
    }
}