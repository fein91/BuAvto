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
import java.util.Arrays;
import java.util.List;

@Component
public class RstArticlesParserStrategy extends AbstractArticlesParsingStrategy {

    private static final List<String> LITERALS_TO_REMOVE = Arrays.asList("НОВЫЙ", "ПРОДАМ", "— УЖЕ ПРОДАНО — АРХИВ RST", "— АРХИВ RST");

    @Override
    public List<Article> parseDom(Document dom) {
        Element body = dom.body();
        Element articlesContainer = body.select("section.rst-ocb1").first();
        Elements articlesDivs = articlesContainer.select("div.rst-ocb-i");
        List<Article> result = new ArrayList<>();

        for (Element articleDiv : articlesDivs) {
            try {
                Element photoContainer = articleDiv.select("a.rst-ocb-i-a").first();
                Element href = photoContainer;

                String detailsUrl = "http://rst.ua" + href.attr("href");//OK
                Element img = href.getElementsByTag("img").first();//OK
                String photoUrl = img.attr("src");//OK

                String articleTitle = href.getElementsByClass("rst-ocb-i-h").first().getElementsByTag("span").first().text();
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

                Element priceContainer = articleDiv.select("li.rst-ocb-i-d-l-i").first();
                Element usdPrice = priceContainer.select("span.rst-uix-grey").first();

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

    protected String preProcessArticleTitle(String articleTitle) {
        String result = articleTitle.toUpperCase();
        for (String literalToRemove : LITERALS_TO_REMOVE) {
            result = result.replaceAll(literalToRemove, "");
        }
        return result;
    }

    @Override
    protected long parseUsdPrice(String priceStr) {
        long price = 0;
        try {
            price = Long.parseLong(priceStr.replaceAll("\\$","").replaceAll("'",""));
        } catch (NumberFormatException e) {
            LOGGER.error("Error while parsing price happened " + e);
        }
        return price;
    }

    @Override
    protected String getNextPageControlSelector() {
        return "#next-page";
    }

    @Override
    public String getSiteName() {
        return "rst_ua";
    }

    @Override
    protected long getLoadTimeout() {
        return 0;
    }
}
