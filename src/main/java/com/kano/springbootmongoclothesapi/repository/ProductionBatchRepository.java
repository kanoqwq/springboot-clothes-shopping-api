package com.kano.springbootmongoclothesapi.repository;

import com.kano.springbootmongoclothesapi.model.ProductionBatch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionBatchRepository extends MongoRepository<ProductionBatch, String> {

}