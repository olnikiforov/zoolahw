package com.academy.zoolahw.File;

import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class JsonFileWorker {
    private static final String DBPATH = "/home/shs/Documents/zoolahw/src/main/resources/db.json";
    private JSONObject people;

    private final HttpServletResponse response;
    public JsonFileWorker(HttpServletResponse response) throws IOException {
        this.response = response;
        JSONParser parser = new JSONParser();
        try {
            this.people = (JSONObject) parser.parse(new FileReader(DBPATH));
        }
        catch (ParseException | IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public JSONObject getPerson(String path){
        return (JSONObject) people.get(path);
    }

    public String getPeople(){
        StringBuilder stringBuilder = new StringBuilder();
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

    public void createPerson(String path, JSONObject jsonBody) throws IOException {
        people.put(path, jsonBody);
        try (FileWriter fileWriter = new FileWriter(DBPATH)) {
            fileWriter.write(people.toJSONString());
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void updatePerson(String path, JSONObject jsonBody) throws IOException {
        people.remove(path);
        people.put(path, jsonBody);
        try (FileWriter fileWriter = new FileWriter(DBPATH)) {
            fileWriter.write(people.toJSONString());
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void deletePerson(String path) throws IOException {
        people.remove(path);
        try (FileWriter fileWriter = new FileWriter(DBPATH)) {
            fileWriter.write(people.toJSONString());
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public boolean isPathNull(String path){
        return people.get(path) == null;
    }
}
