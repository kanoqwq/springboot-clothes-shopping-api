package com.kano.springbootmongoclothesapi.repository;

import com.kano.springbootmongoclothesapi.model.PurchaseRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRecordRepository extends MongoRepository<PurchaseRecord, String> {
}