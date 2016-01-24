package com.buavto.strategies;


import com.buavto.AutoSite;

public class ArticlesParsingStrategyFactory {
    public AbstractArticlesParsingStrategy create(AutoSite autoSite) {
        switch (autoSite) {
            case AUTO_RIA_COM:
                return createAvtoRiaArticlesParsingStrategy();
            case RST_UA:
                return createRstArticlesParsingStrategy();
            case OLX_UA:
                return createOlxArticlesParsingStrategy();
            default:
                throw new UnsupportedOperationException("not implemented yet");
        }
    }

    public OlxArticlesParsingStrategy createOlxArticlesParsingStrategy() {
        return new OlxArticlesParsingStrategy();
    }

    public AvtoRiaArticlesParserStrategy createAvtoRiaArticlesParsingStrategy() {
        return new AvtoRiaArticlesParserStrategy();
    }

    public RstArticlesParserStrategy createRstArticlesParsingStrategy() {
        return new RstArticlesParserStrategy();
    }

}
