package com.buavto.rest;

import com.buavto.AutoSite;
import com.buavto.model.Article;
import com.buavto.model.SearchRequest;
import com.buavto.services.ArticlesService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ArticlesController {

    private final static Logger LOGGER = LogManager.getLogger(ArticlesController.class.getName());

    @Autowired
    private ArticlesService articlesService;

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public Iterable<Article> getArticles() {
        return articlesService.findAll();
    }

    @RequestMapping(value = "/articles/parse", method = RequestMethod.POST)
    public void parseArticles(@RequestParam("autoSiteId") int autoSiteId,
                              @RequestParam("priceFrom") int priceFrom,
                              @RequestParam("priceTo") int priceTo,
                              @RequestParam("yearFrom") int yearFrom,
                              @RequestParam("mark") int mark,
                              @RequestParam("model") int model) {
        LOGGER.info("autoSiteId " + AutoSite.valueOf(autoSiteId));
        LOGGER.info("priceFrom " + priceFrom);
        LOGGER.info("priceTo " + priceTo);
        LOGGER.info("yearFrom " + yearFrom);

        articlesService.parse(new SearchRequest(AutoSite.valueOf(autoSiteId), priceFrom, priceTo, yearFrom, mark, model));
    }

    @RequestMapping(value = "/articles/parseHtml", method = RequestMethod.POST)
    public void parseHtml(@RequestParam("autoSiteId") int autoSiteId) {
        AutoSite site = AutoSite.valueOf(autoSiteId);
        LOGGER.info("autoSiteId " + site);

        articlesService.parseArticlesFromHtml(site);
    }

    @RequestMapping(value = "/articles/parseDetailsPage", method = RequestMethod.POST)
    public void parseDetailsPage(@RequestParam("autoSiteId") int autoSiteId) {
        AutoSite site = AutoSite.valueOf(autoSiteId);
        LOGGER.info("autoSiteId " + site);

        articlesService.parseDetailsPages(site);
    }
}
