package com.caabr.connchecker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by caabr on 2016-11-21.
 */
public class CsvParser implements FileParser {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final DestinationUtils destinationUtils = new DestinationUtils();


    private final ArrayList<ConnectionToCheck> connectionToChecks = new ArrayList<>();

    /*
    public CsvParser(InputStreamSource inputStreamSource) throws IOException {



            final Reader reader = new InputStreamReader(new BOMInputStream(inputStreamSource.getInputStream()), "UTF-8");
            final CSVParser parser = new CSVParser(reader, CSVFormat.newFormat(';').withHeader());
            try {
                for (final CSVRecord record : parser) {
                    connectionToChecks.add(parseRecord(record));
                }
            } finally {
                parser.close();
                reader.close();
            }

    }

    private ConnectionToCheck parseRecord(CSVRecord record) {

        HostPort hostPort;
        if (recordHasValue(record, "URI")) {
            try {
                hostPort = destinationUtils.extractHostPortFromUri(record.get("URI"));
            } catch (MalformedURLException e) {
                return new ConnectionToCheck(record.getRecordNumber(), extractInfoFields(record), new HostPort(record.get("URI"), 0), "Row contains " + record.getRecordNumber() + " no URI column", false);
            }
        } else if (recordHasValue(record, "Host") && recordHasValue(record, "Port")) {
            hostPort = new HostPort(record.get("Host"), Integer.parseInt(record.get("Port")));
        } else if (recordHasValue(record, "Destination (External partner) address") && recordHasValue(record, "Port")) {
            hostPort = new HostPort(record.get("Destination (External partner) address"), Integer.parseInt(record.get("Port")));
        } else {
            return new ConnectionToCheck(extractInfoFields(record), new HostPort("Unknown", 0), "Row " + record.getRecordNumber() + " has no valid Host and/or Port value or no URI column", false);
        }

        return new ConnectionToCheck(extractInfoFields(record), hostPort);
    }

    private boolean recordHasValue(CSVRecord record, String columnName) {
        return record.isMapped(columnName) && record.get(columnName) != null && !record.get(columnName).equals("");
    }

    private List<String> extractInfoFields(CSVRecord record) {
        ArrayList<String> fields = new ArrayList<>();

        addIfFieldNotEmpty(record, "IntegrationObject", fields);
        addIfFieldNotEmpty(record, "External Partner name", fields);
        addIfFieldNotEmpty(record, "Application", fields);
        addIfFieldNotEmpty(record, "Send Port Name", fields);
        addIfFieldNotEmpty(record, "URI", fields);

        return fields;
    }

    private void addIfFieldNotEmpty(CSVRecord record, String fieldName, List<String> list) {
        if (record.isMapped(fieldName) && record.get(fieldName) != null && !record.get(fieldName).equals("")) {
            list.add(record.get(fieldName));
        }
    }
    */

    @Override
    public ArrayList<ConnectionToCheck> getConnectionToChecks() {
        return this.connectionToChecks;
    }
}
