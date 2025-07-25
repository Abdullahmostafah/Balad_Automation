package Pages;

import Base.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends TestBase {
    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "username")
    private WebElement userNameField;

    @FindBy(name = "password")
    private WebElement passWordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;


    public void performLogin(String username, String password) {
        userNameField.sendKeys(username);
        passWordField.sendKeys(password);
        loginButton.click();
    }
}