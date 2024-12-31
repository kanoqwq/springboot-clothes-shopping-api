package com.kano.springbootmongoclothesapi.repository;

import com.kano.springbootmongoclothesapi.model.WarehouseRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRecordRepository extends MongoRepository<WarehouseRecord, String> {
}