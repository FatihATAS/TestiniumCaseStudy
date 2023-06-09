package UITestsBeymenCom;
import UITestsBeymenCom.BasePage.BasePage;
import UITestsBeymenCom.PageClasses.BasketPage;
import UITestsBeymenCom.PageClasses.HomePage;
import UITestsBeymenCom.PageClasses.ProductsPage;
import UITestsBeymenCom.PageClasses.SelectedProductPage;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Keys;
import utilities.ConfigReader;
import utilities.ExcelUtils;
import utilities.ReusableMethods;


public class BeymenTest extends BasePage {

    @Test
    public void beymenTest(){
        logger.info("https://beymen.com sitesine gidilir");
        driver.get(ConfigReader.getProperties("urlBeymen"));
        HomePage hp = new HomePage(driver);
        logger.info("Çerezler kabul edilir");
        hp.cerezleriKabulEtVeKapat();
        logger.info("Anasayfada olunduğu doğrulanır");
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Beymen.com"));
        logger.info("Excel'den veri alınacağı için dosya yolu tanımlanır ve verilerin bulunduğu sheetIndex girilir");
        String excelPath = ConfigReader.getProperties("excelFilePath");
        ExcelUtils excelUtils = new ExcelUtils(excelPath,0);
        /*
        Java ApachePoi de satır ve sutün indexleri 0'dan başladığı için
        1. satır ve 1. sütun da bulunan "şort" verisini almak için rowNumber 0 , colNumber 0 verildi
        1. satır ve 2. sütun da bulunan "gömlek" verisini almak için rowNumber 0 , colNumber 1 verildi
         */
        logger.info("Excel'den alınacak veriler (şort , gömlek) String değişkeni olarak tanımlanır");
        String firstSearchingProduct = excelUtils.getCellData(0,0);
        String secoundSearchingProduct = excelUtils.getCellData(0,1);
        logger.info("Datalar alındaktan sonra Excel kapatılır");
        excelUtils.closeExcelFile();
        logger.info("Arama kısmına 'şort' yazılır");
        hp.inputToSearchArea(firstSearchingProduct);
        logger.info("Arama kısmındaki 'şort kelimesi silinir'");
        hp.searchArea.clear();
        logger.info("Arama kısmına 'gömlek' yazılır ve Enter'a basılır");
        hp.inputToSearchArea(secoundSearchingProduct+Keys.ENTER);
        ProductsPage pp = new ProductsPage(driver);
        logger.warn("Sayfadaki bütün ürünlerin ekrana gelmesi için son ürünün yüklenmesi beklenir");
        pp.waitLastProductVisible();
        logger.warn("Sayfadaki ürünlerden rastgele seçim yapılır");
        pp.selectFirstProduct();
        /*
        ÖNEMLİ NOT
        Verilen task te ürün sayısının artırılması isteniyor.
        Ancak maalesef her üründen 2 adet bulunmamaktadır.
        Test sonuçlarının yanıltıcı olmaması için
        test kodlarında bu method kullanılmıştır.
        Ancak ProductsPage Class'ında rastgele ürün seçim methodu tanımlanmıştır.
         */
        logger.info("Ürün Bilgileri txt uzantılı dosyaya yazdırılır");
        SelectedProductPage spp = new SelectedProductPage(driver);
        String urunMarkasi = spp.productMark.getText();
        String urunAciklamasi = spp.productDetailedInfo.getText();
        String urunFiyatBilgisi = spp.productPrice.getText();
        String urunBilgileri = "Urun Markası = " + urunMarkasi + "\nUrun Aciklamasi = " + urunAciklamasi + "\nUrun Fiyat Bilgisi = " + urunFiyatBilgisi;
        ReusableMethods.writeDataToTxtFile(ConfigReader.getProperties("txtFilePath"),urunBilgileri);
        logger.info("Ürün eklemek için beden seçimi ve sepete ekleme işlemleri yapılır");
        spp.selectRandomVariation();
        ReusableMethods.waitElementVisibleAndClick(spp.addBasketBtn);
        logger.info("Ürünün sepete eklendiğini doğrulanır");
        Assert.assertTrue(spp.alertAddProductInfo.isDisplayed());
        logger.info("Sepete gitmek için 'Sepete Git' butonuna basılır");
        spp.goToBasketBtn.click();
        logger.info("Ürünün sepetteki fiyatı ile aynı olduğu doğrulanır");
        BasketPage bp = new BasketPage(driver);
        Assert.assertEquals(urunFiyatBilgisi , bp.productPriceOnBasket.getText());
        logger.info("Ürün adeti artırma işlemi yapılır");
        bp.selectProductCount(2);
        logger.info("Ürün adetinin 2 adet seçtiği doğrulanır");
        bp.assertSelectedProductCountIs(2);
        logger.info("Ürün sepetten kaldırılır");
        bp.removeProductBtn.click();
        logger.info("Ürünün sepetten silindiği ve sepetin boş olduğu doğrulanır");
        Assert.assertTrue(bp.cartIsEmptyText.isDisplayed());

    }

}
