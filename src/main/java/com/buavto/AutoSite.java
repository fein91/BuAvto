package com.buavto;

public enum AutoSite {
    AUTO_RIA_COM(1),
    RST_UA(2),
    OLX_UA(3),
    AVTO_BAZAR(4);

    AutoSite(long id) {
        this.id = id;
    }

    private long id;

    public static AutoSite valueOf(long id) {
        for (AutoSite autoSite: AutoSite.values()) {
            if (autoSite.id == id) {
                return autoSite;
            }
        }
        return null;
    };
}