package org.bytecub.WedahamineBackend.utils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Utils {

    // List of allowed image MIME types
    public static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/jpg"
    );

    public static String determineImageContentType(String filename) {
        String contentType;
        if (filename.endsWith(".jpeg")) {
            contentType = "image/jpeg";
        } else if (filename.endsWith(".png")) {
            contentType = "image/png";
        } else if (filename.endsWith(".jpg")) {
            contentType = "image/jpg";
        } else {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    public static String uniqKeyGenerator() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

}
