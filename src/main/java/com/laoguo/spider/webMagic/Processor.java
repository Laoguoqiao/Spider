package com.laoguo.spider.webMagic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class Processor implements PageProcessor {

    private static final Logger LOG= LoggerFactory.getLogger(Processor.class);
    private static  String username="";

    //网站相关配置
    private Site site=Site.me().setRetryTimes(3).setSleepTime(1000);
    @Override
    public void process(Page page) {
        if (!page.getUrl().regex("http://www.cnblogs.com/[a-z 0-9 -]+/p/[0-9]{7}.html").match()) {
            page.addTargetRequests(
                    //*[@id="mainContent"]/div/div[1]/div[2]
                    //*[@id="mainContent"]/div/div[2]/div[2]
                    //*[@id="mainContent"]/div/div[5]/div[2]
                    //*[@id="mainContent"]/div/div[5]/div[2]/a
                    page.getHtml().xpath("//*[@id=\"mainContent\"]/div/div/div[@class=\"postTitle\"]/a/@href").all());
        } else {
            page.putField(page.getHtml().xpath("//*[@id=\"mainContent\"]/div/div/div/a/text").toString(),
                    page.getHtml().xpath("//*[@id=\"mainContent\"]/div/div/div/a/@href").toString());
            LOG.info(page.getHtml().xpath("//*[@id=\"mainContent\"]/div/div/div/").toString(),
                    page.getHtml().xpath("//*[@id=\"mainContent\"]/div/div/div/a/@href").toString());
        }

    }

    @Override
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {

        org.apache.log4j.LogManager.resetConfiguration();
        org.apache.log4j.PropertyConfigurator.configure("/G:/IDEAcode/spider/target/classes/log4j.properties");
        Spider.create(new Processor()).addUrl("http://www.cnblogs.com/justcooooode/")
                .addPipeline(new ConsolePipeline()).run();
    }
}
