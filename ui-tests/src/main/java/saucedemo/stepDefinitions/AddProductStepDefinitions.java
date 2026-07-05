package saucedemo.stepDefinitions;

import saucedemo.actions.CartActions;
import saucedemo.actions.ProductsActions;
import core.driver.WebDriverManagerUtil;
import core.utils.StepLogger;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;


public class AddProductStepDefinitions {

    private ProductsActions productsActions;
    private CartActions cartActions;
    WebDriver driver;


    public AddProductStepDefinitions() {
        this.driver = WebDriverManagerUtil.getDriver();
        this.productsActions = new ProductsActions(driver);
        this.cartActions = new CartActions(driver);
    }

    @Given("the user is on the products page")
    public void the_user_is_on_the_products_page() {

        StepLogger.logStep(
                "User is on Products page",
                "User is NOT on Products page",
                () -> assertTrue(productsActions.isProductsPageDisplayed())
        );
    }

    @When("the user adds the following products to cart")
    public void the_user_adds_the_following_products_to_cart(io.cucumber.datatable.DataTable dataTable) {

        StepLogger.logStep(
                "Products are added to cart",
                "Products not added to cart",
                () ->  productsActions.addProductsToCart(dataTable)
        );
    }

    @Then("the following products are visible in the cart")
    public void the_following_products_are_visible_in_the_cart(io.cucumber.datatable.DataTable dataTable) {

        StepLogger.logStep(
                "Product is visible in the cart page",
                "Cart is NOT visible in the cart page",
                () -> productsActions.verifyProductsInCart(dataTable)
        );
    }

    @Then("the cart badge should display {string}")
    public void theCartBadgeShouldDisplay(String expectedCount) {

        StepLogger.logStep(
                "Cart badge shows correct count",
                "Cart badge count is incorrect",
                () -> cartActions.verifyCartBadgeCount(expectedCount)
        );
    }
}

