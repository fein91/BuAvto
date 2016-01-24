package com.buavto.builders.url;

import com.buavto.AutoSite;

public class UrlBuildersFactory {

    public AbstractUrlBuilder create(AutoSite autoSite) {
        switch (autoSite) {
            case AUTO_RIA_COM:
                return createAvtoRiaUrlBuilder();
            case RST_UA:
                return createRstUrlBuilder();
            case OLX_UA:
                return createOlxUrlBuilder();
            case AVTO_BAZAR:
                return createAvtoBazarUrlBuilder();
            default:
                throw new UnsupportedOperationException("not implemented yet");
        }
    }

    public AvtoBazarUrlBuilder createAvtoBazarUrlBuilder() {
        return new AvtoBazarUrlBuilder();
    }

    public OlxUrlBuilder createOlxUrlBuilder() {
        return new OlxUrlBuilder();
    }

    public AvtoRiaUrlBuilder createAvtoRiaUrlBuilder() {
        return new AvtoRiaUrlBuilder();
    }

    public RstUrlBuilder createRstUrlBuilder() {
        return new RstUrlBuilder();
    }
}
