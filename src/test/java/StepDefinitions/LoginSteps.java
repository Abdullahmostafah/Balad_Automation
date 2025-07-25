package StepDefinitions;

import Base.TestBase;
import Pages.AdminPage;
import Pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps extends TestBase {
    private LoginPage loginPage;
    private AdminPage adminPage;

    public LoginSteps() {
        loginPage = new LoginPage();
        adminPage = new AdminPage();
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        openWebSite();
        loginPage = new LoginPage();
        adminPage = new AdminPage();
    }

    @When("I log in with username {string} and password {string}")
    public void i_log_in(String username, String password) throws InterruptedException {
        loginPage.performLogin(username, password);
        Thread.sleep(2000); // Wait for login to complete
    }

    @And("I navigate to the Admin tab")
    public void i_navigate_to_the_admin_tab() throws InterruptedException {
        adminPage.clickAdminButton();
        Thread.sleep(2000); // Wait for admin page to load
    }

    @Then("I note the initial record count")
    public void i_note_the_initial_record_count() {
        String recordsText = adminPage.getRecordsCountText();
        adminPage.setInitialCount(recordsText);
    }
}