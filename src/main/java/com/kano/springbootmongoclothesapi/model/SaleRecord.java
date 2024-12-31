package com.kano.springbootmongoclothesapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;

@Document(collection = "sale_records")
public class SaleRecord {

    @Id
    private String id;

    @NotBlank(message = "批次ID不能为空")
    private String batchId;

//    @NotBlank(message = "销售商ID不能为空")
    private String sellerId;

//    @NotNull(message = "销售日期不能为空")
    private Date saleDate;

//    @Positive(message = "销售数量必须大于 0")
    private int count;

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

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}