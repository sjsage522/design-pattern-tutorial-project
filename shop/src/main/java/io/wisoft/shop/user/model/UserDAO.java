package io.wisoft.shop.user.model;

import io.wisoft.shop.rank.*;
import io.wisoft.shop.util.connection.DBConnection;
import io.wisoft.shop.util.scanner.SingleScanner;
import io.wisoft.shop.user.classes.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAO extends DBConnection {

    //admin
    public List<UserDTO> getUserList() {
        List<UserDTO> userList = new ArrayList<>();

        String query = "SELECT * FROM customer";
        try (java.sql.Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null;
             ResultSet rs = (pstmt != null) ? pstmt.executeQuery() : null) {

            while (rs != null && rs.next()) {
                String id = rs.getString("id");
                String pw = rs.getString("pw");
                String userName = rs.getString("username");
                String gender = rs.getString("gender");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String rankId = rs.getString("rankid");
                int saved_money = rs.getInt("saved_money");

                UserDTO dto = new UserDTO(id, pw, userName, gender, email, address, rankId, saved_money);
                userList.add(dto);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return userList;
    }

    //admin
    public boolean isDeletedUserById(final String id) {
        boolean isSuccess = false;

        if (!isFindUserId(id)) {
            return isSuccess;
        }
        String query = "DELETE FROM customer" + " WHERE id=?";

        try (java.sql.Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null;
        ) {
            pstmt.setString(1, id);

            System.out.println("정말 제거하시겠습니까?(y/n)");
            System.out.print(">> ");
            if (SingleScanner.getInstance().nextLine().equalsIgnoreCase("Y")) {
                pstmt.execute();
                isSuccess = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return isSuccess;
    }


    //register
    public boolean registerUser(final UserDTO dto) {
        if (isFindUserId(dto.getUserId())) return false;

        String query = "INSERT INTO customer";
        query += " (id, pw, username, gender, email, address, rankid)";
        query += " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (java.sql.Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null;
        ) {

            pstmt.setString(1, dto.getUserId());
            pstmt.setString(2, dto.getPw());
            pstmt.setString(3, dto.getUserName());
            pstmt.setString(4, dto.getGender());
            pstmt.setString(5, dto.getEmail());
            pstmt.setString(6, dto.getAddress());
            pstmt.setString(7, dto.getRankId());
            pstmt.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    public void saveMoneyByUserId(final String userId, final int payMoney) {
        String userRankId = getRankIdByUserId(userId);
        RankFactory rankFactory = new RankFactory();

        Rank currentUserRank = rankFactory.getRank(userRankId);
        int savedMoney = (int) ((payMoney * currentUserRank.getDiscountRate()) / (100));
        System.out.println("적립금 : " + savedMoney);
        System.out.println("할인율 : " + currentUserRank.getDiscountRate());

        String query = "UPDATE customer SET saved_money = saved_money + ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setInt(1, savedMoney);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getSavedMoneyByUserId(final String userId) {
        String query = "SELECT saved_money FROM customer WHERE id = ?";
        int savedMoney = -1;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    savedMoney = rs.getInt("saved_money");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (savedMoney == -1) {
            System.out.println("적립금을 조회하는데 실패했습니다.");
            return -1;
        }

        return savedMoney;
    }

    public boolean isUpdateUserRankId(final String userId, final int savedMoney) {
        String currentRankId = getRankIdByUserId(userId);

        String rankId = "None";

        if (savedMoney >= 100 && savedMoney < 300) {
            if (currentRankId.equals("silver")) return false;
            rankId = "silver";
        } else if (savedMoney >= 300 && savedMoney < 500) {
            if (currentRankId.equals("gold")) return false;
            rankId = "gold";
        } else if (savedMoney >= 500) {
            if (currentRankId.equals("platinum")) return false;
            rankId = "platinum";
        }

        if (rankId.equals("None")) return false;

        UserDTO dto = new UserDTO();
        dto.setUserId(userId);
        dto.setRankId(rankId);
        updateUserRankId(dto);
        return true;
    }

    public void updateUserRankId(final UserDTO dto) {
        String query = "UPDATE customer SET rankid = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, dto.getRankId());
            pstmt.setString(2, dto.getUserId());
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getRankIdByUserId(final String userId) {
        String query = "SELECT rankid FROM customer WHERE id = ?";
        String currentRankId = "None";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null) {

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    currentRankId = rs.getString("rankid");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return currentRankId;
    }

    //login
    public boolean isExistUserByUserId(final String inputId, final String inputPw) {
        boolean isMatch = false;

        String query = "SELECT * FROM customer WHERE id=? AND pw=?";
        try (java.sql.Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null;
        ) {

            pstmt.setString(1, inputId);
            pstmt.setString(2, inputPw);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    isMatch = true;
                }
            } catch (NullPointerException exception) {
                exception.printStackTrace();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return isMatch;
    }

    private boolean isFindUserId(final String userId) {
        String query = "SELECT id FROM customer WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(query) : null) {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    return true;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
