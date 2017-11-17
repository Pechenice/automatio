package pages;

import controls.Button;
import core.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class RepoOpenedPage extends BasePage {
    private Button button_Settings() {return Button.byCss("a[data-selected-links$='settings']");}
    private Button button_NotificationsDropDown() {return Button.byCss("a[class$='js-menu-target']");}
    private Button button_Issues() {return Button.byCss("span a[href$='/issues']");}
    private Button button_Watch() {return Button.byXpath("//input[@value='subscribed']/..");}
    private Button button_UnWatch() {return Button.byXpath("//input[@value='included']/..");}
    private Button button_Ignore() {return Button.byCss("//input[@value='ignore']/..");}
    private Button button_SetUpInDesktop() {return Button.byCss("a[href='https://desktop.github.com']");}

    public enum subscriptionTypes {Watch, UnWatch, Ignore}

    public RepoSettingsPage goToSettings() {
        button_Settings().click();
        RepoSettingsPage settingsPage = new RepoSettingsPage();
        try {
            settingsPage.verifySettings();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return settingsPage;
    }

    public IssuesPage goToIssues() {
        button_Issues().click();
        IssuesPage issuesPage = new IssuesPage();
        try {
            issuesPage.verifyIssuePage();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return issuesPage;
    }

    public void subscription(subscriptionTypes subscriptionTypes) {
        button_NotificationsDropDown().click();
        switch (subscriptionTypes) {
            case Watch: button_Watch().moveAndClickElement();
                break;
            case UnWatch: button_UnWatch().moveAndClickElement();
                break;
            case Ignore: button_Ignore().moveAndClickElement();
                break;
            default:
                throw new IllegalStateException("Such type does not exist");
        }
    }

    protected void verifyOpenedRepoPage() throws Exception {
        List<WebElement> listOfElementsToCheck = Driver.get().findElements(By.cssSelector("a[href='https://desktop.github.com']"));
        if (listOfElementsToCheck.size()==0) {
            throw new Exception("Set up in Desktop button is absent on opened certain Repository Page.");
        }
        WebElement setUpInDesktop = listOfElementsToCheck.get(0);
        if (!setUpInDesktop.isEnabled()) {
            throw new Exception("Set up in Desktop button is not clickable on opened certain Repository Page.");
        }
    }

}
