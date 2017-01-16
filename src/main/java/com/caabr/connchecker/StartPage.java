package com.caabr.connchecker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class StartPage {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final DestinationUtils destinationUtils = new DestinationUtils();

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        return "startpage2";
    }

    @PostMapping("/docheck")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam(defaultValue = "") String ignoreLocal, Model model) {


        model.addAttribute("message", "Processing file " + file.getOriginalFilename() + " please wait...");

        // Parse Excel file
        try {
            model.addAttribute("connectionToChecks", new ExcelParser(file).getConnectionToChecks());
        } catch (IOException e) {
            log.error("Error while reading file", e);
            model.addAttribute("message", "Error while processing file: " + e.getMessage());
        }

        // Get public IP of this server
        try {
            model.addAttribute("localIp", destinationUtils.getPublicIp(false));
        } catch (IOException e) {
            model.addAttribute("localIp", "Unable to get public IP of this server.");
            log.error("Unable to get public IP of this server.", e);
        }

        model.addAttribute("ignoreLocal", "ignoreLocal".equals(ignoreLocal));

        return "docheck2";
    }


}
