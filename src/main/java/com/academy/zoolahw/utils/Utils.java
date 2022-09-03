package com.academy.zoolahw.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Utils {
    public static void pageOutput(HttpServletResponse response, Object output) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("<html><body>");
        writer.println(output);
        writer.println("</body></html>");
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
}
