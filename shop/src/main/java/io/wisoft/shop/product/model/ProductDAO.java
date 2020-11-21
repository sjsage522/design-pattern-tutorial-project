package io.wisoft.shop.product.model;

import io.wisoft.shop.util.connection.DBConnection;
import io.wisoft.shop.product.classes.ProductDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DBConnection {

    public List<ProductDTO> getProductList() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null;
             ResultSet rs = (pstmt != null) ? pstmt.executeQuery() : null) {

            while (rs.next()) {
                String productId = rs.getString("product_id");
                String productName = rs.getString("product_name");
                int unitPrice = rs.getInt("unit_price");
                int count = rs.getInt("count");

                ProductDTO dto = new ProductDTO(productId, productName, unitPrice, count);
                productDTOList.add(dto);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return productDTOList;
    }

    public boolean isAddProduct(final ProductDTO dto) {
        if (isFindProductId(dto.getProductId())) return false;

        String query = "INSERT INTO product (product_id, product_name, unit_price, count) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null) {

            pstmt.setString(1, dto.getProductId());
            pstmt.setString(2, dto.getProductName());
            pstmt.setInt(3, dto.getUnitPrice());
            pstmt.setInt(4, dto.getCount());
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    public boolean isDeletedProduct(final ProductDTO dto) {
        if (!isFindProductId(dto.getProductId())) return false;

        String query = "DELETE FROM product WHERE product_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, dto.getProductId());
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    public boolean isSupplyProduct(final ProductDTO dto) {
        if (!isFindProductId(dto.getProductId())) return false;

        String query = "UPDATE product SET count = ? WHERE product_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, dto.getCount());
            pstmt.setString(2, dto.getProductId());
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    private boolean isFindProductId(final String pId) {
        String query = "SELECT * FROM product WHERE product_id = ?";
        boolean isFind = false;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null) {

            pstmt.setString(1, pId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) isFind = true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return isFind;
    }

    public boolean isUpdatedProductUnitPrice(final ProductDTO dto) {
        if (!isFindProductId(dto.getProductId())) return false;

        String query = "UPDATE product ";
        query += "SET unit_price = ? ";
        query += "WHERE product_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null){

            pstmt.setInt(1, dto.getUnitPrice());
            pstmt.setString(2, dto.getProductId());
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }
}
