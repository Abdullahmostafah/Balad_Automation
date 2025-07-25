package StepDefinitions;

import Base.TestBase;
import Utils.ConfigReaderWriter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    String browser = ConfigReaderWriter.getPropKey("browser");

    @Before
    public void launchBrowser() {

        logger.info("Launching browser: {}", browser);
        switch (browser.toLowerCase()) {
            case "chrome":
                configureChromeBrowser();
                break;
            case "firefox":
                configureFireFoxBrowser();
                break;
            default:
                logger.error("Browser not supported: {}", browser);
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }

    @After
    public void tearDown() {
        logger.info("Tearing down browser");
        if (driver != null) {
            driver.quit();
        }
    }
}