package me.jmll;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

@WebServlet(name = "DescargaArchivo", urlPatterns = {"/Download.do"})
public class DescargaArchivo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Object localeAttr = request.getSession().getAttribute("locale");
        Locale locale;

        if (localeAttr instanceof Locale) {
            locale = (Locale) localeAttr;
        } else if (localeAttr instanceof String) {
            String lang = ((String) localeAttr).toLowerCase(Locale.ROOT);
            if (lang.contains("es")) locale = new Locale("es");
            else locale = new Locale("en");
        } else {
            String lang = request.getLocale().getLanguage();
            locale = lang.contains("es") ? new Locale("es") : new Locale("en");
        }

        ResourceBundle bundle = ResourceBundle.getBundle("me.jmll.i18n.app", locale);

        String filePath = request.getParameter("file");
        if (filePath == null || filePath.isEmpty()) {
            mostrarError(request, response, bundle.getString("download.missing"));
            return;
        }

        Path file = Paths.get(filePath);
        if (!Files.exists(file)) {
            mostrarError(request, response, bundle.getString("download.notfound"));
            return;
        }

        response.setContentType(Files.probeContentType(file));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");

        try (InputStream in = Files.newInputStream(file);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    private void mostrarError(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        errores.add(mensaje);
        request.setAttribute("errores", errores);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/DirForm.jsp");
        rd.forward(request, response);
    }
}
