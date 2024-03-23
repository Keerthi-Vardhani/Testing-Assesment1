package Assesment.Assesment;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class url_check {
    public static void main(String[]args) {
        WebDriver driver;
        System.setProperty("webdriver.chrome.driver", "C:/Users/Keerthi Vardhani/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
        driver=new ChromeDriver();

        String url="https://vitap.ac.in/";
        List<String> toCheckList = new ArrayList<String>();
        Set<String> visitedLinks = new HashSet<String>(); // Track visited links
        toCheckList.add(url);
        List<String> brokenLinks = new ArrayList<String>();

        while (!toCheckList.isEmpty()) {
            String currentUrl = toCheckList.remove(0);
            if (!isValid(currentUrl) || visitedLinks.contains(currentUrl)) { // Check if URL is valid and not visited
            
                brokenLinks.add(currentUrl);
                continue;
            }

            driver.get(currentUrl);
            visitedLinks.add(currentUrl); // Mark current link as visited
            List<String> newLinks = getHyperlinks(driver);
            for (String link : newLinks) {
                if (link != null && !visitedLinks.contains(link) && !toCheckList.contains(link) && !link.equals(url)) {
                    System.out.println(link);
                    toCheckList.add(link);
                }
            }
        }

        System.out.println("Broken links:");
        for (String link : brokenLinks) {
            System.out.println(link);
        }

        driver.quit();
    }

    private static boolean isValid(String url) {
        try {
            WebDriver driver = new ChromeDriver();
            driver.get(url);
            driver.quit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static List<String> getHyperlinks(WebDriver driver) {
        List<String> links = new ArrayList<String>();
        List<WebElement> elements = driver.findElements(By.tagName("a"));
        for (WebElement element : elements) {
            String href = element.getAttribute("href");
            if (href != null && !href.isEmpty()) {
                links.add(href);
            }
        }
        return links;
    }
}
