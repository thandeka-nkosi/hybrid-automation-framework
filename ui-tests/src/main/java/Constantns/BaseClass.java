package Constantns;

import actions.LoginActions;
import core.driver.WebDriverManagerUtil;
import core.utils.ConfigReader;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public class BaseClass {

    protected WebDriver driver() {
        return WebDriverManagerUtil.getDriver();
    }

    protected LoginActions login() {
        return new LoginActions(driver()); // new per call is fine; lightweight
    }

    protected void loginToApp(String username, String password) throws InterruptedException {
       // driver().get(ConfigReader.getProperty("sauceDemoUrl", "local"));
        login().userLogin(username, password);
        login().clickLoginButton();
    }

}
