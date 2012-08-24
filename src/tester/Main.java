package tester;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("you need to write two arguments:");
            System.out.println("1) path to chromedriver.exe");
            System.out.println("2) url to testing");
            return;
        }


        java.util.logging.Logger.getLogger("org.apache.http.impl.client").setLevel(java.util.logging.Level.WARNING);

        System.setProperty("webdriver.chrome.driver", args[0]);
        String url = args[1];

        try {
            WebDriver driver = new ChromeDriver();
            try {
                driver.get(url);
                Instructions result = new Instructions();

                result.findLinks(driver);
                result.findImages(driver);
                result.findForms(driver);

                result.write();


                driver.quit();


            } catch (WebDriverException e) {
                e.getMessage();
            } finally {
                driver.quit();
            }
        } catch (WebDriverException e) {
            e.getMessage();
        }


    }
}
