package com.buavto.services;

import com.buavto.dao.BrandsDao;
import com.buavto.model.Brand;
import com.buavto.model.Model;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractArticlesParsingStrategy implements ArticlesParsingStrategy {
    protected final static Logger LOGGER = LogManager.getLogger(AbstractArticlesParsingStrategy.class.getName());

    private static final Pattern YEAR_PATTERN = Pattern.compile("(19[789]\\d|20[01]\\d)");

    @Autowired
    protected BrandsDao brandsDao;

    @Autowired
    protected BrandsService brandsService;

    @Override
    public boolean hasNextPage(Document doc) {
        Element nextPageControl = doc.body().select(getNextPageControlSelector()).first();
        if (nextPageControl == null) {
            LOGGER.warn("Next page control wasn't found on page: [" + doc.title() + "]");
            return false;
        }
        return !nextPageControl.classNames().contains("hide");
    }

    protected String parseYear(String title) {
        Matcher matcher = YEAR_PATTERN.matcher(title);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    protected Brand parseBrand(String title) {
        Iterable<Brand> brands = brandsDao.findAll();

        for (Brand brand : brands) {
            if (title.contains(brand.getName())) {
                return brand;
            }
        }

        LOGGER.warn("Unknown brand in title: " + title);
        return null;
    }

    protected Model parseModel(Brand brand, String modelName) {
        Model model = brandsService.findModel(brand, modelName);

        if (model == null) {
            model = brandsService.addNewModel(brand, modelName);
        }

        return model;
    }

    protected abstract long parseUsdPrice(String price);

    protected abstract String preProcessArticleTitle(String articleTitle);

    protected abstract String getNextPageControlSelector();

    public abstract String getSiteName();

    protected abstract long getLoadTimeout();
}
