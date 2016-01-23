package com.buavto.services;

import com.buavto.builders.ArticleBuilder;
import com.buavto.model.Article;
import com.buavto.model.Brand;
import com.buavto.model.Model;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AvtoRiaArticlesParserStrategy extends AbstractArticlesParsingStrategy {

    @Override
    public List<Article> parseDom(Document doc) {
        Element body = doc.body();
        Elements articlesContainer = body.getElementsByClass("app-content");
        Elements articlesDivs = articlesContainer.select(".ticket-item");
        List<Article> result = new ArrayList<>();

        for (Element articleDiv : articlesDivs) {
            try {
                Element photoContainer = articleDiv.select("div.ticket-photo").first();
                Element href = photoContainer.getElementsByTag("a").first();

                String detailsUrl = "https://auto.ria.com" + href.attr("href");
                Element img = href.getElementsByTag("img").first();
                String photoUrl = img.attr("src");

                String articleTitle = href.attr("title");
                LOGGER.info("articleTitle: [" + articleTitle + "] is processing");

                articleTitle = preProcessArticleTitle(articleTitle);

                String year = parseYear(articleTitle);
                if (year != null) {
                    articleTitle = articleTitle.replace(year, "").trim();
                }

                Brand brand = parseBrand(articleTitle);
                Model model = null;
                if (brand != null) {
                    String modelName = articleTitle.replace(brand.getName(), "").trim();
                    model = parseModel(brand, modelName);
                }

                Element priceDiv = articleDiv.select("div.price-ticket").first();
                Element usdPrice = priceDiv.select("strong.green").first();

                long price = parseUsdPrice(usdPrice.text());

                Article article = new ArticleBuilder()
                        .brand(brand)
                        .model(model)
                        .year(year)
                        .price(price)
                        .detailsUrl(detailsUrl)
                        .photoUrl(photoUrl)
                        .build();
                result.add(article);

                LOGGER.info("Article parsed: " + article);
            } catch (Exception e) {
                LOGGER.error("Troubles with parsing ticket-item: " + articleDiv.outerHtml());
            }
        }

        return result;
    }

    @Override
    protected long parseUsdPrice(String priceStr) {
        long price = 0;
        try {
            price = Long.parseLong(priceStr.replaceAll("\\s", ""));
        } catch (NumberFormatException e) {
            LOGGER.error("Error while parsing price happened " + e);
        }
        return price;
    }

    @Override
    protected String getNextPageControlSelector() {
        return ".controls.next";
    }

    @Override
    public String getSiteName() {
        return "avto_ria_com";
    }

    @Override
    protected long getLoadTimeout() {
        return 1500;
    }

    @Override
    protected String preProcessArticleTitle(String articleTitle) {
        return articleTitle.toUpperCase();
    }
}
