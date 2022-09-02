package com.academy.zoolahw.file;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class UserRepository {
    private static final String DBPATH = "/home/shs/Documents/zoolahw/src/main/resources/db.json";
    private JSONObject people;


    public UserRepository() {
        JSONParser parser = new JSONParser();
        try {
            this.people = (JSONObject) parser.parse(new FileReader(DBPATH));
        } catch (ParseException | IOException e) {
            System.out.println("Cannot parse or read JSON");
        }
    }

    public JSONObject getUser(String pathWithSlash) {
        String path = getPath(pathWithSlash);
        return (JSONObject) people.get(path);
    }

    public String getUsers() {
        ArrayList<String> peopleArray = new ArrayList<>();
        Iterator<String> keys = people.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if (people.get(key) instanceof JSONObject) {
                peopleArray.add(people.get(key).toString());
            }
        }
        return String.join("\n", peopleArray);
    }

    public void createUser(String pathWithSlash, JSONObject jsonBody) throws IOException {
        String path = getPath(pathWithSlash);
        people.put(path, jsonBody);
        updatePeopleFile();
    }

    public void updateUser(String pathWithSlash, JSONObject jsonBody) throws IOException {
        String path = getPath(pathWithSlash);
        people.remove(path);
        people.put(path, jsonBody);
        updatePeopleFile();
    }

    public void deleteUser(String pathWithSlash) throws IOException {
        String path = getPath(pathWithSlash);
        people.remove(path);
        updatePeopleFile();
    }

    private String getPath(String pathWithSlash) {
        int lenPath = pathWithSlash.length();
        return pathWithSlash.substring(1, lenPath);
    }

    public boolean isPathNull(String pathWithSlash) {
        String path = getPath(pathWithSlash);
        return people.get(path) == null;
    }
}
