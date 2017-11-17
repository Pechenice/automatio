package pages;

import controls.Button;
import controls.Text;
import core.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class RepoSettingsPage extends BasePage {
    private Button button_DeleteRepo() {return Button.byCss("button[data-facebox='#delete_repo_confirm']");}
    private Text text_RepoNameForConfirmationPopup() {return Text.byCss(".facebox-popup input[type='text']");}
    private Button button_DeleteFromConfirmationPopup() {return Button.byCss(".facebox-popup button[type='submit']");}

    public Button deleteRepoButton() {
        return button_DeleteRepo();
    }

    public HomeLoggedPage deleteRepo(String repoName) {
        text_RepoNameForConfirmationPopup().sendKeys(repoName);
        button_DeleteFromConfirmationPopup().click();
        HomeLoggedPage homeLoggedPage = new HomeLoggedPage();
        try {
            homeLoggedPage.verifyHomeLoggedPage();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return homeLoggedPage;
    }

    protected void verifySettings() throws Exception {
        List<WebElement> listOfElementsToCheck = Driver.get().findElements(By.cssSelector("button[data-facebox='#delete_repo_confirm']"));
        if (listOfElementsToCheck.size() == 0) {
            throw new Exception("Delete this repository button is absent on Repo Settings page.");
        }
        WebElement deleteRepo = listOfElementsToCheck.get(0);
        if (!deleteRepo.isEnabled()) {
            throw new Exception("Delete this repository button is not clickable on Repo Settings page.");
        }
    }
}
