package APITestsTrello;

import APITestsTrello.BaseURLs.TrelloAPIBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import utilities.ConfigReader;

import java.util.Random;

import static io.restassured.RestAssured.given;
/*
Test Methodlarına öncelik tanımlamak için @FixMethodOrder annotations ile method isimleri sıralandı.
Bundan dolayı method isimlerinin başlarına harfler verilmiştir.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrelloAPITest{
    Logger logger;
    @Before
    public void setUp(){
        PropertyConfigurator.configure("log4j.properties");
        logger = Logger.getLogger(TrelloAPITest.class.getName());
    }

    TrelloAPIBaseUrl trello = new TrelloAPIBaseUrl();
    String token = ConfigReader.getProperties("token");
    String apiKey = ConfigReader.getProperties("apiKey");


    @Test
    public void aGetToken() {
        logger.info("'Token Bilgilerini Almak' için URL'i set eder ve Endpoint için path ve query parametrelerini girilir");
        trello.specification.pathParams("pp1","tokens","pp2",token).queryParams("key",apiKey,"token",token);
        logger.info("token bilgilerini almak için Request gönderilir");
        Response response = given().when().spec(trello.specification).get("/{pp1}/{pp2}");
        logger.info("Response'un status kodun 200 olduğunu ve id'nin null olmadığını doğrulanır");
        response.then().assertThat().statusCode(200).body("id" , Matchers.notNullValue());
    }

    @Test
    public void bCreateBoard() {
        logger.info("'Board Create' etmek için URL'i set eder ve Endpoint için path ve query parametrelerini girilir");
        trello.specification.pathParam("pp1","boards").queryParams("name","TestiniumBoard","key",apiKey,"token",token);
        logger.info("'Board Create' etmek için Request gönderilir");
        Response response = given().when().spec(trello.specification).contentType(ContentType.JSON).post("/{pp1}");
        logger.info("Response'un status kodun 200 olduğunu ve id'nin null olmadığını doğrulanır");
        response.then().assertThat().statusCode(200).body("id",Matchers.notNullValue());
        logger.info("Response'dan gelen 'BoardID' değerini bir değişkene tanımlanır");
        trello.setBoardID(response.jsonPath().getString("id"));
    }
    @Test
    public void cCreateIDList() {
        logger.info("'ListID Create' etmek için URL'i set eder ve Endpoint için path ve query parametrelerini girilir");
        trello.specification.pathParam("pp1","lists").
                queryParams("name","TestiniumIDList","idBoard",trello.getBoardID(),"key",apiKey,"token",token);
        logger.info("'ListID Create' etmek için Request gönderilir");
        Response response = given().when().spec(trello.specification).contentType(ContentType.JSON).post("/{pp1}");
        logger.info("Response'un status kodun 200 olduğunu ve id'nin null olmadığını doğrulanır");
        response.then().assertThat().statusCode(200).body("id",Matchers.notNullValue());
        logger.info("Response'dan gelen 'ListID' değerini bir değişkene tanımlanır");
        trello.setListID(response.jsonPath().getString("id"));
    }

    @Test
    public void dCreateCard() {
        for (int i = 1; i <=2 ; i++) {
            /*
            ÖNEMLİ NOT
            Bu testte birden fazla specification create etmemek için Request'i String URL olarak set edilip
            path ve query parametreleri girildi.
            Bunun nedeni; aynı specification ile birden fazla request gönderememizdir.
             */
            logger.info(i +".Card Create' etmek için URL'i set eder ve Endpoint için path ve query parametrelerini girilir");
            String URL = "https://api.trello.com/1/cards?idList="+trello.getListID()+"&key="+apiKey+"&token="+token;
            logger.info(i+"'.Card Create' etmek için Request gönderilir");
            Response response = given().when().contentType(ContentType.JSON).post(URL);
            logger.info("Response'un status kodun 200 olduğunu ve id'nin null olmadığını doğrulanır");
            response.then().assertThat().statusCode(200).body("id",Matchers.notNullValue());
            logger.info("Response'dan gelen "+i+"'.CardID' değerini listeye tanımlanır");
            trello.addCartIDToList(response.jsonPath().getString("id"));
        }
    }

    @Test
    public void eUpdateRandomCard() {
        logger.info("Rastgele Card seçmek için rastgele sayı üretilir(Min: 0 , Max:CardList'in içerisindeki Card Sayisi)");
        int randomCardIndex = new Random().nextInt(trello.getCardCount());
        logger.info("'Card Update' işlemi için URL'i set eder ve Endpoint için path ve query parametrelerini girilir");
        trello.specification.pathParams("pp1","cards","pp2",trello.selectCartFromList(randomCardIndex))
                .queryParams("key",apiKey,"token",token,"name","updatedCardName");
        logger.info("'Card Update' etmek için Request gönderilir");
        Response response = given().when().spec(trello.specification).contentType(ContentType.JSON).put("/{pp1}/{pp2}");
        logger.info("Response'un status kodun 200 olduğunu ve CardName'in değiştiği doğrulanır");
        response.then().assertThat().statusCode(200).body("name",Matchers.equalTo("updatedCardName"));
    }

    @Test
    public void fDeleteAllCards() {
        for (String cardId : trello.getCardIDList()){
            logger.info("'Card Delete' işlemi için URL'i set eder ve Endpoint için path ve query parametrelerini girilir");
            trello.specification.pathParams("pp1","cards","pp2",cardId).queryParams("key",apiKey,"token",token);
            logger.info("'Card Delete' etmek için Request gönderilir");
            Response response = given().when().spec(trello.specification).delete("/{pp1}/{pp2}");
            logger.info("Response'un status kodun 200 olduğu doğrulanır");
            response.then().assertThat().statusCode(200);
        }
    }

    @Test
    public void gDeleteBoard() {
        logger.info("'Board Delete' işlemi için URL'i set eder ve Endpoint için path ve query parametrelerini girilir");
        trello.specification.pathParams("pp1","boards","pp2",trello.getBoardID()).queryParams("key",apiKey,"token",token);
        logger.info("'Board Delete' etmek için Request gönderilir");
        Response response = given().when().spec(trello.specification).contentType(ContentType.JSON).delete("/{pp1}/{pp2}");
        logger.info("Response'un status kodun 200 olduğu doğrulanır");
        response.then().assertThat().statusCode(200);
    }
}
