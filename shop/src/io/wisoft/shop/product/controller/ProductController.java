package io.wisoft.shop.product.controller;

import io.wisoft.shop.product.classes.ProductDTO;
import io.wisoft.shop.product.model.ProductDAO;

import java.util.List;

public class ProductController {
    ProductDAO dao = new ProductDAO();

    public String getAllProductInfo() {
        List<ProductDTO> productList = dao.getProductList();

        int sequence = 0;
        StringBuilder sb = new StringBuilder();
        System.out.println("       제품아이디   제품이름   단가   수량");
        for (ProductDTO dto : productList) {
            String productId = dto.getProductId();
            String productName = dto.getProductName();
            int unitPrice = dto.getUnitPrice();
            int count = dto.getCount();

            sequence++;
            sb.append("[")
                    .append(sequence)
                    .append("]")
                    .append(" | ")
                    .append(productId)
                    .append(" | ")
                    .append(productName)
                    .append(" | ")
                    .append(unitPrice)
                    .append(" | ")
                    .append(count)
                    .append("\n");
        }

        return sb.toString();
    }

    public String setProductUnitPrice(String productId, int unitPrice) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(productId);
        dto.setUnitPrice(unitPrice);

        String message;
        if (dao.isUpdatedProductUnitPrice(dto))
            message = "제품 단가를 조정하였습니다.";
        else
            message = "제품 단가를 조정하는데 실패했습니다.";

        return message;
    }

    public String addProduct(String productId, String productName, int unitPrice, int count) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(productId);
        dto.setProductName(productName);
        dto.setUnitPrice(unitPrice);
        dto.setCount(count);

        String message;
        if (dao.isAddProduct(dto))
            message = "제품을 추가하였습니다.";
        else
            message = "제품을 추가하는데 실패했습니다.";

        return message;
    }

    public String delProduct(String productId) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(productId);

        String message;
        if (dao.isDelProduct(dto)) {
            message = "제품을 제거하였습니다.";
        } else {
            message = "제품을 제거하는데 실패했습니다.";
        }
        return message;
    }

    public String supplyProduct(String productId, int supplyCount) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(productId);
        dto.setCount(supplyCount);

        String message;
        if (dao.isSupplyProduct(dto))
            message = "제품 제고를 조정하였습니다.";
        else
            message = "제품 재고를 조정하는데 실패했습니다.";
        return message;
    }
}
