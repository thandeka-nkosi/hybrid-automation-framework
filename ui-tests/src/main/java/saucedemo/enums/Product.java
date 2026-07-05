package saucedemo.enums;

import java.util.Arrays;

public enum Product {

    BIKE_LIGHT("Sauce Labs Bike Light"),
    BACKPACK("Sauce Labs Backpack"),
    BOLT_T_SHIRT("Sauce Labs Bolt T-Shirt"),
    FLEECE_JACKET("Sauce Labs Fleece Jacket"),
    ONESIE("Sauce Labs Onesie"),
    RED_T_SHIRT("Test.allTheThings() T-Shirt (Red)");

    private final String productName;

    Product(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public static Product fromName(String productName) {
        return Arrays.stream(values())
                .filter(product -> product.getProductName().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productName));
    }
}
