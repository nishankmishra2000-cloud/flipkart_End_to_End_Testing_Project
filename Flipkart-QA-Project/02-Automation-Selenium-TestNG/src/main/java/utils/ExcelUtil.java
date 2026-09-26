package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Reads rows from an .xlsx file into a simple List<String[]> for TestNG @DataProvider use. */
public class ExcelUtil {

    public static List<String[]> readSheet(String filePath, String sheetName) {
        List<String[]> rows = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            DataFormatter formatter = new DataFormatter();
            boolean firstRow = true;

            for (Row row : sheet) {
                if (firstRow) { // skip header
                    firstRow = false;
                    continue;
                }
                int lastCol = row.getLastCellNum();
                String[] values = new String[lastCol];
                for (int c = 0; c < lastCol; c++) {
                    Cell cell = row.getCell(c);
                    values[c] = cell == null ? "" : formatter.formatCellValue(cell);
                }
                rows.add(values);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data from " + filePath, e);
        }
        return rows;
    }
}
