package com.buavto.services;


import com.buavto.AutoSite;

public class ArticlesParsingStrategyFactory {
    public AbstractArticlesParsingStrategy create(AutoSite autoSite) {
        switch (autoSite) {
            case AUTO_RIA_COM:
                return createAvtoRiaArticlesParsingStrategy();
            case RST_UA:
                return createRstArticlesParsingStrategy();
            default:
                throw new UnsupportedOperationException("not implemented yet");
        }
    }

    public AvtoRiaArticlesParserStrategy createAvtoRiaArticlesParsingStrategy() {
        return new AvtoRiaArticlesParserStrategy();
    }

    public RstArticlesParserStrategy createRstArticlesParsingStrategy() {
        return new RstArticlesParserStrategy();
    }

}
