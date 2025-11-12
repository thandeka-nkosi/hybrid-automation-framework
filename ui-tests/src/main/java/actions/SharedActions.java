package actions;

import core.driver.WebDriverManagerUtil;
import org.openqa.selenium.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SharedActions {

    private final WebDriver driver;
    private String parentWindowHandle;
    private List<String> windowHandlesList;

    public SharedActions() throws MalformedURLException {
        this.driver = WebDriverManagerUtil.getDriver();
        this.parentWindowHandle = driver.getWindowHandle();
        this.windowHandlesList = new ArrayList<>(driver.getWindowHandles());
    }

    /* -------------------- Window handle utils -------------------- */

    /**
     * Refresh the cached list of window handles.
     */
    public void updateWindowHandles() {
        this.windowHandlesList = new ArrayList<>(driver.getWindowHandles());
    }

    /**
     * Save the current window as the parent (call this after opening the main window).
     */
    public void saveParentWindowHandle() {
        this.parentWindowHandle = driver.getWindowHandle();
    }

    /**
     * Switch back to the saved parent window.
     */
    public void switchToParentWindow() {
        driver.switchTo().window(parentWindowHandle);
    }

    /**
     * Switch to a child window by index (1 = first child).
     */
    public boolean switchToChildWindow(int index) {
        updateWindowHandles();
        if (windowHandlesList.size() > index) {
            driver.switchTo().window(windowHandlesList.get(index));
            return true;
        }
        return false;
    }

    public boolean switchToFirstChildWindow() {
        return switchToChildWindow(1);
    }

    public boolean switchToSecondChildWindow() {
        return switchToChildWindow(2);
    }

    public boolean switchToThirdChildWindow() {
        return switchToChildWindow(3);
    }

    /**
     * When popups close, use this to focus the only remaining window.
     * (No waits here; call from test or use Waits.numberOfWindowsToBe if needed.)
     */
    public void focusToOnlyOpenWindow() {
        Set<String> remaining = driver.getWindowHandles();
        for (String handle : remaining) {
            driver.switchTo().window(handle);
            break;
        }
    }

    /**
     * Close current window and switch to parent (if still open).
     */
    public void closeCurrentAndReturnToParent() {
        driver.close();
        if (driver.getWindowHandles().contains(parentWindowHandle)) {
            driver.switchTo().window(parentWindowHandle);
        } else {
            // fall back to any remaining window
            focusToOnlyOpenWindow();
        }
    }

    /* -------------------- Frame helpers -------------------- */

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void switchToFrameByNameOrId(String nameOrId) {
        driver.switchTo().frame(nameOrId);
    }

    public void switchToFrame(By locator) {
        WebElement frame = driver.findElement(locator);
        driver.switchTo().frame(frame);
    }

    /* -------------------- Alert helpers -------------------- */

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public String acceptAlertIfPresent() {
        if (isAlertPresent()) {
            Alert a = driver.switchTo().alert();
            String text = a.getText();
            a.accept();
            return text;
        }
        return null;
    }

    public String dismissAlertIfPresent() {
        if (isAlertPresent()) {
            Alert a = driver.switchTo().alert();
            String text = a.getText();
            a.dismiss();
            return text;
        }
        return null;
    }

    /* -------------------- Lightweight UI helpers (no waits) -------------------- */

    public void click(By locator) {
        driver.findElement(locator).click();
    }

    public void type(By locator, String text) {
        WebElement el = driver.findElement(locator);
        el.clear();
        el.sendKeys(text);
    }

    public String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    public void jsScrollBy(int pxY) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, arguments[0]);", pxY);
    }

    public void jsScrollHalfPage() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, window.innerHeight / 2);");
    }

    public void jsScrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

}
