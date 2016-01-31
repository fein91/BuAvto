package com.buavto.strategies;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class AvtoBazarArticlesParsingStrategy extends AbstractArticlesParsingStrategy {
    @Override
    protected long parseUsdPrice(Element articleDiv) {
        String priceRow = articleDiv.select(".tips").text();
        return priceStringToLong(priceRow.split("/")[1]);
    }

    @Override
    protected Elements parseArticlesContainers(Element body) {
        return body.select("#results").first().getElementsByClass("car");
    }

    @Override
    protected String parseArticleTitle(Element articleDiv) {
        Element header = articleDiv.getElementsByClass("h").first();
        String brandsAndTitle = header.getElementsByTag("a").text();
        String year = header.getElementsByTag("strong").first().text();
        return brandsAndTitle + " " + year;
    }

    @Override
    protected String parseDetailsUrl(Element articleDiv) {
        return "http://avtobazar.infocar.ua" + articleDiv.getElementsByClass("h").first().getElementsByTag("a").attr("href");
    }

    @Override
    protected String parsePhotoUrl(Element articleDiv) {
        return articleDiv.select(".flip").first().getElementsByTag("img").first().attr("src");
    }

    @Override
    protected String preProcessArticleTitle(String articleTitle) {
        return articleTitle.toUpperCase();
    }

    @Override
    public boolean hasNextPage(Document doc) {
        Element pagingContainer = doc.body().select("#paging").first();
        if (pagingContainer == null) {
            return false;
        }
        Element nextPageContainer = pagingContainer.getElementById("next");
        Element selectedPageContainer = pagingContainer.getElementsByClass("sel").first();
        return !nextPageContainer.attr("href").equals(selectedPageContainer.attr("href"));
    }

    @Override
    protected String getNextPageControlSelector() {
        return null;
    }

    @Override
    public String getSiteName() {
        return "avto_bazar";
    }

    @Override
    public long getLoadTimeout() {
        return 0;
    }
}
