package io.wisoft.shop.cart.controller;

import io.wisoft.shop.cart.classes.CartDTO;
import io.wisoft.shop.cart.model.CartDAO;
import io.wisoft.shop.payment.Payment;
import lib.java.scanner.InputInfo;

import java.util.List;

public class CartController {
    CartDAO cartDAO = new CartDAO();
    InputInfo inputInfo = new InputInfo();

    public String getCartInfo(String userId) {
        List<CartDTO> cartDTOList = cartDAO.getCartList(userId);

        int sequence = 0;
        StringBuilder sb = new StringBuilder();
        System.out.println("       카트아이디   제품이름   합계   수량");
        for (CartDTO dto : cartDTOList) {
            String cartId = dto.getCartId();
            String productName = dto.getProductName();
            int totalPrice = dto.getTotalPrice();
            int totalCount = dto.getTotalCount();

            sequence++;
            sb.append("[")
                    .append(sequence)
                    .append("]")
                    .append(" | ")
                    .append(cartId)
                    .append(" | ")
                    .append(productName)
                    .append(" | ")
                    .append(totalPrice)
                    .append(" | ")
                    .append(totalCount)
                    .append("\n");
        }

        return sb.toString();
    }

    public String addProductToCart(String productId, int count, String userId) {
        CartDTO dto = new CartDTO();
        dto.setProductId(productId);
        dto.setTotalCount(count);
        dto.setUserId(userId);

        String message;
        if (cartDAO.isAddProductToCart(dto))
            message = "물품을 장바구니에 추가하였습니다.";
        else
            message = "물품을 장바구니에 추가하는데 실패했습니다.";

        return message;
    }

    public String removeProductFromCart(String cartId, String userId) {
        CartDTO dto = new CartDTO();
        dto.setUserId(userId);
        dto.setCartId(cartId);

        String message;
        if (cartDAO.isRemoveProductFromCart(dto))
            message = "물품을 장바구니에서 제거했습니다.";
        else
            message = "물품을 장바구니에서 제거하는데 실패했습니다.";

        return message;
    }

    public String buyProductFromCart(String userId) {
        Payment payment = inputInfo.selectPaymentMethod();
        payment.checkPaymentMethod();

        CartDTO dto = new CartDTO();
        dto.setUserId(userId);

        String message;
        if (cartDAO.isBuyProductFromCart(dto))
            message = "결재가 완료되었습니다.";
        else
            message = "결재에 실패하였습니다.";

        return message;
    }

    public String clearCart(String userId) {
        CartDTO dto = new CartDTO();
        dto.setUserId(userId);

        String message;
        if (cartDAO.isClearCart(dto))
            message = "장바구니가 초기화 되었습니다.";
        else
            message = "장바구니를 초기화하는데 실패했습니다.";

        return message;
    }
}
