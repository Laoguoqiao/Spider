package com.laoguo.spider.htmlUnit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HttpUnitCraw {

    private static int maxThread=5;

    /**
     * 功能描述：抓取页面时不解析页面的js
     * @param url
     * @throws Exception
     */
    public void crawlPageWithoutAnalyseJs(String url) throws Exception{
        //1.创建连接client
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //2.设置连接的相关选项
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setTimeout(10000);
        //3.抓取页面
        HtmlPage page = webClient.getPage(url);
        System.out.println(page.asXml());
        //4.关闭模拟窗口
        webClient.close();
    }

    /**
     * 功能描述：抓取页面时并解析页面的js
     * @param url
     * @throws Exception
     */
    public static String crawlPageWithAnalyseJs(String url) throws Exception{
        //1.创建连接client
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //2.设置连接的相关选项
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);  //需要解析js
        webClient.getOptions().setThrowExceptionOnScriptError(false);  //解析js出错时不抛异常
        webClient.getOptions().setTimeout(10000);  //超时时间  ms
        URL link=new URL(url);

        WebRequest request=new WebRequest(link);
        //3.抓取页面
        HtmlPage page = webClient.getPage(request);
        //4.将页面转成指定格式
        webClient.waitForBackgroundJavaScript(10000);   //等侍js脚本执行完成
        System.out.println("解析后的页面");
        //System.out.println(page.asXml());
        System.out.println("解析结束！！！");
        //5.关闭模拟的窗口
        webClient.close();
        return page.asXml();
    }

    public static void getElement(String url) throws Exception {
        String html = crawlPageWithAnalyseJs(url);
        if (!StringUtils.isEmpty(html)) {
            Document doc = (Document) Jsoup.parse(html);
           System.out.println(doc.getElementsByTag("title").first());
        }
    }

    public static List<String> getAllString(){
        List<String> list=new ArrayList<>();
        for(int i=0;i<100;i++){
            list.add("listName"+i);
        }
        return list;
    }




    public static void main(String[] args) throws Exception {
        HttpUnitCraw crawl = new HttpUnitCraw();
        String url = "http://finance.sina.com.cn/money/forex/20150609/113222385465.shtml";
        List<String> urls=new ArrayList<>();
        urls.add(url);
        ConcurrentLinkedQueue <String> queue=new ConcurrentLinkedQueue<>();
        urls.stream().forEach(itemUrl->{
            queue.add(itemUrl);
        });
        Consume consume=new Consume(queue);
        ExecutorService executorService= Executors.newFixedThreadPool(maxThread);
        for(int i=0;i<maxThread;i++){
            executorService.submit(consume);
        }
        if(consume.stop==true){
            executorService.shutdown();
        }
    }
}
