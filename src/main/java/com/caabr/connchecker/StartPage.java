package com.caabr.connchecker;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caabr on 2016-11-12.
 */
@Controller
public class StartPage {

    private final DestinationUtils destinationUtils = new DestinationUtils();

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        return "startpage";
    }

    @PostMapping("/docheck")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {


        model.addAttribute("message", "Processing file " + file.getOriginalFilename() + " please wait...");

        ArrayList<ConnectionToCheck> connectionToChecks = new ArrayList<ConnectionToCheck>();
        try {
            final Reader reader = new InputStreamReader(new BOMInputStream(file.getInputStream()), "UTF-8");
            final CSVParser parser = new CSVParser(reader, CSVFormat.newFormat(';').withHeader());
            try {
                for (final CSVRecord record : parser) {
                    connectionToChecks.add(parseRecord(connectionToChecks, record));
                }
            } finally {
                parser.close();
                reader.close();
            }
        } catch (Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
            e.printStackTrace();
        }
        model.addAttribute("connectionToChecks", connectionToChecks);

        // Get public IP of this server
        String publicIP;
        try {
            model.addAttribute(DestinationUtils.getPublicIp(false));
        } catch (IOException e) {
            model.addAttribute("Unable to get public IP of this server.");
            e.printStackTrace();
        }

        return "docheck2";
    }

    private ConnectionToCheck parseRecord(ArrayList<ConnectionToCheck> connectionToChecks, CSVRecord record) {
        ConnectionToCheck connectionToCheck;

        HostPort hostPort;
        if (recordHasValue(record, "URI")) {
            try {
                hostPort = destinationUtils.extractHostPortFromUri(record.get("URI"));
            } catch (MalformedURLException e) {
                return new ConnectionToCheck(extractInfoFields(record), new HostPort(record.get("URI"), 0), "Row contains " + record.getRecordNumber() + " no URI column", false);
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
        ArrayList<String> fields = new ArrayList<String>();

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

}
