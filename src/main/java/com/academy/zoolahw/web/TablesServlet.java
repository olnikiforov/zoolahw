package com.academy.zoolahw.web;

import com.academy.zoolahw.file.UserRepository;
import com.academy.zoolahw.utils.Utils;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "tablesServlet", value = "/tables")
public class TablesServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserRepository userRepository = new UserRepository();
        String result = userRepository.getUsers();
        Utils.pageOutput(response, result);
    }
}
