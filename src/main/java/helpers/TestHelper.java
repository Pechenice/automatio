package helpers;

import pages.HomeLoggedPage;
import pages.HomeUnloggedPage;

import java.util.Random;

public class TestHelper {
    public HomeLoggedPage logIn(String userLogin, String userPassword) {
        HomeUnloggedPage startUnloggedPagePage = new HomeUnloggedPage();
        return startUnloggedPagePage.goToLoginPage().logIn(userLogin, userPassword);
    }

}
