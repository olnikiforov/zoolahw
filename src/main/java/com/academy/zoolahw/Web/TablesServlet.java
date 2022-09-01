package com.academy.zoolahw.Web;

import com.academy.zoolahw.File.JsonFileWorker;
import com.academy.zoolahw.Utils.Utils;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "tablesServlet", value = "/tables")
public class TablesServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonFileWorker jsonFileWorker = new JsonFileWorker(response);
        String result = jsonFileWorker.getPeople();
        Utils.pageOutput(response, result);
    }
}
