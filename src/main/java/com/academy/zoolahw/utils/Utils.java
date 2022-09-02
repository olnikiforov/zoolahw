package com.academy.zoolahw.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class Utils {
    public static void pageOutput(HttpServletResponse response, Object output) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("<html><body>");
        writer.println(output);
        writer.println("</body></html>");
    }
}
