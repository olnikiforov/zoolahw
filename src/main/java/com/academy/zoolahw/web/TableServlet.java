package com.academy.zoolahw.web;

import com.academy.zoolahw.file.UserRepository;
import com.academy.zoolahw.utils.Utils;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;


import java.io.*;

@WebServlet(name = "tableServlet", value = "/table/*")
public class TableServlet extends HttpServlet {
    public UserRepository userRepository;

    @Override
    public void init() {
        this.userRepository = new UserRepository();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject table = userRepository.getUser(request.getPathInfo());
        if (table != null) {
            Utils.pageOutput(response, table);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!userRepository.isUserExist(request.getPathInfo())) {
            try {
                userRepository.createUser(request.getPathInfo(), Utils.getBody(request, response));
            } catch (IOException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendError(HttpServletResponse.SC_CONFLICT);
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (userRepository.isUserExist(request.getPathInfo())) {
            try {
                userRepository.updateUser(request.getPathInfo(), Utils.getBody(request, response));
            } catch (IOException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (userRepository.isUserExist(request.getPathInfo())) {
            try {
                userRepository.deleteUser(request.getPathInfo());
            } catch (IOException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
