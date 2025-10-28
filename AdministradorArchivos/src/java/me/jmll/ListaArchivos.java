package me.jmll;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import me.jmll.io.NIO2RecursiveDir;

@WebServlet(name = "ListaArchivos", urlPatterns = {"/List.do"})
public class ListaArchivos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String> errores = new ArrayList<>();
        List<Path> paths = new ArrayList<>();
        Map<String, Long> sizes = new HashMap<>();

        
        String lang = (String) request.getSession().getAttribute("locale");
        if (lang == null || lang.isEmpty()) {
            lang = request.getLocale().getLanguage();
        }

        Locale locale = lang.startsWith("es") ? new Locale("es") : new Locale("en");
        ResourceBundle bundle = ResourceBundle.getBundle("me.jmll.i18n.app", locale);

     
        String dirParam = request.getParameter("path");

        try {
            if (dirParam != null && !dirParam.trim().isEmpty()) {
                Path path = Paths.get(dirParam.trim());

                if (Files.exists(path) && Files.isDirectory(path)) {
                    NIO2RecursiveDir walker = new NIO2RecursiveDir();
                    paths = walker.walkdir(path);

                    for (Path p : paths) {
                        try {
                            sizes.put(p.toString(), Files.size(p));
                        } catch (IOException e) {
                            sizes.put(p.toString(), 0L);
                        }
                    }
                } else {
                    errores.add(bundle.getString("download.notfound"));
                }
            } else {
                errores.add(bundle.getString("download.missing"));
            }
        } catch (Exception e) {
            errores.add("Error: " + e.getMessage());
        }

        request.setAttribute("paths", paths);
        request.setAttribute("sizes", sizes);
        request.setAttribute("errores", errores);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/DirView.jsp");
        rd.forward(request, response);
    }
}
