package com.kano.springbootmongoclothesapi.service;

import com.kano.springbootmongoclothesapi.model.ProductionBatch;
import com.kano.springbootmongoclothesapi.repository.ProductionBatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionBatchService {

    @Autowired
    private ProductionBatchRepository repository;

    // 添加生产批次
    public ProductionBatch addProductionBatch(ProductionBatch batch) {
        return repository.save(batch);
    }

    // 列出所有生产批次
    public List<ProductionBatch> getAllProductionBatches() {
        return repository.findAll();
    }
}