package pages;

import controls.Link;
import core.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchResultRepoPage extends BasePage {
    private List<WebElement> element_ListOfSearchedElements() {
        return Driver.get().findElements(By.cssSelector("div[class^='repo-list-item'] a"));
    }
    private WebElement searches;
    private Link link_AdvancedSearch() {return Link.byCss(".muted-link");}

    public SearchResultRepoPage() {}

    public SearchResultRepoPage(WebElement element) {
            searches = element;
    }

    public WebElement getSearches() {
        return searches;
    }


    public List<SearchResultRepoPage> resultsOfSearch() {
         List<SearchResultRepoPage> searchResultPages = new ArrayList<>();
         for (WebElement elements: element_ListOfSearchedElements()) {
             SearchResultRepoPage searchResult = new SearchResultRepoPage(elements);
             searchResultPages.add(searchResult);
         }
         return searchResultPages;
     }

    protected void verifySearchRepoResults() throws Exception {
        List<WebElement> listOfElementsToCheck = Driver.get().findElements(By.cssSelector(".muted-link"));
        if (listOfElementsToCheck.size()==0) {
            throw new Exception("Advanced search link is absent on Repository Searched Results.");
        }
        WebElement advancedSearch = listOfElementsToCheck.get(0);
        if (!advancedSearch.isEnabled()) {
            throw new Exception("Advanced search link is not clickable on Repository Searched Results.");
        }
    }
}
