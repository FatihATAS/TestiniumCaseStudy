package APITestsTrello.BaseURLs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;
import java.util.ArrayList;
import java.util.List;

public class TrelloAPIBaseUrl {
    public RequestSpecification specification;
    private static String boardID;
    private static String listID;
    private static List<String> cardIDList = new ArrayList<>();


    public TrelloAPIBaseUrl(){
        specification = new RequestSpecBuilder().setBaseUri(ConfigReader.getProperties("baseUrlTrello")).build();
    }

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        TrelloAPIBaseUrl.boardID = boardID;
    }

    public String getListID() {
        return listID;
    }

    public void setListID(String listID) {
        TrelloAPIBaseUrl.listID = listID;
    }

    public void addCartIDToList(String cardID){
        cardIDList.add(cardID);
    }
    public String selectCartFromList(int index){
        return cardIDList.get(index);
    }
    public int getCardCount(){
        return cardIDList.size();
    }

    public List<String> getCardIDList() {
        return cardIDList;
    }
}
