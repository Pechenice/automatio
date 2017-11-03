package pages;

import controls.ButtonControl;
import core.PropertiesContainer;

public class StartLoggedPage extends BasePage {
    private ButtonControl createRepo;
    private ButtonControl profile;
    private ButtonControl editingProfile;

    public StartLoggedPage() {
        createRepo = ButtonControl.findButtonByCss("a[href='/new'][class^='btn'][data-ga-click^='Hello World']");
        profile = ButtonControl.findButtonByCss("summary[class^='HeaderNavlink']>img[alt='@"+ PropertiesContainer.get("test.login")+"']");
    }

    public RepoCreationPage goToRepoCreation() {
        createRepo.click();
        return new RepoCreationPage();
    }

    public ProfilePage goToProfilePage() {
        profile.click();
        editingProfile = ButtonControl.findButtonByCss("a[href='/" +PropertiesContainer.get("test.login")+ "']");
        editingProfile.click();
        return new ProfilePage();
    }
}