package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Categorie {

    public static void main(String[] args) throws InterruptedException, IOException {
        String url = "https://www.mytek.tn"; // Replace with the URL of the e-commerce site


        System.out.println("1");

        System.out.println("2");
        Document doc = Jsoup.connect(url).get();
        Elements products = doc.select(".vertical-list.clearfix").select(".vertical-list.clearfix li.rootverticalnav"); // Replace ".product" with the CSS selector for product elements
        String csvFilePath = "parentCat.csv";
        try (FileWriter writer = new FileWriter(csvFilePath)) {
        writer.append("Active (0/1),Name *,Parent category,Root category (0/1)\n");
        for (Element product : products) {
            String name = product.getElementsByTag("a").get(0).text();
            // Replace ".name" with the CSS selector for the product name
            writer.append("1,");
            writer.append(name+",");
            writer.append("Home,");
            writer.append("0").append("\n");
        }
        // Writing to Excel file





            // Write headers




            System.out.println("CSV file updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

