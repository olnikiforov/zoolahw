package com.academy.zoolahw.Web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        session.setAttribute("userName", request.getParameter("userName"));
        session.setAttribute("password", request.getParameter("password"));

        System.out.println("Login");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Success</h1>");
        out.println("</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String authHeader = request.getHeaders("authorization").nextElement();
        String base64UsernamePassword = authHeader.split(" ")[1];

        byte[] decoded = Base64.getDecoder().decode(base64UsernamePassword);
        String decodedStr = new String(decoded, StandardCharsets.UTF_8);
        String[] credentials = decodedStr.split(":");

        String userName = credentials[0];
        String password = credentials[1];

        session.setAttribute("userName", userName);
        session.setAttribute("password", password);
    }
}