package pages;

import controls.Button;
import controls.Link;
import controls.Text;
import core.Driver;
import core.PropertiesContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class EditProfilePage extends BasePage {
    private Text text_Name() {return Text.byCss("#user_profile_name");}
    private Text text_Bio() {return Text.byCss("#user_profile_bio");}
    private Text text_Company() {return Text.byCss("#user_profile_company");}
    private Button button_uploadProfilePicture() {return Button.byCss("#upload-profile-picture");}
    private Button button_UpdateProfile(){return Button.byCss("button[type='submit'][class$='btn-primary']");}
    private Link link_ImageChecker() {return Link.byCss("img[class$='rounded-2']");}
    private Link link_GoToProfileAfterUpdate(){return Link.byCss("button+a[href='/"+ PropertiesContainer.get("test.login")+"']");}
    private Text text_PrivateContributions() {return Text.byXpath("//label[@for='user_show_private_contribution_count']");}

    public EditProfilePage upDateProfile(String name, String bio, String company) {
        if (name != null) {
            this.text_Name().clear();
            this.text_Name().sendKeys(name);
        }
        if (bio != null) {
            this.text_Bio().clear();
            this.text_Bio().sendKeys(bio);
        }
        if (company != null) {
            this.text_Company().clear();
            this.text_Company().sendKeys(company);
        }
        button_UpdateProfile().click();
        EditProfilePage editProfilePage = new EditProfilePage();
        try {
            editProfilePage.verifyEditProfile();
        } catch (Exception e) {
            Assert.fail(e.getMessage()  );
        }
        return editProfilePage;
    }

    public EditProfilePage upDateAvatar(String pass) {
        button_uploadProfilePicture().sendKeys(pass);
        Button.byCss("button[type='submit'][value='save']").click();
        return new EditProfilePage();
    }

    public EditProfilePage deleteAllInfoFromProfile() {
        text_Name().clear();
        text_Bio().clear();
        text_Company().clear();
        button_UpdateProfile().click();
        EditProfilePage editProfilePage = new EditProfilePage();
        try {
            editProfilePage.verifyEditProfile();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return editProfilePage;
    }

    public ProfilePage goToProfileAfterUpdate() {
        link_GoToProfileAfterUpdate().click();
        ProfilePage profilePage = new ProfilePage();
        try {
            profilePage.verifyProfile();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return profilePage;
    }

    public Link getImageChecker() {
        return link_ImageChecker();
    }

    protected void verifyEditProfile() throws Exception {
        List<WebElement> listOfElementsToCheck = Driver.get().findElements(By.xpath("//label[@for='user_show_private_contribution_count']"));
        if (listOfElementsToCheck.size() == 0) {
            throw new Exception("Checkbox with private contributions is absent on Editing Profile Page.");
        }
        WebElement oneElement = listOfElementsToCheck.get(0);
        if (!oneElement.isDisplayed()) {
            throw new Exception("Checkbox with private contributions is not displayed on Editing Profile Page.");
        }
    }
}
