package com.kano.springbootmongoclothesapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "warehouse_records")
public class WarehouseRecord {

    @Id
    private String id;

//    @NotBlank(message = "批次ID不能为空")
    private String batchId;

//    @Positive(message = "库存数量必须大于 0")
    private int currentQuantity;

    private String warehouseManagerId;

//    @NotNull(message = "最后更新时间不能为空")
    private Date lastUpdated;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getWarehouseManagerId() {
        return warehouseManagerId;
    }

    public void setWarehouseManagerId(String warehouseManagerId) {
        this.warehouseManagerId = warehouseManagerId;
    }
}