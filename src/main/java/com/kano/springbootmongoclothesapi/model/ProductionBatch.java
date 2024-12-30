package com.kano.springbootmongoclothesapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;
import java.util.List;

@Document(collection = "production_batches")  // 指定 MongoDB 集合名称
public class ProductionBatch {

    @Id
    private String id;

    @NotBlank(message = "批次号不能为空")
    private String batchNumber;

    @NotBlank(message = "款式不能为空")
    private String style;

    @NotBlank(message = "颜色不能为空")
    private String color;

    @NotNull(message = "尺码不能为空")
    private String size;

    @Positive(message = "数量必须大于 0")
    private int quantity;

    @NotBlank(message = "生产商ID不能为空")
    private String producerId;

    @NotNull(message = "生产日期不能为空")
    private Date productionDate;

    @NotBlank(message = "二维码不能为空")
    private String qrCode;

    @NotNull(message = "状态不能为空")
    private String status;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}