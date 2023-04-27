package UITestsBeymenCom.PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.ReusableMethods;

import java.util.List;

public class ProductsPage {
    public ProductsPage(WebDriver driver){
        PageFactory.initElements(driver , this);
    }
    @FindBy(css = ".m-productCard__photo a")
    List<WebElement> allProductsList;
    @FindBy (xpath = "(//*[@class='m-productCard__photo'])[48]")
    WebElement lastProduct;
    public void selectRandomProduct(){
       /*Math.random() : 0-1 arasında rastgele sayı üretir.
       Bu sayıyı ürün sayısı ile çarpar ve 1 eklersek ürünlerden rastgele bir ürün alabiliriz.
        */
        int randomProductIndex = (int)(Math.random()*allProductsList.size()+1);
        allProductsList.get(randomProductIndex).click();
    }
    public void selectFirstProduct(){
        /*
        ÖNEMLİ NOT
        Verilen task te ürün sayısının artırılması isteniyor.
        Ancak maalesef her üründen 2 adet bulunmamaktadır.
        Testlerde sonucunun yanıltıcı olmaması için
        test kodlarında bu method kullanılmıştır.
         */
        allProductsList.get(0).click();
    }
    public void waitLastProductVisible(){
        ReusableMethods.waitElementIsVisible(lastProduct);
    }
}
