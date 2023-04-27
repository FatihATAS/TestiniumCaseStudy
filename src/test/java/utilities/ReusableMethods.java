package utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.time.Duration;

public class ReusableMethods {
    public static void waitElementIsVisible(WebElement webElement){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver() , Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }
    public static void waitElementVisibleAndClick(WebElement webElement){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver() , Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(webElement)).click();
    }
    public static void writeDataToTxtFile(String filePath , String data){
        File file = new File(filePath);
        try{
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
