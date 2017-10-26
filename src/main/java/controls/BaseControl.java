package controls;

import core.Driver;
import core.PropertiesContainer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseControl {
    protected WebElement element;
    protected By by;

    public void click () {
        element.click();
    }

//    public void waitForElement(By by) {
//        new WebDriverWait(Driver.get(), Long.parseLong(PropertiesContainer.get("test.timeout"))).until(new ExpectedCondition<Boolean>() {
//            public Boolean apply (WebDriver input) {
//                return input.findElements(by).size() == 1;
//            }
// });

    public void waitForElementVisible () {

    }

    public void waitForElementInvisible() {

    }

    public void isElementClickable () {

    }

    public void isElementPresent() {

    }

    public void isElementNotPresent () {
}
}