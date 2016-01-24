package com.buavto.strategies;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class AvtoRiaArticlesParserStrategy extends AbstractArticlesParsingStrategy {

    @Override
    protected long parseUsdPrice(Element articleDiv) {
        Element priceDiv = articleDiv.select("div.price-ticket").first();
        Element usdPrice = priceDiv.select("strong.green").first();
        return priceStringToLong(usdPrice.text());
    }

    @Override
    protected Elements parseArticlesContainers(Element body) {
        Elements articlesContainer = body.getElementsByClass("app-content");
        Elements articlesDivs = articlesContainer.select(".ticket-item");
        return articlesDivs;
    }

    @Override
    protected String parseArticleTitle(Element articleDiv) {
        Element photoContainer = articleDiv.select("div.ticket-photo").first();
        Element href = photoContainer.getElementsByTag("a").first();
        String articleTitle = href.attr("title");
        return articleTitle;
    }

    @Override
    protected String parseDetailsUrl(Element articleDiv) {
        Element photoContainer = articleDiv.select("div.ticket-photo").first();
        Element href = photoContainer.getElementsByTag("a").first();

        String detailsUrl = "https://auto.ria.com" + href.attr("href");
        return detailsUrl;
    }

    @Override
    protected String parsePhotoUrl(Element articleDiv) {
        Element photoContainer = articleDiv.select("div.ticket-photo").first();
        Element href = photoContainer.getElementsByTag("a").first();
        Element img = href.getElementsByTag("img").first();
        String photoUrl = img.attr("src");
        return photoUrl;
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
    public long getLoadTimeout() {
        return 1500;
    }

    @Override
    protected String preProcessArticleTitle(String articleTitle) {
        return articleTitle.toUpperCase();
    }
}
