package com.kano.springbootmongoclothesapi.repository;

import com.kano.springbootmongoclothesapi.model.InventoryRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRecordRepository extends MongoRepository<InventoryRecord, String> {
    Optional<InventoryRecord> findByBatchId(String id);
}