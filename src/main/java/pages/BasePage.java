package pages;

import core.Driver;

public class BasePage {

    public String getTitleText() {
        return Driver.get().getTitle();
    }

    public String getCurrentUrl() {
        return Driver.get().getCurrentUrl();
    }

    public void open(String url) {
        Driver.get().navigate().to(url);
    }
}
