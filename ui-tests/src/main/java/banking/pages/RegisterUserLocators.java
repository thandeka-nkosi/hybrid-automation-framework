package banking.pages;

import org.openqa.selenium.By;

public class RegisterUserLocators {

    public static By registerLink = By.xpath("//*[@id=\"loginPanel\"]/p[2]/a");
    public static By registerFormDisplayed = By.xpath("//*[@id=\"rightPanel\"]/h1");
    public static By firstName = By.xpath("//*[@id=\"customer.firstName\"]");
    public static By lastName = By.xpath("//*[@id=\"customer.lastName\"]");
    public static By address = By.xpath("//*[@id=\"customer.address.street\"]");
    public static By city = By.xpath("//*[@id=\"customer.address.city\"]");
    public static By state = By.xpath("//*[@id=\"customer.address.state\"]");
    public static By zipCode = By.xpath("//*[@id=\"customer.address.zipCode\"]");
    public static By phone = By.xpath("//*[@id=\"customer.phoneNumber\"]");
    public static By ssn = By.xpath("//*[@id=\"customer.ssn\"]");
    public static By username = By.xpath("//*[@id=\"customer.username\"]");
    public static By password = By.xpath("//*[@id=\"customer.password\"]");
    public static By confirmPassword = By.xpath("//*[@id=\"repeatedPassword\"]");
    public static By registerButton = By.xpath("//input[@type='submit' and @value='Register']");
    public static By userRegistered = By.xpath("//*[@id=\"leftPanel\"]/ul/li[8]/a");
}
