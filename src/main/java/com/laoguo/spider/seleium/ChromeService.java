package com.laoguo.spider.seleium;


import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ChromeService  {

    private static  ChromeDriverService service;
    public static void createAndStartServer(){
        ChromeDriverService service=new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("chromedriver.exe"))
                .usingAnyFreePort().build();
        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        URL url= service.getUrl();

    }

    public void openUrl(URL serverUrl,String url){
        WebDriver driver=new RemoteWebDriver(serverUrl, DesiredCapabilities.chrome());
        driver.get(url);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();
    }

}
