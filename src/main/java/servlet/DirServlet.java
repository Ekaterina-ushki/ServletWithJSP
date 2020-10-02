package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/")
public class DirServlet extends HttpServlet {

    private String defaultFolder = "C:";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getParameter("path");
        path = path == null ? defaultFolder : path;

        Path pathToFile = Paths.get(path);
        File file = pathToFile.toFile();

        if (file.isFile()){
            resp.setHeader("Content-Type", "application/octet-stream");
            resp.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

            FileInputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = resp.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,bytesRead);
            }

            inputStream.close();
            outputStream.close();
        }else{
            printDirectory(req, path == null ? defaultFolder : path);

            req.setAttribute("now", new Date());
            req.setAttribute("name", path == null ? defaultFolder : path);
            req.getRequestDispatcher("explorer.jsp").forward(req, resp);
        }
    }

    private void printDirectory(HttpServletRequest req, String path) {

        StringBuilder attrButton = new StringBuilder();
        StringBuilder attrFiles = new StringBuilder();
        StringBuilder attrFolders = new StringBuilder();

        if (path.contains("/"))
            addBackButton(attrButton, path.substring(0, path.lastIndexOf('/')), "back");

        File[] files = new File(path).listFiles();

        if(files == null || files.length == 0)
            return;

        for (File file : files) {
            if (file.isDirectory())
                addDirectory(attrFolders, path + "/" + file.getName(), file.getName(), file.lastModified(), file.length());

            else
                addFile(attrFiles, file.getName(), file.lastModified(), file.length(), path);
        }

        req.setAttribute("button", attrButton);
        req.setAttribute("folders", attrFolders);
        req.setAttribute("files", attrFiles);
    }

    private void addBackButton(StringBuilder attrButton, String path, String text){
        attrButton.append("<tr><td><img src=\"http://s1.iconbird.com/ico/0612/MustHave/w24h241339196030StockIndexUp24x24.png\"><a href=\"?path=")
                .append(path)
                .append("\">")
                .append(text)
                .append("</a></td>");
    }

    private void addDirectory(StringBuilder attrFiles, String path, String text, long date, long length) {
        attrFiles.append("<tr><td><img src=\"http://s1.iconbird.com/ico/0612/Aurora/w16h161339789902icontextoaurorafoldersopen5.png\"><a href=\"?path=")
            .append(path)
            .append("\">")
            .append(text)
            .append("</a></td><td>")
            .append(length + " B")
            .append("</td><td>")
            .append(new SimpleDateFormat("MM.dd.yyyy HH:mm:ss").format(new Date(date)))
            .append("</td>");
    }

    private void addFile(StringBuilder attrFiles, String text, long date, long length, String path) {
        attrFiles.append("<tr><td><img src=\"http://s1.iconbird.com/ico/0912/fugue/w16h161349012974scripttext.png\"><a href=\"?path=" + path +"/" + text + "\">")
            .append(text)
            .append("</a></td><td>")
            .append(length + " B")
            .append("</td><td>")
            .append(new SimpleDateFormat("MM.dd.yyyy HH:mm:ss").format(new Date(date)))
            .append("</td>");
    }
}