package saucedemo.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import saucedemo.pages.ProductsLocators;

import java.util.List;

public class CartActions {


    WebDriver driver;

    public CartActions(WebDriver driver) {

        this.driver = driver;
    }

    public void verifyCartBadgeCount(String expectedCount) {

        String actualCount = driver.findElement(ProductsLocators.shoppingCartBadge)
                .getText()
                .trim();

        if (!actualCount.equals(expectedCount)) {
            throw new AssertionError(
                    "Expected cart count: " + expectedCount + " but found: " + actualCount
            );
        }
    }

    public void verifyCartIsEmpty(String expectedCount) {

        List<WebElement> badge = driver.findElements(By.className("shopping_cart_badge"));

        if (badge.isEmpty()) {
            if (!expectedCount.equals("0")) {
                throw new AssertionError("Expected cart count " + expectedCount + " but badge not found");
            }
        } else {
            String actualCount = badge.get(0).getText().trim();

            if (!actualCount.equals(expectedCount)) {
                throw new AssertionError("Expected: " + expectedCount + " but got: " + actualCount);
            }
        }
    }
}
