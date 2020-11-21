package io.wisoft.shop.user.classes;

public class UserDTO {
    private String userId;
    private String pw;
    private String userName;
    private String gender;
    private String email;
    private String address;
    private String rankId;
    private int savedMoney;

    public UserDTO() {
    }

    public UserDTO(final String userId, final String pw, final String userName, final String gender, final String email,
                   final String address, final String rankId, final int savedMoney) {
        this.userId = userId;
        this.pw = pw;
        this.userName = userName;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.rankId = rankId;
        this.savedMoney = savedMoney;
    }

    public String getUserId() {
        return userId;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(final String pw) {
        this.pw = pw;
    }

    public void setUserId(final String id) {
        this.userId = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getRankId() {
        return rankId;
    }

    public void setRankId(final String rankId) {
        this.rankId = rankId;
    }

    public int getSavedMoney() {
        return savedMoney;
    }

    public void setSavedMoney(final int savedMoney) {
        this.savedMoney = savedMoney;
    }
}
