package io.wisoft.shop.user.controller;

import io.wisoft.shop.user.model.UserDAO;
import io.wisoft.shop.user.classes.UserDTO;

import java.util.List;

public class UserController {
    private final UserDAO dao = new UserDAO();

    public String getLoginUser(String id, String pw) {
        if (dao.isExistUser(id, pw)) {
            return id;
        }
        return null;
    }

    public boolean registerUser(String id, String pw, String userName, String gender, String email, String address, String rankId) {
        UserDTO dto = new UserDTO();
        dto.setUserId(id);
        dto.setPw(pw);
        dto.setUserName(userName);
        dto.setGender(gender);
        dto.setEmail(email);
        dto.setAddress(address);
        dto.setRankId(rankId);
        return dao.registerUser(dto);
    }

    public void delUser(String id) {
        if (dao.isDelUser(id))
            System.out.println("고객 정보를 제거하였습니다.");
        else
            System.out.println("고객 정보를 제거하는데 실패했습니다.");
    }

    public void printUserList() {
        List<UserDTO> userList = dao.getUserList();
        int seq = 1;
        for (UserDTO dto : userList) {
            System.out.println("[" + seq++ + "]");
            System.out.println("id : " + dto.getUserId());
            System.out.println("password : " + dto.getPw());
            System.out.println("name : " + dto.getUserName());
            System.out.println("gender : " + dto.getGender());
            System.out.println("email : " + dto.getEmail());
            System.out.println("address : " + dto.getAddress());
            System.out.println("io.wisoft.shop.rank : " + dto.getRankId());
            System.out.println("saved_money : " + dto.getSavedMoney());
        }
    }
}
