package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategorieSub {


    public static List<String> getFirstColumn(String csvFile) {
        List<String> firstColumn = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            int k =0;
            while ((line = br.readLine()) != null) {
                // Splitting the line by comma and getting the first element

                String[] data ;
                if(k>0) {
                    data = line.split(",");
                    if (data.length > 0) {
                        firstColumn.add(data[1]);
                    }
                }
                k++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return firstColumn;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        String url = "https://www.mytek.tn"; // Replace with the URL of the e-commerce site


        System.out.println("1");

        System.out.println("2");
        Document doc = Jsoup.connect(url).get();
        Elements products = doc.select(".vertical-list.clearfix").select(".vertical-list.clearfix li.rootverticalnav"); // Replace ".product" with the CSS selector for product elements
   //     Elements products = doc.select(".vertical-list.clearfix").select(".vertical-list.clearfix li.rootverticalnav"); // Replace ".product" with the CSS selector for product elements

        String parentCat = "parentCat.csv";
        String catSub = "parentSub.csv";
        List<String>catString =getFirstColumn(parentCat);
        try (FileWriter writer = new FileWriter(catSub)) {
            writer.append("Active (0/1),Name *,Parent category,URL Simplifier,Root category (0/1)\n");
int indexCat=0;
                for (Element categorie : products) {
                    Elements subCats = categorie.select(".vertical_fullwidthmenu.varticalmenu_main.fullwidth.clearfix").select(".title_normal");

                    for (Element subcat : subCats) {
                        // Replace ".name" with the CSS selector for the product name
//                        Random random = new Random();
//                        random.nextInt(100000);
                       // writer.append(random.nextInt(100000)+",");
                        writer.append("1,");
                        writer.append(subcat.text() + ",");
                        writer.append(catString.get(indexCat) + ",");
                        writer.append(subcat.text().substring(subcat.text().length()-2) + ",");
                        writer.append("0").append("\n");
                    }
                    indexCat++;
                }
            System.out.println("CSV file updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

