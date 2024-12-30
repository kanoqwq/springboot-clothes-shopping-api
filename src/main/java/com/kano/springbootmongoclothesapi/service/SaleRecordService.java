package com.kano.springbootmongoclothesapi.service;

import com.kano.springbootmongoclothesapi.model.SaleRecord;
import com.kano.springbootmongoclothesapi.repository.SaleRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleRecordService {

    @Autowired
    private SaleRecordRepository repository;

    // 添加销售记录
    public SaleRecord addSaleRecord(SaleRecord record) {
        return repository.save(record);
    }

    // 列出所有销售记录
    public List<SaleRecord> getAllSaleRecords() {
        return repository.findAll();
    }
}