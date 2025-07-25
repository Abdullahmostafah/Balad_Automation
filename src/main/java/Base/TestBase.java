package Base;

import Utils.ConfigReaderWriter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class TestBase {
    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);
    protected static WebDriver driver;
    protected static SoftAssert softAssert = new SoftAssert();

    protected void openWebSite() {
        logger.info("Navigating to URL: {}", ConfigReaderWriter.getPropKey("url"));
        driver.get(ConfigReaderWriter.getPropKey("url"));
    }

    protected static void initiateBrowser() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        logger.info("Browser window maximized");
    }

    protected static void configureChromeBrowser() {
        driver = new ChromeDriver();
        logger.info("Chrome browser initialized");
        initiateBrowser();
    }

    protected static void configureFireFoxBrowser() {
        driver = new FirefoxDriver();
        logger.info("Firefox browser initialized");
        initiateBrowser();
    }


}