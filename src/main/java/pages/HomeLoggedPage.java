package pages;

import controls.Button;
import controls.Text;
import core.Driver;
import core.PropertiesContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class HomeLoggedPage extends BasePage {
    private Button button_SignOut() {
        return Button.byCss("li button[type='submit']");
    }
    private Button button_EditingProfile() {
        return Button.byCss("a[href='/" +PropertiesContainer.get("test.login")+ "']");
    }
    private Button button_Profile() {
        return Button.byCss("summary[class^='HeaderNavlink']>img[alt^='@']");
    }
    private Button button_CreateRepo() {
        return Button.byCss("a[href='/new'][class^='btn'][data-ga-click^='Hello World']");
    }
    private Text text_SearchField() {
        return Text.byCss("input[class^='form-control']");
    }

    public RepoCreationPage goToRepoCreation() {
        button_CreateRepo().click();
        RepoCreationPage repoCreationPage = new RepoCreationPage();
        try {
            repoCreationPage.verifyRepoCreation();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return repoCreationPage;
    }

    public ProfilePage goToProfilePage() {
        button_Profile().click();
        button_EditingProfile().click();
        ProfilePage profilePage = new ProfilePage();
        try {
            profilePage.verifyProfile();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return profilePage;
    }

    public HomeUnloggedPage signOut() {
        button_Profile().click();
        button_SignOut().click();
        HomeUnloggedPage homeUnloggedPage = new HomeUnloggedPage();
        try {
            homeUnloggedPage.verifyHomeUnloggedPage();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return homeUnloggedPage;
    }

    public SearchResultRepoPage search(String search) {
        text_SearchField().sendKeys(search);
        text_SearchField().pressEnter();
        SearchResultRepoPage searchRepoResultPage = new SearchResultRepoPage();
        try {
            searchRepoResultPage.verifySearchRepoResults();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return searchRepoResultPage;
    }

    protected void verifyHomeLoggedPage() throws Exception {
        List<WebElement> listOfElementsToCheck = Driver.get().findElements(By.cssSelector("a[href='/new'][class^='btn'][data-ga-click^='Hello World']"));
        if (listOfElementsToCheck.size() == 0) {
            throw new Exception("Start a project button is absent on Logged Home Page.");
        }
        WebElement createRepo = listOfElementsToCheck.get(0);
        if (!createRepo.isDisplayed()) {
            throw new Exception("Start a project button on Logged Home Page is not visible.");
        }
    }
}
