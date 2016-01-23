package com.buavto.builders.url;

import com.buavto.AutoSite;

public class UrlBuildersFactory {

    public AbstractUrlBuilder create(AutoSite autoSite) {
        switch (autoSite) {
            case AUTO_RIA_COM:
                return createAvtoRiaUrlBuilder();
            case RST_UA:
                return createRstUrlBuilder();
            default:
                throw new UnsupportedOperationException("not implemented yet");
        }
    }

    public AvtoRiaUrlBuilder createAvtoRiaUrlBuilder() {
        return new AvtoRiaUrlBuilder();
    }

    public RstUrlBuilder createRstUrlBuilder() {
        return new RstUrlBuilder();
    }
}
