package com.buavto.builders.url;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class AvtoBazarUrlBuilder extends AbstractUrlBuilder {
    private static final String TEMPLATE = "http://avtobazar.infocar.ua/search-car/" +
            "?new[]=0" +
            "&y1=2014" +
            "&p1=11000" +
            "&p2=13000" +
            "&c[]=11101" +
            "&fo=1" +
            "&w=1";
    private static final String URL_PREFIX = "http://avtobazar.infocar.ua/search-car/";
    private Region region;
    private long onPage;

    public AvtoBazarUrlBuilder() {
        this.page = 1;
        this.region = Region.NO_REGION;
        this.onPage = 30;
    }

    public AbstractUrlBuilder region(Region region) {
        this.region = region;
        return this;
    }

    public AbstractUrlBuilder onPage(long onPage) {
        this.onPage = onPage;
        return this;
    }

    @Override
    public String build() {
        StringBuilder strBuilder = new StringBuilder(URL_PREFIX);
        strBuilder.append("?new[]=0")
                .append("&y1=").append(s_yers)
                .append("&p1=").append(price_ot)
                .append("&p2=").append(price_do)
                .append("&c[]=").append(region.getId())
                .append("&fo=1")
                .append("&w=").append(page)
                .append("&onp=").append(onPage);

        String urlString = strBuilder.toString();
        LOGGER.info("[avtobazar.infocar.ua] url was build: " + urlString);
        return urlString;
    }

    public enum Region {
        KIEV(11101L),
        NO_REGION(0L);

        private long id;

        Region(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }
    }
}
