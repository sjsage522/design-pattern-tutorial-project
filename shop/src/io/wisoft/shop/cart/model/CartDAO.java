package io.wisoft.shop.cart.model;

import io.wisoft.shop.cart.classes.CartDTO;
import io.wisoft.shop.rank.RankFactory;
import io.wisoft.shop.user.model.UserDAO;
import lib.java.connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class CartDAO extends DBConnection {
    UserDAO userDAO = new UserDAO();
    RankFactory rankFactory = new RankFactory();

    public boolean isAddProductToCart(CartDTO dto) {
        if (!isFindProductId(dto.getProductId())) {
            System.out.println("존재하지 않는 제품입니다.");
            return false;
        }

        if (!isRemainProduct(dto.getProductId(), dto.getTotalCount())) {
            System.out.println("제품 재고가 부족합니다.");
            return false;
        }

        String query = "SELECT * FROM product WHERE product_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null){

            pstmt.setString(1, dto.getProductId());
            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    String user_id = dto.getUserId();
                    String product_id = dto.getProductId();
                    String product_name = rs.getString("product_name");
                    int total_price = dto.getTotalCount() * rs.getInt("unit_price");
                    int total_count = dto.getTotalCount();

                    CartDTO cartDTO = new CartDTO();
                    cartDTO.setUserId(user_id);
                    cartDTO.setProductId(product_id);
                    cartDTO.setProductName(product_name);
                    cartDTO.setTotalPrice(total_price);
                    cartDTO.setTotalCount(total_count);
                    cartDTO.setCartId(
                            UUID.randomUUID().toString().toUpperCase().substring(0, 8)
                    );

                    addProduct(cartDTO);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    public List<CartDTO> getCartList(String id) {
        List<CartDTO> cartDTOList = new ArrayList<>();
        String query = "SELECT * FROM cart WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null){

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String cartId = rs.getString("cart_id");
                    String userId = rs.getString("user_id");
                    String productName = rs.getString("product_name");
                    String product_id = rs.getString("product_id");
                    int totalPrice = rs.getInt("total_price");
                    int totalCount = rs.getInt("total_count");

                    CartDTO cartDTO = new CartDTO();
                    cartDTO.setCartId(cartId);
                    cartDTO.setUserId(userId);
                    cartDTO.setProductName(productName);
                    cartDTO.setProductId(product_id);
                    cartDTO.setTotalPrice(totalPrice);
                    cartDTO.setTotalCount(totalCount);

                    cartDTOList.add(cartDTO);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return cartDTOList;
    }

    public boolean isRemoveProductFromCart(CartDTO cartDTO) {
        String query = "DELETE FROM cart WHERE cart_id = ? AND user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null){

            pstmt.setString(1, cartDTO.getCartId());
            pstmt.setString(2, cartDTO.getUserId());
            int process = pstmt.executeUpdate();

            if (process == 0) {
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    public boolean isBuyProductFromCart(CartDTO cartDTO) {
        String userId = cartDTO.getUserId();

        String query = "SELECT product_name, product_id, SUM(total_price) AS total_price, SUM(total_count) AS total_count ";
        query += "FROM cart WHERE user_id = ? ";
        query += "GROUP BY product_name, product_id";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()){
                LinkedList<CartDTO> tempList = new LinkedList<>();
                while (rs.next()) {
                    String productName = rs.getString("product_name");
                    String productId = rs.getString("product_id");
                    int totalPrice = rs.getInt("total_price");
                    int totalCount = rs.getInt("total_count");

                    if (!isRemainProduct(productId, totalCount)) {
                        System.out.println("재고가 부족합니다. 장바구니를 확인해 주세요.");
                        return false;
                    }

                    CartDTO dto = new CartDTO();
                    dto.setProductName(productName);
                    dto.setProductId(productId);
                    dto.setTotalPrice(totalPrice);
                    dto.setTotalCount(totalCount);
                    tempList.add(dto);
                }

                int sum = 0;
                while (!tempList.isEmpty()) {
                    CartDTO dto = tempList.pollFirst();
                    String productId = dto.getProductId();
                    int totalCount = dto.getTotalCount();
                    int totalPrice = dto.getTotalPrice();
                    sum += totalPrice;

                    double discountRate =
                            rankFactory.getRank(userDAO.getRankId(userId)).getDiscountRate();
                    sum = (int) (sum * (1 - (discountRate / 100)));

                    updateProductCount(productId, totalCount);
                }

                if (isClearCart(cartDTO)) {
                    System.out.println("총 결제 금액 : " + sum + "원.");
                    userDAO.saveMoney(userId, sum);
                }
                else {
                    System.out.println("장바구니가 비었습니다.");
                    System.out.println("총 결제 금액 0 원.");
                }

                int currentUserSavedMoney = userDAO.getSavedMoney(userId);
                if (userDAO.isUpdateUserRankId(userId, currentUserSavedMoney)) {
                    System.out.println("등급이 올라갔습니다.");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    public boolean isClearCart(CartDTO cartDTO) {
        String userId = cartDTO.getUserId();

        if (!isFindUserId(userId)) return false;

        String query = "DELETE FROM cart WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, userId);
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    private void updateProductCount(String productId, int totalCount) {
        int currentCount = getCurrentCount(productId);
        int setCount = currentCount - totalCount;

        String query = "UPDATE product ";
        query += "SET count = ? ";
        query += "WHERE product_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null){

            pstmt.setInt(1, setCount);
            pstmt.setString(2, productId);
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private int getCurrentCount(String productId) {
        String query = "SELECT count FROM product WHERE product_id = ?";
        int currentCount = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, productId);
            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    currentCount = rs.getInt("count");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return currentCount;
    }

    private void addProduct(CartDTO cartDTO) {
        String query = "INSERT INTO cart (cart_id, user_id, product_name, product_id, total_price, total_count) ";
        query += "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null) {

            pstmt.setString(1, cartDTO.getCartId());
            pstmt.setString(2, cartDTO.getUserId());
            pstmt.setString(3, cartDTO.getProductName());
            pstmt.setString(4, cartDTO.getProductId());
            pstmt.setInt(5, cartDTO.getTotalPrice());
            pstmt.setInt(6, cartDTO.getTotalCount());
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean isRemainProduct(String productId, int totalCount) {
        String query = "SELECT count FROM product WHERE product_id = ?";
        boolean isRemain = false;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null) {

            pstmt.setString(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");

                    if (count - totalCount >= 0) isRemain = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return isRemain;
    }

    private boolean isFindProductId(String pId) {
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

    private boolean isFindUserId(String userId) {
        String query = "SELECT * FROM cart WHERE user_id = ?";
        boolean isFind = false;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null) {

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) isFind = true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return isFind;
    }
}
