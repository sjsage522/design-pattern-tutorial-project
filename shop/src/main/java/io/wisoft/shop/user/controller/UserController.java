package io.wisoft.shop.user.controller;

import io.wisoft.shop.user.model.UserDAO;
import io.wisoft.shop.user.classes.UserDTO;

import java.util.List;

public class UserController {
    private final UserDAO dao = new UserDAO();

    public String getLoginUser(final String id, final String pw) {
        if (dao.isExistUser(id, pw)) {
            return id;
        }
        return null;
    }

    public boolean registerUser(final String id, final String pw, final String userName, final String gender,
                                final String email, final String address, final String rankId) {
        UserDTO dto = new UserDTO(id, pw, userName, gender, email, address, rankId, 0);
        return dao.registerUser(dto);
    }

    public void deleteUserById(final String id) {
        if (dao.isDeletedUserById(id))
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
