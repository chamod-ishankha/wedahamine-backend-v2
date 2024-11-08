package org.bytecub.WedahamineBackend.rest.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping
    public ResponseEntity<String> test() {
        log.info("Inside test controller");
        return new ResponseEntity<>("Test OK!", HttpStatus.OK);
    }

    String lightStatus = "OFF";

    @GetMapping("/light/on")
    public ResponseEntity<String> lightOn() {
        log.info("Inside lightOn controller");
        lightStatus = "ON";
        return new ResponseEntity<>("Light is ON", HttpStatus.OK);
    }

    @GetMapping("/light/off")
    public ResponseEntity<String> lightOff() {
        log.info("Inside lightOff controller");
        lightStatus = "OFF";
        return new ResponseEntity<>("Light is OFF", HttpStatus.OK);
    }

    @GetMapping("/light/status")
    public ResponseEntity<String> lightStatus() {
        log.info("Inside lightStatus controller");
        return new ResponseEntity<>(lightStatus, HttpStatus.OK);
    }

    @GetMapping("/music/play")
    public ResponseEntity<Resource> getMusicFile() {
        log.info("Inside getMusicFile controller");

        try {
            // Load the music.wav file from the resources folder
            ClassPathResource resource = new ClassPathResource("music.wav");

            // Check if the resource exists
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Set the headers to specify the content type
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "audio/wav");

            // Return the file as the response
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error serving music file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
