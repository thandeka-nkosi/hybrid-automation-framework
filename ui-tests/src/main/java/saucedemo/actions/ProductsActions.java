package saucedemo.actions;

import saucedemo.enums.Product;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import saucedemo.pages.ProductsLocators;

import java.util.List;

public class ProductsActions {

    WebDriver driver;

    public ProductsActions(WebDriver driver) {

        this.driver = driver;
    }


    public boolean isProductsPageDisplayed() {
        //return driver.getCurrentUrl().contains("inventory.html") &&
        driver.findElement(ProductsLocators.productsTitle)
                .getText()
                .trim()
                .equals("Products");

        return true;
    }



    public void addProductsToCart(DataTable dataTable) {
        List<Product> products = dataTable.asMaps()
                .stream()
                .map(row -> Product.fromName(row.get("Product")))
                .toList();

        addProductsToCart(products);
    }

    public void addProductsToCart(List<Product> products) {
        for (Product product : products) {
            addProductToCart(product);
        }
    }

    public void addProductToCart(Product product) {
        String addProduct = "//div[text()='" + product.getProductName() + "']" +
                "/ancestor::div[contains(@class,'inventory_item')]" +
                "//button[text()='Add to cart']";

        driver.findElement(By.xpath(addProduct)).click();
    }


    public void verifyProductsInCart(DataTable dataTable) {

        List<Product> expectedProducts = dataTable.asMaps()
                .stream()
                .map(row -> Product.fromName(row.get("Product")))
                .toList();

        List<String> actualProductNames = driver.findElements(By.className("inventory_item_name"))
                .stream()
                .map(element -> element.getText())
                .toList();

        for (Product product : expectedProducts) {
            if (!actualProductNames.contains(product.getProductName())) {
                throw new AssertionError("Product not found in cart: " + product.getProductName());
            }
        }

    }

    public void openCart() {
        driver.findElement(ProductsLocators.checkCart).click();
    }


}
