package controls;

import org.openqa.selenium.By;

public class TextControl extends BaseControl {

    public TextControl(By by) {
        super(by);
    }

    public static TextControl findTextById(String id) {
        return new TextControl(By.id(id));
    }

    public static TextControl findTextByName(String name) {
        return new TextControl(By.name(name));
    }

    public static TextControl findTextByClassName(String className) {
        return new TextControl(By.className(className));
    }

    public static TextControl findTextByTagName(String tagName) {
        return new TextControl(By.tagName(tagName));
    }

    public static TextControl findTextByLinkText(String linkText) {
        return new TextControl(By.linkText(linkText));
    }

    public static TextControl findTextByPartText(String partText) {
        return new TextControl(By.partialLinkText(partText));
    }

    public static TextControl findTextByCss(String css) {
        return new TextControl(By.cssSelector(css));
    }

    public static TextControl findTextByXpath(String xpath) {
        return new TextControl(By.xpath(xpath));
    }

    public String getText() {
        return element.getText();
    }

    public void clear() {
        element.clear();
    }

    public void sendKeys(String text) {
        clear();
        element.sendKeys(text);
    }
}
