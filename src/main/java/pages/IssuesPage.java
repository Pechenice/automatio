package pages;

import controls.Button;
import core.Driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class IssuesPage extends BasePage {
    private WebElement searchResult;
    private Button button_NewIssue() {return Button.byCss(".subnav a[href$='/issues/new']");}
    private Button button_IssueTabIsActive() {return Button.byCss("span a[href$='/issues'][class~='selected']");}
    private List<WebElement> element_AllSearchedResults(String userName, String repoName) {return Driver.get().findElements(By.cssSelector("div[class^='float-left'] a[href^='/"+userName+"/"+repoName+"/issues/']"));}

    public WebElement getSearchResult() {
        return searchResult;
    }

    public IssuesPage() {
    }

    public IssuesPage(WebElement element) {
        searchResult = element;
    }


    public IssueCreationPage goToIssueCreation() {
        button_NewIssue().click();
        IssueCreationPage issueCreationPage = new IssueCreationPage();
        try {
            issueCreationPage.verifyIssueCreation();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return issueCreationPage;
    }

    public List<IssuesPage> getCreatedIssue(String userName, String repoName) {
        List<IssuesPage> issuesPages = new ArrayList<>();
        for (WebElement element: element_AllSearchedResults(userName, repoName)) {
            IssuesPage issuesPage = new IssuesPage(element);
            issuesPages.add(issuesPage);
        }
        return issuesPages;
    }

    protected void verifyIssuePage() throws Exception {
        List<WebElement> listOfElementsToCheck = Driver.get().findElements(By.cssSelector("span a[href$='/issues'][class~='selected']"));
        if (listOfElementsToCheck.size() == 0) {
            throw new Exception("Button New Issue is not present on Issue Page.");
        }
        WebElement issueTable = listOfElementsToCheck.get(0);
        if (!issueTable.isEnabled()) {
            throw new Exception("Button New Issue is not clickable on Issue Page.");
        }
    }

}
