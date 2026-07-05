package saucedemo.pages;

import org.openqa.selenium.By;

public class ProductsLocators {

    public static By bikeLight = By.xpath("//*[@id=\"bike-light\"]");
    public static By productsTitle = By.xpath("//span[text()='Products']");
    public static By checkCart = By.xpath("//*[@id=\"shopping_cart_container\"]/a/span");
    public static By shoppingCartBadge = By.xpath("//*[@id=\"shopping_cart_container\"]/a/span");

}
