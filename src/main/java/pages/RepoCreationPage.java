package pages;

import controls.Button;
import controls.Link;
import controls.Text;
import core.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class RepoCreationPage extends BasePage {
    private Button button_Owner() {return Button.byCss("div[class^='select-menu']>button[class^='btn'] div[class$='gravatar']");}
    private Text text_RepoName() {return Text.byCss("#repository_name");}
    private Text text_DescriptionField() {return Text.byCss("#repository_description");}
    private Button button_Submit() {return Button.byCss("button[type='submit'][class^='btn']");}
    private Link link_ValidationOfRepoName() {return Link.byCss("dl[class$='successed']");}

    public RepoOpenedPage createRepo(String repoNameInput) {
        return createRepo(repoNameInput, null);
    }

    public RepoOpenedPage createRepo(String repoNameInput, String description) {
        text_RepoName().sendKeys(repoNameInput);
        if (!(description ==null)) {
            text_DescriptionField().sendKeys(description);
        }
        link_ValidationOfRepoName().waitForElementPresent();
        button_Submit().click();
        RepoOpenedPage openedRepoPage = new RepoOpenedPage();
        try {
            openedRepoPage.verifyOpenedRepoPage();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return openedRepoPage;
    }
    protected void verifyRepoCreation() throws Exception {
        List<WebElement> listOfElementsToCheck = Driver.get().findElements(By.cssSelector("div[class^='select-menu']>button[class^='btn'] div[class$='gravatar']"));
        if (listOfElementsToCheck.size() == 0) {
            throw new Exception("Owner button is absent on Repository Creation Page");
        }
        WebElement owner = listOfElementsToCheck.get(0);
        if (!owner.isEnabled()) {
            throw new Exception("Owner button is not clickable on Repository Creation Page");
        }
    }

}
