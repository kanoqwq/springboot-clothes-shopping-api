package com.kano.springbootmongoclothesapi.controller.ProductionBatch;

import com.kano.springbootmongoclothesapi.model.ProductionBatch;
import com.kano.springbootmongoclothesapi.service.ProductionBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production-batches")
public class ProductionBatchController {

    @Autowired
    private ProductionBatchService service;

    // 添加生产批次
    @PostMapping
    public ResponseEntity<ProductionBatch> createProductionBatch(
            @Validated @RequestBody ProductionBatch batch) {
        ProductionBatch createdBatch = service.addProductionBatch(batch);
        return ResponseEntity.ok(createdBatch);
    }

    // 列出所有生产批次
    @GetMapping
    public ResponseEntity<List<ProductionBatch>> getAllBatches() {
        List<ProductionBatch> batches = service.getAllProductionBatches();
        return ResponseEntity.ok(batches);
    }
}