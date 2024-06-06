package demo;


import com.sun.jna.WString;
import org.openqa.selenium.*;

import java.util.List;

public class WrapperAction {
    WebDriver driver;

    public static String textContents(WebDriver driver, By locator) {
        WebElement textContent = driver.findElement(locator);
        return textContent.getText();
    }

    public static void selectionOfTab(WebDriver driver, String tab) {
        try {
            WebElement selectedTab = driver.findElement(By.xpath("//*[@class='title style-scope ytd-guide-entry-renderer'][text()='" + tab + "']"));
            selectedTab.click();
        } catch (Exception e) {
            System.out.println("Exception occurred!" + e.getMessage());
        }
    }

    public static void scrollToTopic(WebDriver driver, WebElement topic) {
        try {
            Point point = topic.getLocation();
            int xcoord = point.getX();
            int ycoord = point.getY();
            //Scroll up to the element
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(" + xcoord + ", " + ycoord + ");");
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Exception occurred!" + e.getMessage());
        }
    }


    public static void scrollToEnd(WebDriver driver, WebElement nextButton) {
        try {
            boolean yes = nextButton.isEnabled();
            while (yes) {
                nextButton.click();
            }
        } catch (Exception e) {
            System.out.println("Exception occurred!" + e.getMessage());
        }
    }

    public static boolean checkMature(WebDriver driver, String rating, String maturity) {
        try {
            boolean status;
            if (rating.contains(maturity)) {
                status = true;
            } else {
                status = false;
            }
            System.out.println(status);
        } catch (Exception e) {
            System.out.println("Exception occurred!" + e.getMessage());
        }
        return false;
    }


    public static boolean checkGenre(WebDriver driver, String type, String genre1, String genre2) {
        try {
            boolean status;
            if (type.contains(genre1)) {
                status = true;
            } else if (type.contains(genre2)) {
                status = true;
            } else {
                status = false;
            }
            System.out.println(status);
        } catch (Exception e) {
            System.out.println("Exception occurred!" + e.getMessage());
        }
        return false;
    }

    public static void iterateThroughTitleList(WebDriver driver, List<WebElement> titles) {
        for (int i = 0; i < 3; i++) {
            String title = titles.get(i).getText();
            System.out.println(title);
        }
    }

    public static void iterateThroughBodyList(WebDriver driver, List<WebElement> bodies) {
        for (int i = 0; i < 3; i++) {
            String body = bodies.get(i).getText();
            System.out.println(body);
        }
    }
}
