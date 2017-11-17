package pages;

import controls.Button;
import controls.Link;
import controls.Text;
import core.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class IssueCreationPage extends BasePage{
    private Button button_Assignees() {
        return Button.byCss("button[aria-label^='Assign']");
    }
    private Button button_Labels() {
        return Button.byCss("button[aria-label^='Apply']");
    }
    private Text text_TitleField() {
        return Text.byCss("input[aria-label='Title']");
    }
    private Button button_SubmitNewIssue() {
        return Button.byCss(".form-actions button[type='submit']");
    }
    private Text text_DescriptionField() {
        return Text.byCss("#issue_body");
    }
    private Button button_CommentSubmit() {
        return Button.byCss("#partial-new-comment-form-actions button[type='submit']");
    }
    public enum typesOfIssue {Bug, Duplicate, Enhancement, FirstIssue, HelpWanted, Invalid, Question, Wontfix}
    private Button button_Bug() {return Button.byCss("div[data-name='bug']");}
    private Button button_Duplicate() {return Button.byCss("div[data-name='duplicate']");}
    private Button button_Enhancement() {return Button.byCss("div[data-name='enhancement']");}
    private Button button_FirstIssue() {return Button.byCss("div[data-name^='good']");}
    private Button button_HelpWanted() {return Button.byCss("div[data-name^='help']");}
    private Button button_Invalid() {return Button.byCss("div[data-name='invalid']");}
    private Button button_Question() {return Button.byCss("div[data-name='question']");}
    private Button button_Wontfix() {return Button.byCss("div[data-name='wontfix']");}
    private Button button_ShutDownLabels() {return Button.byCss(".js-new-issue-labels-container svg[aria-label='Close'][class^='octicon']");}
    private Link link_StylingInfo() {return Link.byCss(".tabnav-extra");}


    public void leaveCommentToUser(String toUser, String comment) {
        Text commentField = Text.byCss("#new_comment_field");
        commentField.sendKeys("@"+toUser +" "+ comment);
        button_CommentSubmit().click();
    }

    public void makeAssigneer(String nameOfAssigneer) {
        button_Assignees().click();
        Button selfAssign = Button.byCss("div[data-contents-url^='/"+nameOfAssigneer+"/'] div[role='menuitem']");
        selfAssign.waitForElementVisible();
        selfAssign.moveAndClickElement();
        Button shutDownAssigneer = Button.byCss("div[data-contents-url^='/"+ nameOfAssigneer +"/'] svg[aria-label='Close'][class^='octicon']");
        shutDownAssigneer.moveAndClickElement();
    }

    public void markWithLabel(List<typesOfIssue> typesList) {
        button_Labels().click();
        for (typesOfIssue typesOfIssue: typesList) {
            switch (typesOfIssue) {
                case Bug: button_Bug().click(); break;
                case Duplicate: button_Duplicate().click(); break;
                case Enhancement: button_Enhancement().click(); break;
                case FirstIssue: button_FirstIssue().click(); break;
                case HelpWanted: button_HelpWanted().click(); break;
                case Invalid: button_Invalid().click(); break;
                case Question: button_Question().click(); break;
                case Wontfix: button_Wontfix().click(); break;
                default: throw new IllegalStateException("No such element");
            }
        }
        button_ShutDownLabels().click();
    }

    public IssuesPage fillTheIssue(String name, String description) {
        text_TitleField().sendKeys(name);
        text_DescriptionField().sendKeys(description);
        button_SubmitNewIssue().click();
        IssuesPage issuesPage = new IssuesPage();
        try {
            issuesPage.verifyIssuePage();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return issuesPage;
    }

    protected void verifyIssueCreation() throws Exception {
        List<WebElement> listOfElementsToCheck = Driver.get().findElements(By.cssSelector(".tabnav-extra"));
        if (listOfElementsToCheck.size() == 0) {
            throw new Exception("Styling information link absent on Issue Creation Page.");
        }
        WebElement stylingInfo = listOfElementsToCheck.get(0);
        if (!stylingInfo.isDisplayed()) {
            throw new Exception("Styling information link is not visible on Issue Creation Page.");
        }
    }
}
