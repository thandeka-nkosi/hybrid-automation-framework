package core.driver;

import com.aventstack.extentreports.ExtentTest;
import core.reporting.ExtentReportManager;
import core.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class WebDriverManagerUtil {

    private WebDriverManagerUtil() {}

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            String browser = ConfigReader.getProperty("browser", "chrome");
            String execution = ConfigReader.getProperty("execution", "local");

            // ✅ Prefer system property gridUrl (Docker runner), fallback to config file hubUrl
            String gridUrl = System.getProperty("gridUrl");
            if (gridUrl == null || gridUrl.isBlank()) {
                gridUrl = ConfigReader.getProperty("hubUrl", "http://localhost:4444/wd/hub");
            }

            WebDriver createdDriver = null;

            try {
                if ("remote".equalsIgnoreCase(execution)) {
                    // === Remote execution (Docker or Grid) ===
                    switch (browser.toLowerCase()) {
                        case "chrome":
                            ChromeOptions chromeOptions = new ChromeOptions();
                            createdDriver = new RemoteWebDriver(new URL(gridUrl), chromeOptions);
                            break;
                        case "edge":
                            EdgeOptions edgeOptions = new EdgeOptions();
                            createdDriver = new RemoteWebDriver(new URL(gridUrl), edgeOptions);
                            break;
                        default:
                            throw new RuntimeException("Unsupported remote browser: " + browser);
                    }
                } else {
                    // === Local execution ===
                    switch (browser.toLowerCase()) {
                        case "chrome":
                            WebDriverManager.chromedriver().setup();
                            ChromeOptions chromeOptions = new ChromeOptions();
                            chromeOptions.addArguments("--headless=new");
                            chromeOptions.addArguments("--no-sandbox");
                            chromeOptions.addArguments("--disable-dev-shm-usage");
                            chromeOptions.addArguments("--disable-gpu");
                            chromeOptions.addArguments("--window-size=1920,1080");
                            chromeOptions.addArguments("--remote-allow-origins=*");
                            createdDriver = new ChromeDriver(chromeOptions);
                            break;

                        case "edge":
                            WebDriverManager.edgedriver().setup();
                            EdgeOptions edgeOptions = new EdgeOptions();
                            createdDriver = new EdgeDriver(edgeOptions);
                            break;
                        default:
                            throw new RuntimeException("Unsupported local browser: " + browser);
                    }
                }

                // Common setup for both local & remote
                createdDriver.manage().window().maximize();
                createdDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                DRIVER.set(createdDriver);

                System.out.println(">>> Browser: " + browser);
                System.out.println(">>> Execution mode: " + execution);
                System.out.println(">>> Grid URL used: " + gridUrl);

            } catch (MalformedURLException e) {
                throw new RuntimeException("❌ Invalid Selenium Grid URL: " + gridUrl, e);
            } catch (Exception e) {
                throw new RuntimeException("❌ Failed to initialize WebDriver for " + browser, e);
            }
        }

        return DRIVER.get();
    }

    public static void quitDriver() {
        try {
            WebDriver d = DRIVER.get();
            if (d != null) {
                d.quit();
            }
        } catch (Exception ignored) {

        } finally {
            DRIVER.remove();
        }
    }
}
