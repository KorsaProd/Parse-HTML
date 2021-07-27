

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static final String folderPath = "images";

    public static void main(String[] args) {
        String htmlFile = parseFile("data/code.html");
        Document doc = Jsoup.parse(htmlFile);
        Elements elements = doc.select("img.g-picture");
        try {
            for (Element el : elements) {
                String src = el.absUrl("src");
                getImages(src);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void getImages(String src) throws IOException {
        int indexname = src.lastIndexOf("/");
        if (indexname == src.length()) {
            src = src.substring(1, indexname);
        }
        indexname = src.lastIndexOf("/");
        String name = src.substring(indexname);
        System.out.println(name);
        URL url = new URL(src);
        InputStream in = url.openStream();
        OutputStream out = new BufferedOutputStream(new FileOutputStream(folderPath + name));
        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }
        out.close();
        in.close();
    }

    public static String parseFile(String path){
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(line -> builder.append(line + "\n") );
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
}
