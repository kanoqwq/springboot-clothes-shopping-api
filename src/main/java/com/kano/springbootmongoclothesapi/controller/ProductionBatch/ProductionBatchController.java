package com.kano.springbootmongoclothesapi.controller.ProductionBatch;

import com.kano.springbootmongoclothesapi.model.ProductionBatch;
import com.kano.springbootmongoclothesapi.service.ProductionBatchRecordService;
import com.kano.springbootmongoclothesapi.utils.RequestJWT;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/production-batches")
public class ProductionBatchController {

    @Autowired
    private ProductionBatchRecordService service;

    // 添加生产批次
    @PostMapping
    public ResponseEntity<ProductionBatch> createProductionBatch(
            @Validated @RequestBody ProductionBatch batch, @NotBlank @RequestParam String callback_url) {
        if (RequestJWT.getUserInfo() == null) return new ResponseEntity<>(HttpStatusCode.valueOf(401));

        //添加id
        HashMap<String,String> map =  RequestJWT.getUserInfo();
        String userId = map.get("userId");
        batch.setProducerId(userId);

        //先插入数据库，然后再创建二维码，再次插入数据库
        ProductionBatch createdBatch = service.addProductionBatch(callback_url,batch);

        return ResponseEntity.ok(createdBatch);
    }

    // 列出所有生产批次
    @GetMapping
    public ResponseEntity<List<ProductionBatch>> getAllBatches() {
        if (RequestJWT.getUserInfo() == null) return new ResponseEntity<>(HttpStatusCode.valueOf(401));

        List<ProductionBatch> batches = service.getAllProductionBatches();
        return ResponseEntity.ok(batches);
    }
}