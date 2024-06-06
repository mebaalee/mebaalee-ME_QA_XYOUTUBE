package demo;

import net.bytebuddy.agent.builder.AgentBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.ls.LSOutput;

import java.lang.invoke.WrongMethodTypeException;
import java.time.Duration;
import java.util.List;

import static org.testng.Assert.*;

public class TestCases {
    WebDriver driver;
    SoftAssert softAssert = new SoftAssert();

    @BeforeSuite
    public void setup() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterTest
    public void tearDown() {
        driver.close();
        driver.quit();
    }


    @Test
    public void printAboutSection() throws InterruptedException {
        try {
            System.out.println("Test Case 01 Starts : Verify the current URL of the page & Print the About section text");
            driver.get("https://www.youtube.com/");
            String URL = driver.getCurrentUrl();
            Assert.assertEquals(URL, "https://www.youtube.com/", "Wrong Home Page");
            Thread.sleep(2000);


            WebElement home = driver.findElement(By.xpath("//a[@id='endpoint' and @title='Home']"));
            home.click();
            home.sendKeys(Keys.PAGE_DOWN);
            home.sendKeys(Keys.PAGE_DOWN);
            Thread.sleep(2000);

            driver.findElement(By.xpath("//div[@id='guide-links-primary']/a")).click();

            String h1 = driver.findElement(By.tagName("h1")).getText();
            String text1 = driver.findElement(By.xpath("(//p[@class = 'lb-font-display-3 lb-font-color-text-primary'])[1]")).getText();
            String text2 = driver.findElement(By.xpath("(//p[@class = 'lb-font-display-3 lb-font-color-text-primary'])[2]")).getText();
            System.out.println(h1 + " " + text1 + " " + text2);



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test case Failed - Exception thrown");
        }
    }




    @Test
    public void checkMovieRating() throws InterruptedException {
        try {
            System.out.println("Test Case 02 Starts : Verify the genre and rating of the last film under Top selling section");
            driver.get("https://www.youtube.com/");
            Thread.sleep(2000);
            //Invoking a method by passing the tab name
            WrapperAction.selectionOfTab(driver,"Movies");

            //Invoking a method by passing the Topic element
            WebElement topic = driver.findElement(By.xpath("//span[@id='title'][text()='Top selling']"));
            WrapperAction.scrollToTopic(driver,topic);
            Thread.sleep(2000);

            //Invoking a method by passing the button element
            WebElement nextButton = driver.findElement(By.xpath("(//div[contains(@class,'yt-spec-touch-feedback-shape__fill')])[7]"));
            WrapperAction.scrollToEnd(driver,nextButton);
            Thread.sleep(2000);

            //Invoking a method by passing the rating , maturity strings
            String rating = driver.findElement(By.xpath("//div[@id=\"items\"]/ytd-grid-movie-renderer[16]/ytd-badge-supported-renderer/div[2]/p")).getText();
            String maturity = "A";
            softAssert.assertTrue(WrapperAction.checkMature(driver,rating,maturity));
            Thread.sleep(2000);

            //Invoking a method by passing the type, genre strings
            String type = driver.findElement(By.xpath("//*[@id=\"items\"]/ytd-grid-movie-renderer[16]/a/span")).getText();
            String genre1 = "Comedy";
            String genre2 = "Animation";
            softAssert.assertTrue(WrapperAction.checkGenre(driver,type,genre1,genre2));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test case Failed - Exception thrown");
        }
    }




    @Test
    public void printPlaylistName() throws InterruptedException {
        try {
            System.out.println("Test Case 03 Starts : Verify the number of tracks < 50 in the last playlist");
            driver.get("https://www.youtube.com/");
            Thread.sleep(2000);

            //Invoking a method by passing the tab name
            WrapperAction.selectionOfTab(driver,"Music");

            //Invoking a method by passing the Topic element
            WebElement topic = driver.findElement(By.xpath("//*[@tab-title = 'Community']"));
            WrapperAction.scrollToTopic(driver,topic);
            Thread.sleep(2000);

            //Invoking a method by passing the button element
            WebElement nextButton = driver.findElement(By.xpath("(//div[contains(@class, 'yt-spec-touch-feedback-shape__fill')])[8]"));
            WrapperAction.scrollToEnd(driver,nextButton);
            Thread.sleep(2000);
            //Print the title of the album
            String title = driver.findElement(By.xpath("(//h3[contains(@class,'style-scope ytd-compact-station-renderer')])[11]")).getText();
            System.out.println(title);

            //Invoking a method by passing the rating , maturity strings
            String rating = driver.findElement(By.xpath("(//p[@id='video-count-text'])[11]")).getText();
            String maturity = "50";
            softAssert.assertTrue(WrapperAction.checkMature(driver,rating,maturity));
            Thread.sleep(2000);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test case Failed - Exception thrown");
        }
    }




    @Test
    public void printNewsDetails() throws InterruptedException {
        try {
            System.out.println("Test Case 04 Starts : Verify the sum of likes of the first 3 Latest News Posts ");
            driver.get("https://www.youtube.com/");
            Thread.sleep(2000);

            //Invoking a method by passing the tab name
            WrapperAction.selectionOfTab(driver,"News");

            //Invoking a method by passing the Topic element
            WebElement topic = driver.findElement(By.xpath("(//div[@id='title-text'])[2]"));
            WrapperAction.scrollToTopic(driver,topic);
            Thread.sleep(2000);

            //Invoking a method by passing the Titles element
            List<WebElement> titles = driver.findElements(By.xpath("//a[@id='author-text']/span"));
            WrapperAction.iterateThroughTitleList(driver,titles);

            //Invoking a method by passing the bodies element
            List<WebElement> bodies = driver.findElements(By.xpath("//*[@id='home-content-text']/span[1]"));
            WrapperAction.iterateThroughBodyList(driver,bodies);

            //Print the sum of Likes
            List<WebElement> allLikes = driver.findElements(By.xpath("//span[@id='vote-count-middle']"));
            int sumOfLikes = 0;
            for(int i = 0;i<3;i++){
                String likes = allLikes.get(i).getText();
                int likesCount;
                if(likes.endsWith("K")){
                    likesCount = (int) (Double.parseDouble(likes.replace("K", "")) * 1000);
                } else if (likes.equals("null")){
                    likesCount = 0;
                } else{
                    likesCount = Integer.parseInt(likes);
                }
                sumOfLikes += likesCount;
            }
            System.out.println("Sum of likes :" + sumOfLikes);
        } catch (Exception e) {
            e.printStackTrace();
            {
                System.out.println("Test case Failed - Exception thrown");
            }
        }
    }
}
















