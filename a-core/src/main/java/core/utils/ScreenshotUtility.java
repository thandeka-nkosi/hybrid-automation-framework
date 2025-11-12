package core.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtility {



    private final WebDriver driver;
    private static final ThreadLocal<String> moduleName = new ThreadLocal<>();

    public ScreenshotUtility(WebDriver driver) {
        this.driver = driver;
    }

    public String captureScreenshot(String scenarioName, String stepName) {
        String module = System.getProperty("moduleName", "DefaultModule");
        moduleName.set(module);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String dirPath = "test-output/screenshots/Failure";
        new File(dirPath).mkdirs();

        String fileName = module + "__" + scenarioName.replaceAll("\\s+", "_") + "__" + stepName.replaceAll("\\s+", "_") + "_" + timestamp + ".png";
        String relativePath = dirPath + "/" + fileName;
        File dest = new File(relativePath);

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "./" + relativePath.replace("\\", "/");

}}



