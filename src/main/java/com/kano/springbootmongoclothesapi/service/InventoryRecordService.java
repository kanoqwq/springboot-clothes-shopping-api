package com.kano.springbootmongoclothesapi.service;

import com.kano.springbootmongoclothesapi.model.InventoryRecord;
import com.kano.springbootmongoclothesapi.repository.InventoryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryRecordService {

    @Autowired
    private InventoryRecordRepository repository;

    // 添加库存记录
    public InventoryRecord addInventoryRecord(InventoryRecord record) {
        return repository.save(record);
    }

    // 列出所有库存记录
    public List<InventoryRecord> getAllInventoryRecords() {
        return repository.findAll();
    }
}