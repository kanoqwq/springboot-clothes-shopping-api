package com.kano.springbootmongoclothesapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;
import java.util.List;

@Document(collection = "purchase_records")
public class PurchaseRecord {

    @Id
    private String id;

    @NotBlank(message = "批次ID不能为空")
    private String batchId;

    @NotBlank(message = "销售商ID不能为空")
    private String sellerId;

    @NotNull(message = "采购日期不能为空")
    private Date purchaseDate;

    @Positive(message = "采购数量必须大于 0")
    private int quantity;

    @NotNull(message = "状态不能为空")
    private List<String> status;

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

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }
}