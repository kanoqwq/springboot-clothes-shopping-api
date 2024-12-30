package com.kano.springbootmongoclothesapi.service;

import com.kano.springbootmongoclothesapi.model.PurchaseRecord;
import com.kano.springbootmongoclothesapi.repository.PurchaseRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseRecordService {

    @Autowired
    private PurchaseRecordRepository repository;

    // 添加采购记录
    public PurchaseRecord addPurchaseRecord(PurchaseRecord record) {
        return repository.save(record);
    }

    // 列出所有采购记录
    public List<PurchaseRecord> getAllPurchaseRecords() {
        return repository.findAll();
    }
}