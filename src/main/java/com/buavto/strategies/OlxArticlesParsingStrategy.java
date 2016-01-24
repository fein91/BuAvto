package com.buavto.strategies;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class OlxArticlesParsingStrategy extends AbstractArticlesParsingStrategy {

    @Override
    protected long parseUsdPrice(Element articleDiv) {
        return priceStringToLong(articleDiv.select(".price").first().getElementsByTag("strong").first().text());
    }

    @Override
    protected Elements parseArticlesContainers(Element body) {
        return body.select("#offers_table").first().select(".offer");
    }

    @Override
    protected String parseArticleTitle(Element articleDiv) {
        return articleDiv.select(".fleft").first().attr("alt");
    }

    @Override
    protected String parseDetailsUrl(Element articleDiv) {
        return articleDiv.select(".detailsLink").first().attr("href");
    }

    @Override
    protected String parsePhotoUrl(Element articleDiv) {
        return articleDiv.select(".fleft").attr("src");
    }

    @Override
    protected String preProcessArticleTitle(String articleTitle) {
        return articleTitle.toUpperCase();
    }

    @Override
    protected String getNextPageControlSelector() {
        return ".fbold.next.abs.large";
    }

    @Override
    public String getSiteName() {
        return "olx_ua";
    }

    @Override
    public long getLoadTimeout() {
        return 0;
    }
}
