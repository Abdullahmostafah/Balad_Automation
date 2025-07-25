package Utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            if (driver == null) {
                System.err.println("WebDriver is null, cannot capture screenshot for test '" + testName + "'");
                return "";
            }

            // Ensure browser window is maximized and rendered
            try {
                driver.manage().window().maximize();
                Thread.sleep(1000); // Delay to ensure rendering
            } catch (Exception e) {
                System.err.println("Failed to maximize window for test '" + testName + "': " + e.getMessage());
            }

            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timeStamp = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date());
            String fileName = testName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timeStamp + ".png";
            String destination = System.getProperty("user.dir") + "/target/screenshots/" + fileName;

            // Ensure the screenshots directory exists
            File screenshotDir = new File(System.getProperty("user.dir") + "/target/screenshots/");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            FileUtils.copyFile(source, new File(destination));
            System.out.println("Screenshot saved at: " + destination);
            return destination;
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot for test '" + testName + "': " + e.getMessage());
            return "";
        } catch (Exception e) {
            System.err.println("Unexpected error while capturing screenshot for test '" + testName + "': " + e.getMessage());
            return "";
        }
    }
}