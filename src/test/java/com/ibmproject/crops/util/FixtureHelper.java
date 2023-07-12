package com.ibmproject.crops.util;

import com.google.common.io.Resources;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FixtureHelper {
    private final static String FIXTURES = "fixtures/";

    public static String getFileAsString(String fileName) throws IOException {
        return Resources.toString(Resources.getResource(FIXTURES + fileName), Charset.defaultCharset());
    }
}
