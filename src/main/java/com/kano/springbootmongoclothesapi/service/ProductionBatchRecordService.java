package com.kano.springbootmongoclothesapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kano.springbootmongoclothesapi.model.ProductionBatch;
import com.kano.springbootmongoclothesapi.repository.ProductionBatchRepository;
import com.kano.springbootmongoclothesapi.utils.QRCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductionBatchRecordService {

    @Autowired
    private ProductionBatchRepository repository;

    public Optional<ProductionBatch> getProductionBatchById(String id) {
        return repository.findById(id);
    }

    // 添加生产批次
    public ProductionBatch addProductionBatch(String callback_url,ProductionBatch oldBatch) {

        //先保存，方便获取id
        ProductionBatch batch  =  repository.save(oldBatch);

        HashMap<String,Object> parsemap =  new HashMap<>();

        parsemap.put("batchNumber",batch.getBatchNumber() );
        parsemap.put("style",batch.getStyle() );
        parsemap.put("color",batch.getColor() );
        parsemap.put("size",batch.getSize() );
        parsemap.put("quantity",batch.getQuantity() );
        parsemap.put("productionDate",batch.getProductionDate() );
        parsemap.put("status",batch.getStatus() );
        parsemap.put("producer_id",batch.getProducerId() );
        parsemap.put("id",batch.getId() );

        ObjectMapper objectMapper = new ObjectMapper();
        // 将接收的JSON转换为字符串
        String jsonData = "";
        try {
            jsonData = objectMapper.writeValueAsString(parsemap);
        } catch (Exception e) {
            throw new RuntimeException("二维码生成失败", e);
        }

        //包装为前端跳转URL
        String url = callback_url +"?QRContent="+  jsonData;

        // 生成二维码Base64字符串
        String base64QRCode = "data:image/png;base64," + QRCodeUtils.generateQRCodeBase64(url, 300, 300);
        batch.setQrCode(base64QRCode);

        //然后再更新
        return repository.save(batch);
    }

    // 列出所有生产批次
    public List<ProductionBatch> getAllProductionBatches() {
        return repository.findAll();
    }
}