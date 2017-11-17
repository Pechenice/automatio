
import controls.Base;
import core.PropertiesContainer;
import core.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static helpers.DataGenerator.stringGenerator;

public class GitHubTest extends TestBase {

    @Test
    public void logInTest() {
        HomeLoggedPage startLoggedPage = new HomeLoggedPage();
        Assert.assertEquals(startLoggedPage.getTitleText(), "GitHub", "Title is different.");
    }

    @Test
    public void createRepo() {
        RepoCreationPage repoCreationPagePage = new HomeLoggedPage().goToRepoCreation();
        String repoName = stringGenerator("RepoName");
        RepoOpenedPage repoPage = repoCreationPagePage.createRepo(repoName);
        setRepoName(repoName);
        deleteAfterTest.add(actions.deleteRepo);
        Assert.assertEquals(repoPage.getTitleText(), PropertiesContainer.get("test.login") + "/" + repoName, "Title is different.");
    }

    @Test
    public void updateProfile() {
        String profileName = stringGenerator("Unicorn");
        String profileBio = stringGenerator("Bio");
        String profileCompany = stringGenerator("Company");
        EditProfilePage editProfilePage = new HomeLoggedPage().goToProfilePage().goToEditProfilePage();
        String pictureBeforeUpdate = editProfilePage.getImageChecker().getAttribute("src");
        editProfilePage = editProfilePage.upDateAvatar(new File("src/main/resources/unicorn.jpg").getAbsolutePath());
        String pictureAfterUpdate = editProfilePage.getImageChecker().getAttribute("src");
        ProfilePage profilePage = editProfilePage.upDateProfile(profileName, profileBio, profileCompany).goToProfileAfterUpdate();
        deleteAfterTest.add(actions.resetProfileInfo);
        List<Base> listOfProfileInformation = profilePage.getProfileInformation();
        List<String> itemsText = new ArrayList<>();
        for (Base info : listOfProfileInformation) {
            itemsText.add(info.getText());
        }

        Assert.assertTrue(itemsText.contains(profileName), "Profile name is wrong.");
        Assert.assertTrue(itemsText.contains(profileBio), "Profile bio is absent.");
        Assert.assertTrue(itemsText.contains(profileCompany), "Profile company is absent.");
        Assert.assertTrue(!pictureAfterUpdate.equals(pictureBeforeUpdate), "Avatar picture was same, as previous one.");
    }

    @Test
    public void issueNotification() throws Exception {
        String repoName = stringGenerator("Nagibator");
        RepoOpenedPage repoPage = new HomeLoggedPage().goToRepoCreation().createRepo(repoName);
        setRepoName(repoName);
        deleteAfterTest.add(actions.deleteRepo);
        repoPage.subscription(RepoOpenedPage.subscriptionTypes.Watch);
        IssueCreationPage issueCreationPage = repoPage.goToIssues().goToIssueCreation();
        issueCreationPage.makeAssigneer(PropertiesContainer.get("test.login"));
        List<IssueCreationPage.typesOfIssue> allLabels = new ArrayList<>();
        allLabels.add(IssueCreationPage.typesOfIssue.Bug);
        allLabels.add(IssueCreationPage.typesOfIssue.FirstIssue);
        issueCreationPage.markWithLabel(allLabels);
        String issueTitle = stringGenerator("Danger!");
        String issueDescription = stringGenerator("Glavatar");
        IssuesPage issuesPage = issueCreationPage.fillTheIssue(issueTitle, issueDescription);
        String createdIssueUrl = issuesPage.getCurrentUrl();
        HomeLoggedPage loggedPage = new HomeLoggedPage().signOut().goToLoginPage().logIn(PropertiesContainer.get("test.otherlogin"), PropertiesContainer.get("test.otherpassword"));
        setNeedToRelogin(true);
        List<SearchResultRepoPage> searchResults = loggedPage.search(repoName).resultsOfSearch();
        SearchResultRepoPage resultPage = null;
        for (SearchResultRepoPage searchResult : searchResults) {
            String link = searchResult.getSearches().getAttribute("href");
            if (link.equals("https://github.com/"+PropertiesContainer.get("test.login")+"/"+repoName)) {
                resultPage = searchResult;
                break;
            }
        }
        if (resultPage == null) {
            throw new Exception("Searched repository wasn't found");
        }
        resultPage.getSearches().click();
        RepoOpenedPage anotherRepoPage = new RepoOpenedPage();
        List<IssuesPage> listOfIssuesPage = anotherRepoPage.goToIssues().getCreatedIssue(PropertiesContainer.get("test.login"), repoName);
        IssuesPage foundedIssuePage = null;
        for (IssuesPage onePage : listOfIssuesPage) {
            String issueUrl = onePage.getSearchResult().getAttribute("href");
            if (issueUrl.equals(createdIssueUrl)) {
                foundedIssuePage = onePage;
                break;
            }
        }
        foundedIssuePage.getSearchResult().click();
        IssueCreationPage openedIssue = new IssueCreationPage();
        openedIssue.leaveCommentToUser(PropertiesContainer.get("test.login"), stringGenerator("Sad"));
    }
}