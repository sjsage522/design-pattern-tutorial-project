package io.wisoft.shop.cart.classes;

public class CartDTO {
    private String cartId;
    private String userId;
    private String productName;
    private String productId;
    private int totalPrice;
    private int totalCount;

    public CartDTO() {
    }

    public CartDTO(final String userId, final String productId, final String productName, final int totalPrice,
                   final int totalCount) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
    }

    public CartDTO(final String cartId, final String userId, final String productName, final String productId,
                   final int totalPrice, final int totalCount) {
        this.cartId = cartId;
        this.userId = userId;
        this.productName = productName;
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
    }

    public CartDTO(final String productName, final String productId, final int totalPrice, final int totalCount) {
        this.productName = productName;
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(final String cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(final int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(final int totalCount) {
        this.totalCount = totalCount;
    }
}
