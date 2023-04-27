package UITestsBeymenCom.PageClasses;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class BasketPage {

    public BasketPage(WebDriver driver){
        PageFactory.initElements(driver , this);
    }
    @FindBy(className = "m-productPrice__salePrice")
    public WebElement productPriceOnBasket;
    @FindBy(id = "quantitySelect0-key-0")
    public WebElement productCountSelect;
    @FindBy(id = "removeCartItemBtn0-key-0")
    public WebElement removeProductBtn;
    @FindBy(css="#emtyCart .m-empty__messageTitle")
    public WebElement cartIsEmptyText;
    public void selectProductCount(int count){
        Select select = new Select(productCountSelect);
        select.selectByValue(String.valueOf(count));
    }
    public void assertSelectedProductCountIs(int count){
        Select select = new Select(productCountSelect);
        Assert.assertEquals(select.getFirstSelectedOption().getAttribute("value"), String.valueOf(count));
    }
}
