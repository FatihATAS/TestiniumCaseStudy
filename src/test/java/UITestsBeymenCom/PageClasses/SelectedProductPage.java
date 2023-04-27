package UITestsBeymenCom.PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SelectedProductPage {
    public SelectedProductPage(WebDriver driver){
        PageFactory.initElements(driver , this);
    }
    @FindBy(css = "h1.o-productDetail__title a")
    public WebElement productMark;
    @FindBy(css = "h1.o-productDetail__title span")
    public WebElement productDetailedInfo;
    @FindBy(className = "m-price__list")
    public WebElement productPrice;
    @FindBy(className = "m-variation__item")
    List<WebElement> variationList;
    @FindBy(id = "addBasket")
    public WebElement addBasketBtn;
    @FindBy(className = "m-notification__message")
    public WebElement alertAddProductInfo;
    @FindBy(css = ".m-notification__button.btn")
    public WebElement goToBasketBtn;

    public void selectRandomVariation(){
        int randomIndex = (int) (Math.random()*variationList.size());
        variationList.get(randomIndex).click();
    }
}
