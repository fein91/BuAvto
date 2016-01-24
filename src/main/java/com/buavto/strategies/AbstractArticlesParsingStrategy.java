package com.buavto.strategies;

import com.buavto.builders.ArticleBuilder;
import com.buavto.dao.BrandsDao;
import com.buavto.model.Article;
import com.buavto.model.Brand;
import com.buavto.model.Model;
import com.buavto.model.Option;
import com.buavto.services.BrandsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
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
    public List<Article> parseDom(Document dom) {
        Element body = dom.body();
        Elements articlesDivs = parseArticlesContainers(body);
        List<Article> result = new ArrayList<>();

        for (Element articleDiv : articlesDivs) {
            Article article = parseArticleDiv(articleDiv);
            if (article != null) {
                result.add(article);
            }
        }

        return result;
    }

    protected Article parseArticleDiv(Element articleDiv) {
        try {
            String articleTitle = parseArticleTitle(articleDiv);
            String detailsUrl = parseDetailsUrl(articleDiv);
            String photoUrl = parsePhotoUrl(articleDiv);
            long price = parseUsdPrice(articleDiv);

            Article article = createArticleBuilderFromTitle(articleTitle)
                    .price(price)
                    .detailsUrl(detailsUrl)
                    .photoUrl(photoUrl)
                    .build();
            LOGGER.info("Article parsed: " + article);
            return article;
        } catch (Exception e) {
            LOGGER.error("Troubles with parsing ticket-item: " + articleDiv.outerHtml(), e);
        }
        return null;
    }

    protected ArticleBuilder createArticleBuilderFromTitle(String articleTitle) {
        articleTitle = preProcessArticleTitle(articleTitle);

        String year = parseYear(articleTitle);
        if (year != null) {
            articleTitle = articleTitle.replace(year, "").trim();
        }

        Brand brand = parseBrand(articleTitle);
        Model model = null;
        Option option = null;
        String rawModelName = articleTitle;
        if (brand != null) {
            rawModelName = articleTitle.replace(brand.getName(), "").trim();
            model = parseModel(brand, rawModelName);
            option = parseOption(model, rawModelName);
        }
        return new ArticleBuilder()
                .year(year)
                .brand(brand)
                .model(model)
                .option(option)
                .title(rawModelName);
    }

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

    protected Model parseModel(Brand brand, String rawModelName) {
        Model model = brandsService.findModel(brand, rawModelName);

        if (model == null) {
            model = brandsService.addNewModel(brand, rawModelName);
        }

        return model;
    }

    protected Option parseOption(Model model, String rawModelName) {
        for (Option option : model.getOptions()) {
            if (rawModelName.contains(option.getName())) {
                return option;
            }
        }
        return null;
    }

    protected long priceStringToLong(String priceStr) {
        long price = 0;
        try {
            price = Long.parseLong(priceStr.replaceAll("\\$", "").replaceAll("'", "").replaceAll("\\s", ""));
        } catch (NumberFormatException e) {
            LOGGER.error("Error while parsing price happened " + e);
        }
        return price;
    }

    protected abstract long parseUsdPrice(Element articleDiv);

    protected abstract Elements parseArticlesContainers(Element body);

    protected abstract String parseArticleTitle(Element articleDiv);

    protected abstract String parseDetailsUrl(Element articleDiv);

    protected abstract String parsePhotoUrl(Element articleDiv);

    protected abstract String preProcessArticleTitle(String articleTitle);

    protected abstract String getNextPageControlSelector();

    public abstract String getSiteName();

    public abstract long getLoadTimeout();
}
