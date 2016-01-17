package com.buavto.services;

import com.buavto.builders.ArticleBuilder;
import com.buavto.dao.BrandsDao;
import com.buavto.model.Article;
import com.buavto.model.Brand;
import com.buavto.model.Model;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AvtoRiaArticlesParserStrategy implements ArticlesParsingStrategy {

    private final static Logger LOGGER = LogManager.getLogger(AvtoRiaArticlesParserStrategy.class.getName());
    private static final Pattern YEAR_PATTERN = Pattern.compile("(19[789]\\d|20[01]\\d)");

    @Autowired
    private BrandsDao brandsDao;

    @Autowired
    private BrandsService brandsService;

    @Override
    public List<Article> parseDom(Document doc) {
        List<Element> articlesDiv = doc.select("div.ticket-item");
        List<Article> result = new ArrayList<>();

        for (Element articleDiv : articlesDiv) {
            Element photoDiv = articleDiv.select("div.ticket-photo").first();
            Element href = photoDiv.getElementsByTag("a").first();
            String hrefTitle = href.attr("title").toUpperCase();
            LOGGER.info("hrefTitle: [" + hrefTitle + "] is processing");

            String year = parseYear(hrefTitle);
            if (year != null) {
                hrefTitle = hrefTitle.replace(year, "").trim();
            }

            Brand brand = parseBrand(hrefTitle);
            Model model = null;
            if (brand != null) {
                model = parseModel(brand, hrefTitle);
            }

            String detailsUrl = href.attr("href");
            Element img = href.getElementsByTag("img").first();
            String photoUrl = img.attr("src");

            result.add(new ArticleBuilder()
                            .brand(brand)
                            .model(model)
                            .year(year)
                            .detailsUrl(detailsUrl)
                            .photoUrl(photoUrl)
                            .build()
            );
        }

        return result;
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
        return null;
    }

    protected Model parseModel(Brand brand, String title) {
        String modelName = title.replace(brand.getName(), "").trim();

        Model model = brandsService.findModel(brand, modelName);

        if (model == null) {
            brandsService.addNewModel(brand, modelName);
        }

        return model;
    }
}
