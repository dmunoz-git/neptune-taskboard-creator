package com.neptune.boards.tests.e2e.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtils {

    public static String readJsonFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
