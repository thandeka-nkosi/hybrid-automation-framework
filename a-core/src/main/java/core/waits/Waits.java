package core.waits;

import core.config.AppConfig;
import core.driver.WebDriverManagerUtil;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;

public class Waits {

    private static final AppConfig CFG = ConfigFactory.create(AppConfig.class);
    private Waits() {}

    private static WebDriver driver() throws MalformedURLException { return WebDriverManagerUtil.getDriver(); }

    /** internal helper; name NOT "wait" to avoid confusion with Object.wait */
    private static WebDriverWait makeWait(long seconds) throws MalformedURLException {
        return new WebDriverWait(driver(), Duration.ofSeconds(seconds));
    }

    private static WebDriverWait makeDefaultWait() throws MalformedURLException {
        return makeWait(CFG.timeoutSec());
    }

    /* ------------ Common wrappers (static) ------------ */

    public static WebElement visible(By by) throws MalformedURLException {
        return makeDefaultWait().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static WebElement clickable(By by) throws MalformedURLException {
        return makeDefaultWait().until(ExpectedConditions.elementToBeClickable(by));
    }

    public static boolean present(By by) {
        try {
            makeDefaultWait().until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (TimeoutException | MalformedURLException e) {
            return false;
        }
    }

    public static boolean invisible(By by) throws MalformedURLException {
        return makeDefaultWait().until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static void numberOfWindowsToBe(int n) throws MalformedURLException {
        makeDefaultWait().until(ExpectedConditions.numberOfWindowsToBe(n));
    }

    public static void frameAndSwitch(By frameLocator) throws MalformedURLException {
        makeDefaultWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    public static <T> T until(ExpectedCondition<T> condition) throws MalformedURLException {
        return makeDefaultWait().until(condition);
    }
}
