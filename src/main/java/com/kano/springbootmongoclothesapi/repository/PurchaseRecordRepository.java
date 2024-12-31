package com.kano.springbootmongoclothesapi.repository;

import com.kano.springbootmongoclothesapi.model.PurchaseRecord;
import com.kano.springbootmongoclothesapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseRecordRepository extends MongoRepository<PurchaseRecord, String> {
    Optional<PurchaseRecord> findPurchaseRecordByBatchId(String username);
}