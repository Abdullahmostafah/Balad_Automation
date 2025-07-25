package StepDefinitions;

import Base.TestBase;
import Pages.AdminPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import Utils.ConfigReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminPanelSteps extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(AdminPanelSteps.class);
    private AdminPage adminPage;
    private Integer recordsAfterCreation;

    public AdminPanelSteps() {
        adminPage = new AdminPage();
    }

    @When("I add a new user with username {string} and password {string}")
    public void i_add_a_new_user(String employeeUsername, String employeePassword) throws InterruptedException {
        adminPage.clickAddButton();
        adminPage.searchEmployeeHint("a");
        adminPage.enterUsername(employeeUsername);
        adminPage.enterPassword(employeePassword);
        adminPage.enterConfirmPassword(employeePassword);
        adminPage.selectStatus();
        adminPage.selectRoles();
        adminPage.clickSaveButton();
    }

    @Then("the record count should increase by 1")
    public void the_record_count_should_increase_by_1() {
        String recordsAfterCreationText = adminPage.getRecordsCountText();
        recordsAfterCreation = adminPage.getCurrentCount(recordsAfterCreationText);
        Integer initialCount = Integer.valueOf(ConfigReaderWriter.getDynamicValue("initialCount"));
        logger.info("Initial count: {}", initialCount);
        logger.info("Count after creation: {}", recordsAfterCreation);
        Integer expectedCountAfterCreation = initialCount + 1;
        softAssert.assertEquals(recordsAfterCreation, expectedCountAfterCreation,
                "Record count did not increase by 1. Expected: " + expectedCountAfterCreation +
                        ", Actual: " + recordsAfterCreation);
        softAssert.assertAll();
    }

    @When("I search for {string}")
    public void i_search_for_user(String employeeUsername) {
        logger.info("Searching for username: {}", employeeUsername);
        adminPage.enterSearchName(employeeUsername);
        adminPage.clickSearchButton();
    }

    @And("I delete the user")
    public void i_delete_the_user() {
        adminPage.clickDeleteButton();
        adminPage.clickConfirmDeletionButton();
    }

    @And("I reset the search results")
    public void i_reset_the_search_results() {
        adminPage.clickResetButton();
    }

    @Then("the record count should decrease by 1")
    public void the_record_count_should_decrease_by_1() {
        String recordsAfterDeletionText = adminPage.getRecordsCountText();
        Integer recordsAfterDeletion = adminPage.getCurrentCount(recordsAfterDeletionText);
        Integer initialCount = Integer.valueOf(ConfigReaderWriter.getDynamicValue("initialCount"));
        logger.info("Initial count: {}", initialCount);
        logger.info("Count after deletion: {}", recordsAfterDeletion);
        Integer expectedCountAfterDeletion = initialCount;
        if (recordsAfterCreation == null || recordsAfterCreation != initialCount + 1) {
            logger.error("User addition not verified. Records after creation: {}", recordsAfterCreation);
            softAssert.fail("User addition not verified before deletion. Records after creation: " + recordsAfterCreation);
        } else if (recordsAfterDeletion == null) {
            logger.error("Failed to retrieve record count after deletion. Text: {}", recordsAfterDeletionText);
            softAssert.fail("Record count is null after deletion. Text: " + recordsAfterDeletionText);
        } else {
            softAssert.assertEquals(recordsAfterDeletion, expectedCountAfterDeletion,
                    "Record count did not return to initial value. Expected: " + expectedCountAfterDeletion +
                            ", Actual: " + recordsAfterDeletion);
        }
        softAssert.assertAll();
    }
}