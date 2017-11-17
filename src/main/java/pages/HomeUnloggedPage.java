package pages;

import controls.Button;
import core.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class HomeUnloggedPage extends BasePage {
    private Button button_SignIn() {return Button.byCss("a[href='/login']");}

    public LoginPage goToLoginPage() {
        button_SignIn().click();
        LoginPage loginPage = new LoginPage();
        try {
            loginPage.verifyLoginPage();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return loginPage;
    }

    protected void verifyHomeUnloggedPage() throws Exception {
        List<WebElement> listOfElementsToCheck = Driver.get().findElements(By.cssSelector("a[href='/login']"));
        if (listOfElementsToCheck.size() == 0) {
            throw new Exception("Sign In button is absent on Unlogged Home Page.");
        }
        WebElement signIn = listOfElementsToCheck.get(0);
        if (!signIn.isEnabled()) {
            throw new Exception("Sign In button is not clickable on Unlogged Home Page.");
        }
    }
}
