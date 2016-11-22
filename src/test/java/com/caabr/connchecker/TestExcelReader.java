package com.caabr.connchecker;


import org.springframework.core.io.FileSystemResource;


/**
 * Created by caabr on 2016-11-21.
 */
public class TestExcelReader {

    public static void main(String[] args) throws Exception {
        /*Workbook wb = new XSSFWorkbook(new FileInputStream(args[0]));
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            System.out.println(wb.getSheetName(i));
            for (Row row : sheet) {
                System.out.println("rownum: " + row.getRowNum());
                for (Cell cell : row) {
                    System.out.println(cell.toString());
                }
            }
        }
        */

        FileSystemResource fileSystemResource = new FileSystemResource("/Users/caabr/Documents/20161019_Swix_ProxyList_ST.XLSX");

        ExcelParser excelParser = new ExcelParser(fileSystemResource);
        for (ConnectionToCheck connectionToCheck : excelParser.getConnectionToChecks()) {
            System.out.println(connectionToCheck);
        }
    }
}
