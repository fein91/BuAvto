package com.buavto.strategies;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RstArticlesParserStrategy extends AbstractArticlesParsingStrategy {

    private static final List<String> LITERALS_TO_REMOVE = Arrays.asList("НОВЫЙ", "ПРОДАМ", "— УЖЕ ПРОДАНО — АРХИВ RST", "— АРХИВ RST");

    protected String preProcessArticleTitle(String articleTitle) {
        String result = articleTitle.toUpperCase();
        for (String literalToRemove : LITERALS_TO_REMOVE) {
            result = result.replaceAll(literalToRemove, "");
        }
        return result;
    }

    @Override
    protected long parseUsdPrice(Element articleDiv) {
        Element priceContainer = articleDiv.select("li.rst-ocb-i-d-l-i").first();
        Element usdPrice = priceContainer.select("span.rst-uix-grey").first();
        return priceStringToLong(usdPrice.text());
    }

    @Override
    protected Elements parseArticlesContainers(Element body) {
        Element articlesContainer = body.select("section.rst-ocb1").first();
        Elements articlesDivs = articlesContainer.select("div.rst-ocb-i");
        return articlesDivs;
    }

    @Override
    protected String parseArticleTitle(Element articleDiv) {
        Element photoContainer = articleDiv.select("a.rst-ocb-i-a").first();
        String articleTitle = photoContainer.getElementsByClass("rst-ocb-i-h").first().getElementsByTag("span").first().text();
        return articleTitle;
    }

    @Override
    protected String parseDetailsUrl(Element articleDiv) {
        Element photoContainer = articleDiv.select("a.rst-ocb-i-a").first();
        return "http://rst.ua" + photoContainer.attr("href");
    }

    @Override
    protected String parsePhotoUrl(Element articleDiv) {
        Element photoContainer = articleDiv.select("a.rst-ocb-i-a").first();
        Element img = photoContainer.getElementsByTag("img").first();
        return img.attr("src");
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
    public long getLoadTimeout() {
        return 0;
    }
}
