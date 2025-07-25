package StepDefinitions;

import Base.TestBase;
import Pages.AdminPage;
import Pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginSteps extends TestBase {
    private LoginPage loginPage;
    private AdminPage adminPage;
    private final WebDriverWait wait;

    public LoginSteps() {
        loginPage = new LoginPage();
        adminPage = new AdminPage();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        openWebSite();
        loginPage = new LoginPage();
        adminPage = new AdminPage();
    }

    @When("I log in with username {string} and password {string}")
    public void i_log_in(String username, String password) {
        loginPage.performLogin(username, password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("oxd-main-menu")));
    }

    @And("I navigate to the Admin tab")
    public void i_navigate_to_the_admin_tab() {
        adminPage.clickAdminButton();
    }

    @Then("I note the initial record count")
    public void i_note_the_initial_record_count() {
        String recordsText = adminPage.getRecordsCountText();
        adminPage.setInitialCount(recordsText);
    }
}