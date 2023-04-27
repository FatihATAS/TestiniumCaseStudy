package utilities;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    private Workbook workbook;
    private Sheet sheet;
    private String path;

    public ExcelUtils(String path, int sheetIndex){
        this.path=path;
        try {
            FileInputStream fis=new FileInputStream(path);
            workbook= WorkbookFactory.create(fis);
            sheet=workbook.getSheetAt(sheetIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Istedigimiz hucredeki veriyi almamizi saglar
    public String getCellData(int rowNum, int colNum) {
        Cell cell;
        try {
            cell = sheet.getRow(rowNum).getCell(colNum);
            String cellData = cell.toString();
            return cellData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
