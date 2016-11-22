package com.caabr.connchecker;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caabr on 2016-11-21.
 */
public class ExcelParser implements FileParser {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final DestinationUtils destinationUtils = new DestinationUtils();

    private final InputStreamSource inputStreamSource;

    private final ArrayList<ConnectionToCheck> connectionToChecks = new ArrayList<>();

    private final Workbook wb;

    private final ArrayList<String> colunNames = new ArrayList<>();

    public ExcelParser(InputStreamSource fileInputStreamSource) throws IOException {
        this.inputStreamSource = fileInputStreamSource;
        Workbook wb;
        try {
            wb = new XSSFWorkbook(this.inputStreamSource.getInputStream());
        } catch (OLE2NotOfficeXmlFileException exception) {
            // try old format
            wb = new HSSFWorkbook(this.inputStreamSource.getInputStream());
        }
        this.wb = wb;

        parseFile();
    }

    @Override
    public ArrayList<ConnectionToCheck> getConnectionToChecks() {
        return connectionToChecks;
    }

    private void parseFile() throws IOException {
        boolean firstRow = true;
        for (Row row : wb.getSheetAt(0)) {
            if (firstRow) {
                parseHeaderRow(row);
                firstRow = false;
            } else {
                connectionToChecks.add(parseRow(row));
            }
        }
    }

    private void parseHeaderRow(Row row) {
        for (Cell cell : row) {
            colunNames.add(cell.getStringCellValue());
        }
    }


    private ConnectionToCheck parseRow(Row row) {
        try {
            final HostPort hostPort;
            if (recordHasValue(row, "URI")) {
                try {
                    hostPort = destinationUtils.extractHostPortFromUri(getRecordValue(row, "URI"));
                } catch (MalformedURLException e) {
                    return new ConnectionToCheck(row.getRowNum(), extractInfoFields(row), "Row has no valid URI (Check with ICC?)");
                }
            } else if (recordHasValue(row, "Host") && recordHasValue(row, "Port")) {
                hostPort = new HostPort(getRecordValue(row, "Host"), Integer.parseInt(getRecordValue(row, "Port")));
            } else if (recordHasValue(row, "Destination (External partner) address") && recordHasValue(row, "Port")) {
                hostPort = new HostPort(getRecordValue(row, "Destination (External partner) address"), Integer.parseInt(getRecordValue(row, "Port")));
            } else {
                return new ConnectionToCheck(row.getRowNum(), extractInfoFields(row), "No valid Host and/or Port value or no URI column");
            }

            return new ConnectionToCheck(row.getRowNum(), extractInfoFields(row), hostPort);
        } catch (RuntimeException ex) {
            log.error("Error while parsing row " + row.getRowNum(), ex);
            throw ex;
        }
    }

    private String getRecordValue(Row row, String columnName) {
        if (!isMapped(row, columnName))
            throw new RuntimeException("Row " + row.getRowNum() + " doesn't contain the column " + columnName);
        Cell cell = row.getCell(colunNames.indexOf(columnName));
        if (columnName.equals("Port")) // Hack!
            return Integer.toString(((int) cell.getNumericCellValue()));
        else
            return cell.toString();
    }

    private boolean isMapped(Row row, String columnName) {
        return colunNames.contains(columnName);
    }

    private boolean recordHasValue(Row row, String columnName) {
        return isMapped(row, columnName) && getRecordValue(row, columnName) != null && !getRecordValue(row, columnName).equals("");
    }

    private List<String> extractInfoFields(Row row) {
        ArrayList<String> fields = new ArrayList<>();
        addIfFieldNotEmpty(row, "IntegrationObject", fields);
        addIfFieldNotEmpty(row, "External Partner name", fields);
        addIfFieldNotEmpty(row, "Application", fields);
        addIfFieldNotEmpty(row, "Send Port Name", fields);
        addIfFieldNotEmpty(row, "URI", fields);

        return fields;
    }

    private void addIfFieldNotEmpty(Row row, String columnName, List<String> list) {
        if (isMapped(row, columnName) && getRecordValue(row, columnName) != null && !getRecordValue(row, columnName).equals("")) {
            list.add(getRecordValue(row, columnName));
        }
    }


}
