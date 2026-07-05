package banking.actions;

import banking.pages.RegisterUserLocators;
import core.utils.ConfigReader;
import org.openqa.selenium.WebDriver;

public class RegisterUserActions {

    private WebDriver driver;

    public RegisterUserActions(WebDriver driver) {

        this.driver = driver;
    }

    public void launchBrowser() {

        driver.get(ConfigReader.getProperty("banking.baseUrl","local"));
    }

    public void clickRegisterLink(){

        driver.findElement(RegisterUserLocators.registerLink).click();

    }

    public void registerFormDisplayed(){

        driver.findElement(RegisterUserLocators.registerFormDisplayed).
                getText().trim().equals("Signing up is easy!");
    }

    public void captureFormDetails(String firstName, String lastName,
                                   String address, String city,
                                   String state, String zipcode,
                                   String phone, String ssn) {
        String username = ConfigReader.getProperty("banking.username");
        String password = ConfigReader.getProperty("banking.password");
        String confirmPassword = ConfigReader.getProperty("banking.password");


        driver.findElement(RegisterUserLocators.firstName).sendKeys(firstName);
        driver.findElement(RegisterUserLocators.lastName).sendKeys(lastName);
        driver.findElement(RegisterUserLocators.address).sendKeys(address);
        driver.findElement(RegisterUserLocators.city).sendKeys(city);
        driver.findElement(RegisterUserLocators.state).sendKeys(state);
        driver.findElement(RegisterUserLocators.zipCode).sendKeys(zipcode);
        driver.findElement(RegisterUserLocators.phone).sendKeys(phone);
        driver.findElement(RegisterUserLocators.ssn).sendKeys(lastName);
        driver.findElement(RegisterUserLocators.username).sendKeys(username);
        driver.findElement(RegisterUserLocators.password).sendKeys(password);
        driver.findElement(RegisterUserLocators.confirmPassword).sendKeys(confirmPassword);

    }
    public void clickOnRegisterButton(){
        driver.findElement(RegisterUserLocators.registerButton).click();

    }

    public void userIsRegistered(){

        driver.findElement(RegisterUserLocators.userRegistered).isDisplayed();

    }

}
