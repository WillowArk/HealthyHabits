package com.example.backend.users;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
    public static String readFileAsString(String fileName) throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public static void main(String[] args) throws Exception {
        String jsonString = readFileAsString("data/a.json");
        System.out.println(jsonString);
    }
}

