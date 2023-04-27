package UITestsBeymenCom.PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.ReusableMethods;

public class HomePage {
    private Actions actions;
    public HomePage(WebDriver driver){
        actions = new Actions(driver);
        PageFactory.initElements(driver , this);
    }
    @FindBy(id = "onetrust-accept-btn-handler")
    public WebElement cerezleriKabulEtBtn;
    @FindBy(css = ".bwi-close svg")
    public WebElement cerezleriKapatBtn;
    @FindBy(css = "input.o-header__search--input")
    public WebElement searchArea;
    public void cerezleriKabulEtVeKapat(){
        ReusableMethods.waitElementVisibleAndClick(cerezleriKapatBtn);
        ReusableMethods.waitElementVisibleAndClick(cerezleriKabulEtBtn);
    }
    public void inputToSearchArea(String key){
        actions.sendKeys(searchArea,key).perform();
    }
}
