package UITestsBeymenCom.BasePage;

import APITestsTrello.TrelloAPITest;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import utilities.Driver;

public class BasePage {
    public Logger logger;
    public WebDriver driver;
    @Before
    public void setUp(){
        PropertyConfigurator.configure("log4j.properties");
        logger = Logger.getLogger(TrelloAPITest.class.getName());
        logger.info("Driver Set edilir");
        driver = Driver.getDriver();
    }
    @After
    public void tearDown() {
        logger.info("Driver kapatılır");
        Driver.closeDriver();
    }
}
