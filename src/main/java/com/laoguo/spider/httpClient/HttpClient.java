package com.laoguo.spider.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HttpClient {

    public static String getStringHtml(String url) throws ClientProtocolException, IOException {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址
        //"https://www.cnblogs.com/"
        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
//        HttpHost proxy = new HttpHost("112.85.168.223", 9999);
//        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//        request.setConfig(config);
        response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity httpEntity = response.getEntity();
            String html = EntityUtils.toString(httpEntity, "utf-8");
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
            return html;
        } else {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
            return url + "not successfully";
        }
    }

    public static void  getDataFromHtml(String html){
        //6.Jsoup解析html
        Document document = Jsoup.parse(html);
        //像js一样，通过标签获取title
        System.out.println(document.getElementsByTag("title").first());
        //像js一样，通过id 获取文章列表元素对象
        Element postList = document.getElementById("post_list");
        //像js一样，通过class 获取列表下的所有博客
        Elements postItems = postList.getElementsByClass("post_item");
        //循环处理每篇博客
        for (Element postItem : postItems) {
            //像jquery选择器一样，获取文章标题元素
            Elements titleEle = postItem.select(".post_item_body a[class='titlelnk']");
            System.out.println("文章标题:" + titleEle.text());;
            System.out.println("文章地址:" + titleEle.attr("href"));
            //像jquery选择器一样，获取文章作者元素
            Elements footEle = postItem.select(".post_item_foot a[class='lightblue']");
            System.out.println("文章作者:" + footEle.text());;
            System.out.println("作者主页:" + footEle.attr("href"));
            System.out.println("*********************************");
        }
    }

    public static void  main(String[] args){
//        try {
//            System.out.println(getDatagetStringHtml("https://www.cnblogs.com"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            getDataFromHtml(getStringHtml("https://www.cnblogs.com"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
