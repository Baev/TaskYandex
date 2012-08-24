package tester;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.PrintWriter;

public class Tester {


    public void run(String url, PrintWriter pw) {

        java.util.logging.Logger.getLogger("org.apache.http.impl.client").setLevel(java.util.logging.Level.WARNING);


        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\Charlie\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.get(url);
        Instructions result = new Instructions();


        result.findLinks(driver);
        result.findImages(driver);
        result.findForms(driver);

        result.write(pw);


        driver.quit();


    }
}
