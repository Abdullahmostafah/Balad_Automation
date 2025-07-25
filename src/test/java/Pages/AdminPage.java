package Pages;

import Base.TestBase;
import Utils.ConfigReaderWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminPage extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(AdminPage.class);
    private Integer initialCount = null;
    private final WebDriverWait wait;

    @FindBy(xpath = "//span[text()='Admin']")
    private WebElement adminButton;

    @FindBy(className = "orangehrm-horizontal-padding")
    private WebElement recordsCountTextField;

    @FindBy(xpath = "//*[text()=' Add ']")
    private WebElement addButton;

    @FindBy(xpath = "(//div/div/div[@class='oxd-select-text-input'])[1]")
    private WebElement roles;

    @FindBy(xpath = "//div[contains(text(),'Admin')]")
    private WebElement dropDownRoles;

    @FindBy(xpath = "(//div/div/div[@class='oxd-select-text-input'])[2]")
    private WebElement status;

    @FindBy(xpath = "//*[text()='Enabled']")
    private WebElement selectStatus;

    @FindBy(xpath = "//*[@placeholder='Type for hints...']")
    private WebElement employeeNameField;

    @FindBy(xpath = "(//input[@class='oxd-input oxd-input--active'])[2]")
    private WebElement usernameField;

    @FindBy(xpath = "(//input[@type='password'])[1]")
    private WebElement PWField;

    @FindBy(xpath = "(//input[@type='password'])[2]")
    private WebElement confirmPWField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveBtn;

    @FindBy(xpath = "//body/div[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/form[1]/div[1]/div[1]/div[1]/div[1]/div[2]/input[1]")
    private WebElement searchName;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchBtn;

    @FindBy(xpath = "(//button[@type='button'])[7]")
    private WebElement deleteBtn;

    @FindBy(xpath = "//*[text()=' Yes, Delete ']")
    private WebElement confirmDeletionBtn;

    @FindBy(xpath = "//button[@type='button' and contains(@class, 'oxd-button--ghost')]")
    private WebElement resetButton;

    public AdminPage() {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAdminButton() {
        wait.until(ExpectedConditions.elementToBeClickable(adminButton));
        adminButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("orangehrm-horizontal-padding")));
    }

    public String getRecordsCountText() {
        wait.until(ExpectedConditions.visibilityOf(recordsCountTextField));
        String text = recordsCountTextField.getText();
        logger.info("Records count text: {}", text);
        return text;
    }

    public void clickAddButton() {
        addButton.click();
    }

    public void selectRoles() {
        wait.until(ExpectedConditions.elementToBeClickable(roles));
        roles.click();
        roles.sendKeys(Keys.DOWN);
        roles.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(dropDownRoles));
        dropDownRoles.click();
    }

    public void selectStatus() {
        wait.until(ExpectedConditions.elementToBeClickable(status));
        status.click();
        wait.until(ExpectedConditions.elementToBeClickable(selectStatus));
        selectStatus.click();
    }

    public void searchEmployeeHint(String characterToSearch) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(employeeNameField));
        employeeNameField.click();
        employeeNameField.clear();
        employeeNameField.sendKeys(characterToSearch);
        Thread.sleep(1500);
        List<WebElement> suggestions = driver.findElements(By.xpath("//div[@class='oxd-autocomplete-option']"));
        if (suggestions.isEmpty()) {
            logger.error("No autocomplete suggestions found for input: {}", characterToSearch);
            throw new RuntimeException("No suggestions found for employee hint: " + characterToSearch);
        }
        employeeNameField.sendKeys(Keys.DOWN);
        employeeNameField.sendKeys(Keys.ENTER);
    }

    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        PWField.sendKeys(password);
    }

    public void enterConfirmPassword(String password) {
        confirmPWField.sendKeys(password);
    }

    public void clickSaveButton() {
        saveBtn.click();
    }

    public void enterSearchName(String username) {
        searchName.sendKeys(username);
    }

    public void clickSearchButton() {
        searchBtn.click();
    }

    public void clickDeleteButton() {
        deleteBtn.click();
    }

    public void clickConfirmDeletionButton() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeletionBtn));
        confirmDeletionBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(confirmDeletionBtn));
    }

    public void clickResetButton() {
        logger.info("Clicking reset button");
        wait.until(ExpectedConditions.elementToBeClickable(resetButton));
        resetButton.click();
    }

    public void setInitialCount(String textToExtract) {
        initialCount = extractNumber(textToExtract);
        if (initialCount != null) {
            logger.info("Initial number of records: {}", initialCount);
            ConfigReaderWriter.setDynamicValue("initialCount", initialCount.toString());
        } else {
            logger.warn("No number found in the initial count text: {}", textToExtract);
            ConfigReaderWriter.setDynamicValue("initialCount", "0");
        }
    }

    public Integer getCurrentCount(String textToExtract) {
        Integer currentCount = extractNumber(textToExtract);
        if (currentCount != null) {
            logger.info("Current number of records: {}", currentCount);
        } else {
            logger.warn("No number found in the text: {}", textToExtract);
        }
        return currentCount;
    }



    private Integer extractNumber(String textToExtract) {
        if (textToExtract == null || textToExtract.trim().isEmpty()) {
            logger.info("Text is empty or null, returning 0");
            return 0;
        }
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(textToExtract);
        if (matcher.find()) {
            Integer number = Integer.valueOf(matcher.group(1));
            logger.info("Extracted number: {}", number);
            return number;
        }
        logger.info("No numeric value found in text: {}, returning 0", textToExtract);
        return 0;
    }
}