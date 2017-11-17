package pages;

import controls.Button;
import controls.Link;
import controls.Text;
import core.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class LoginPage extends BasePage {
    private Text text_LoginField() {return Text.byCss("#login_field");}
    private Text text_PasswordField() {return Text.byCss("#password");}
    private Button button_SignIn() {return Button.byCss("input[name='commit']");}
    private Button button_ForgotPassword() {return Button.byCss(".label-link");}
    private Link link_CreateNewAccount() {return Link.byCss("a[href='/join?source=login']");}

    public HomeLoggedPage logIn(String login, String password) {
        text_LoginField().sendKeys(login);
        text_PasswordField().sendKeys(password);
        button_SignIn().click();
        HomeLoggedPage homeLoggedPage = new HomeLoggedPage();
        try {
            homeLoggedPage.verifyHomeLoggedPage();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return homeLoggedPage;
    }

    protected void verifyLoginPage() throws Exception {
        List<WebElement> listOfElementsToCheck = Driver.get().findElements(By.cssSelector("a[href='/join?source=login']"));
        if (listOfElementsToCheck.size() == 0) {
            throw new Exception("Create an account link is absent on Login Page.");
        }
        WebElement createNewAccount = listOfElementsToCheck.get(0);
        if (!createNewAccount.isDisplayed()) {
            throw new Exception("Create an account link is not visible on Login Page.");
        }
    }

}
