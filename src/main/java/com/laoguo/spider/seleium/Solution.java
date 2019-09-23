package com.laoguo.spider.seleium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.IOException;

public class Solution {


    public void getHtml(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions chromeOptions=new ChromeOptions();
        chromeOptions.setHeadless(true);
        WebDriver driver=new ChromeDriver(chromeOptions);
        driver.get(url);
        Thread.sleep(1000);
        String html=driver.getPageSource();
        System.out.println(html);
        driver.close();
    }
    public static void main(String[] args){
        try {
            new Solution().getHtml("https://www.csdn.net/nav/arch");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
