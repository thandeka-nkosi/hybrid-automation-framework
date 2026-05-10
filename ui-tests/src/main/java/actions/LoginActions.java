package actions;

import core.utils.ConfigReader;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPageLocators;
import static pages.LoginPageLocators.*;
import java.time.Duration;

public class LoginActions {

    public WebDriver driver;
    WebDriverWait wait;


    public LoginActions(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isOnLoginPage() {
        driver.switchTo().defaultContent();
        driver.switchTo().frame("display");
        //driver.findElement(usernameField).sendKeys(); - to explore this further

        try {
            return driver.findElement(LoginPageLocators.usernameField).isDisplayed()
                    && driver.findElement(LoginPageLocators.passwordField).isDisplayed()
                    && driver.findElement(LoginPageLocators.loginButton).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void userLogin(String username, String password) {

        driver.findElement(LoginPageLocators.usernameField).sendKeys(username.trim());
        driver.findElement(LoginPageLocators.passwordField).sendKeys(password.trim());

    }

    public void clickLoginButton() throws InterruptedException {

        driver.findElement(LoginPageLocators.loginButton).click();
    }

    public void userIsLoggedIn() {



    }
}
