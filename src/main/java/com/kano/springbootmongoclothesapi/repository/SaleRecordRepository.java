package com.kano.springbootmongoclothesapi.repository;

import com.kano.springbootmongoclothesapi.model.SaleRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRecordRepository extends MongoRepository<SaleRecord, String> {
}