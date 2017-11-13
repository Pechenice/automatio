package core;

import helpers.TestHelper;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pages.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestBase {
    private String userLogin = PropertiesContainer.get("test.login");
    private String userPassword = PropertiesContainer.get("test.password");
    private String repoName;
    private Boolean needToRelogin = false;
    protected enum actions {deleteRepo, resetProfileInfo}
    protected List<actions> deleteAfterTest = new ArrayList<>();

    @BeforeTest
    public void setUp() {
        Driver.init();
        Driver.get().manage().window().maximize();
        Driver.get().manage().timeouts().implicitlyWait(Long.parseLong(PropertiesContainer.get("test.timeout")), TimeUnit.SECONDS);
        Driver.get().get(PropertiesContainer.get("test.baseUrl"));
        TestHelper helper = new TestHelper();
        helper.logIn(PropertiesContainer.get("test.login"), PropertiesContainer.get("test.password"));
    }

    @AfterTest
    public void tearDown() {
        clearUp(deleteAfterTest);
        Driver.quit();
    }

    public void clearUp(List<actions> deleteAfterTest) {
        for (actions actionsList: deleteAfterTest) {
            switch (actionsList) {
                case deleteRepo: deleteRepo(); break;
                case resetProfileInfo: resetProfileInfo(); break;
            }
        }
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    private void deleteRepo() {
        if (needToRelogin) {
            HomeUnloggedPage homeUnloggedPage = new HomeLoggedPage().signOut();
            TestHelper helper = new TestHelper();
            helper.logIn(userLogin, userPassword);
        }
        Driver.get().navigate().to("https://github.com/"+userLogin+"/"+repoName);
        RepoSettingsPage settingsPage = new RepoOpenedPage().goToSettings();
        settingsPage.deleteRepoButton().click();
        settingsPage.deleteRepo(repoName);
    }

    private void resetProfileInfo() {
        Driver.get().get("https://github.com/settings/profile");
        EditProfilePage editProfilePage = new EditProfilePage();
        editProfilePage.upDateAvatar(new File("src/main/resources/no_unicorn.png").getAbsolutePath()).deleteAllInfoFromProfile();
    }

    public void setNeedToRelogin(Boolean needToRelogin) {
        this.needToRelogin = needToRelogin;
    }
}
