package com.kano.springbootmongoclothesapi.repository;

import com.kano.springbootmongoclothesapi.model.InventoryRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRecordRepository extends MongoRepository<InventoryRecord, String> {
}