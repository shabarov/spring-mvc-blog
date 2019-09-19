package ru.shabarov.blog.image;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

@WebServlet(name = "Image Load Servlet", urlPatterns = "/image/*")
//        initParams = {@WebInitParam(name = "basePath", value = "/tmp")})
public class ImageServlet extends HttpServlet {

    private static final int DEFAULT_BUFFER_SIZE = 10240;

    //TODO: @Value doesn't work
//    @Value("${blog.image.basePath}")
    private String basePath = "/tmp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String requestedFile = request.getPathInfo();
        if (requestedFile == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        File file = new File(basePath, URLDecoder.decode(requestedFile, "UTF-8"));

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String contentType = getServletContext().getMimeType(file.getName());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
             BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE)) {

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        }
    }
}
