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
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 */


public class App {

    public static String number(String str) {
        Pattern pattern = Pattern.compile("[0-9,]+");
        Matcher matcher = pattern.matcher(str);

        // StringBuilder to store the extracted numerical part
        StringBuilder numericalPart = new StringBuilder();

        // Iterate through the matches and append to numericalPart
        while (matcher.find()) {
            numericalPart.append(matcher.group());
        }

        // Output the numerical part
        //  System.out.println("Numerical part: " + numericalPart.toString());
        return numericalPart.toString();
    }

    public static String ref(String str1, String str2) {
        StringBuilder result = new StringBuilder();
        int minLength = Math.min(str1.length(), str2.length());

        for (int i = 0; i < minLength; i += 5) {
            // Copy 5 characters from str1
            if (i + 5 <= str1.length()) {
                result.append(str1, i, i + 5);
            }
            // Copy 5 characters from str2
            if (i + 5 <= str2.length()) {
                result.append(str2, i, i + 5);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        String url = "https://www.mytek.tn/electromenager/gros-electromenager/climatiseur.html"; // Replace with the URL of the e-commerce site
        WebDriver driver = new ChromeDriver();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");
        String csvFile = "your_file.csv";
        String nameCat="Clima";
        driver.get(url);
        int i = 0;
        Boolean ok = true;
        int rowCount = 0;
        Row row = sheet.createRow(rowCount);

        rowCount++;
        while (ok) {
            System.out.println("1");
            try {
                System.out.println("2");
                Document doc = Jsoup.connect(url).get();
                Elements products = doc.select(".item.product.product-item"); // Replace ".product" with the CSS selector for product elements
                int k = 2;
                for (Element product : products) {

                    Row row1 = sheet.createRow(rowCount++);
                    String name = product.select(".name").text(); // Replace ".name" with the CSS selector for the product name
                    String price = product.select(".price").text(); // Replace ".price" with the CSS selector for the product price
                    String stock = product.select(".stock.available").text(); // Replace ".price" with the CSS selector for the product price
                    String productUrl = product.select(".product-image-photo").attr("src");
                    String description = product.select(".product.description.product-item-description").text();
                    Cell cell11 = row1.createCell(0);
                    Random random = new Random();
                    int id = (random.nextInt(90000) + 50);
                    cell11.setCellValue(id);
                    k++;

                    String ref = ref(name, String.valueOf(id));
                    String newLineData =
                            name+","+nameCat+","+description+","+
                                    "// Text when in stock\n" +
                                    "// Text when backorder allowed\n" +
                                    "// Available for order (0 = No, 1 = Yes)\n" +
                                    "// Product available date\tProduct creation date\n" +
                                    "// Show price (0 = No, 1 = Yes)\tImage URLs (x,y,z...)\n" +
                                    "// Delete existing images (0 = No, 1 = Yes)\tFeature(Name:Value:Position)\n" +
                                    "// Available online only (0 = No, 1 = Yes)\n" +
                                    "// Condition\tCustomizable (0 = No, 1 = Yes)\n" +
                                    "// Uploadable files (0 = No, 1 = Yes)\n" +
                                    "// Text fields (0 = No, 1 = Yes)\tOut of stock\n" +
                                    "// ID / Name of shop\n" +
                                    "// Advanced stock management\n" +
                                    "// Depends On Stock\tWarehouse";

                    // Append the new line to the existing file
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true))) {
                        // Add a new line separator if the file is not empty
                        if (new File(csvFile).length() > 0) {
                            bw.newLine();
                        }
                        // Write the new line
                        bw.write(newLineData);
                        System.out.println("New line inserted successfully.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                // Writing to Excel file

            } catch (IOException e) {
                e.printStackTrace();
            }

            Thread.sleep(1000);
            try {
                driver.findElement(By.cssSelector("#maincontent > div.columns > div.column.main > div:nth-child(3) > div.pages > ul > li.item.pages-item-next > a")).click();
            } catch (Exception e) {
                ok = false;
            }

        }

    }
}
