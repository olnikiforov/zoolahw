package com.academy.zoolahw.Web;

import com.academy.zoolahw.File.JsonFileWorker;
import com.academy.zoolahw.Utils.Utils;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

@WebServlet(name = "tableServlet", value = "/table/*")
public class TableServlet extends HttpServlet {
    public JsonFileWorker jsonFileWorker;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = getPath(request);
        jsonFileWorker = new JsonFileWorker(response);
        JSONObject table = jsonFileWorker.getPerson(path);

        if (table != null) {
            Utils.pageOutput(response, table);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = getPath(request);
        jsonFileWorker = new JsonFileWorker(response);
        if (jsonFileWorker.isPathNull(path)) {
            jsonFileWorker.createPerson(path, getBody(request, response));
        } else {
            response.sendError(HttpServletResponse.SC_CONFLICT);
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = getPath(request);
        jsonFileWorker = new JsonFileWorker(response);
        if (!jsonFileWorker.isPathNull(path)) {
            jsonFileWorker.updatePerson(path, getBody(request, response));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = getPath(request);
        jsonFileWorker = new JsonFileWorker(response);
        if (!jsonFileWorker.isPathNull(path)) {
            jsonFileWorker.deletePerson(path);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static JSONObject getBody(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        try (InputStream inputStream = request.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                bodyBuilder.append(charBuffer, 0, bytesRead);
            }
        } catch (IOException ex) {
            System.err.println("Cannot read body");
        }
        String body = bodyBuilder.toString();
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    public static String getPath(HttpServletRequest request) {
        String pathWithSlash = request.getPathInfo();
        int lenPath = pathWithSlash.length();
        return pathWithSlash.substring(1, lenPath);
    }
}
