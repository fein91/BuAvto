package com.buavto;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.buavto"})
@ImportResource("classpath:config.xml")
public class Application {
    private final static Logger LOGGER = LogManager.getLogger(Application.class.getName());
    private final static String PATH_TO_HTMLS = ".\\target\\htmls\\";

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
//          readHtmlFromSite(ctx, AutoSite.AVTO_BAZAR);
       // readHtmlFromSite(ctx, AutoSite.AUTO_RIA_COM);
//        readHtmlFromSite(ctx, AutoSite.RST_UA);
//        readHtmlFromSite(ctx, AutoSite.OLX_UA);
       // parseArticles(ctx, AutoSite.AUTO_RIA_COM);
//        parseArticles(ctx, AutoSite.RST_UA);
//        parseArticles(ctx, AutoSite.OLX_UA);
//       parseArticles(ctx, AutoSite.AVTO_BAZAR);
    }
}



