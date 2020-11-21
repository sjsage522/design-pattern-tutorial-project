package io.wisoft.shop.product.classes;

public class ProductDTO {
    private String productId;
    private String productName;
    private int unitPrice;
    private int count;

    public ProductDTO() {}

    public ProductDTO(final String productId, final String productName, final int unitPrice, final int count) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.count = count;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(final int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }
}
