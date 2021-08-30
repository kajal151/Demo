package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Hello world!
 */
public class App {

    public static void scrapeLink() {

        try {
            //
            Document doc = Jsoup.connect("https://mobilefirstplatform.ibmcloud.com/tutorials/en/foundation/8.0/installation-configuration").get();
            Elements links = doc.select("a[href]");    // filter all the links having tag a of href type
            List<String> list = new ArrayList<String>();   // created a list to store all http links
            for (Element link : links) {
                boolean stringCheck = link.attr("href").contains("http");     //if the link contains http then only save in list
                if (stringCheck) {
                    list.add(link.attr("href"));       // save only http links to list
                }
                System.out.println(link.attr("href"));    // to print all the links
            }

            for (int i = 0; i < list.size(); i++) {
                saveData(list.get(i));                       // SaveData method used to get response and save into file
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void saveData(String str) {

        File file = new File("C:\\Test\\Urls\\" + "Folder" + ".txt");  // created new file
        int count = 1;

        while (file.exists()) {
            count++;
            file = new File("C:\\Test\\Urls\\" + "Folder" + count + ".txt");   // value of count increases for every link and create a unique file name
        }
        if (!file.exists()) {
            try {
                boolean fileCreated = file.createNewFile();
                // Validate that file actually got created
                if (!fileCreated) {
                    throw new IOException("Unable to create file at specified path. It already exists");
                }


                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);           // use this to write in txt file
                

                URL obj = new URL(str);
                HttpURLConnection hr = (HttpURLConnection) obj.openConnection();
                //  int responseCode= connection.getResponseCode();

                InputStream im = hr.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(im));    // Use this to read response
                String line = br.readLine();
                while (line != null) {
                    bw.write(line);
                    line = br.readLine();
                }


                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public static void main(String[] args) throws Exception {
        scrapeLink();

    }
}
