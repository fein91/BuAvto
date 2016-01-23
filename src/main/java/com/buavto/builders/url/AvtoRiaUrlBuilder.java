package com.buavto.builders.url;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class AvtoRiaUrlBuilder extends AbstractUrlBuilder {
    private final static String URL_PREFIX = "https://auto.ria.com/search/";

    public AvtoRiaUrlBuilder() {
        this.category = AvtoRiaCategory.CARS;
        this.marka_id = 0;
        this.model_id = 0;
        this.state = 0;
        this.currency = 1;
    }

    public AvtoRiaUrlBuilder(AvtoRiaCategory category, long marka_id, long model_id, long state) {
        this.category = category;
        this.marka_id = marka_id;
        this.model_id = model_id;
        this.state = state;
    }

    private final AvtoRiaCategory category;
    private final long marka_id;
    private final long model_id;
    private final long state;
    private long currency;
    private long countpage;

    public AvtoRiaUrlBuilder currency(long currency) {
        this.currency = currency;
        return this;
    }

    public AvtoRiaUrlBuilder countpage(long countpage) {
        this.countpage = countpage;
        return this;
    }


    private final static String TEMPLATE =
            "https://auto.ria.com/search/" +
                "?category_id=1&marka_id=0&model_id=0&state=0" +
                    "#category_id=1" +
                        "&state[0]=0" +
                        "&s_yers[0]=2014" +
                        "&po_yers[0]=0" +
                        "&price_ot=10000" +
                        "&price_do=14000" +
                        "&currency=1" +
                        "&marka_id[0]=0" +
                        "&model_id[0]=0" +
                        "&countpage=10" +
                        "&page=1";

    @Override
    public String build() {
        StringBuilder strBuilder = new StringBuilder(URL_PREFIX);
        strBuilder.append("?category_id=").append(category.getId())
                .append("&marka_id=").append(marka_id)
                .append("&model_id=").append(model_id)
                .append("&state=").append(state)
                .append("#")
                .append("category_id=").append(category.getId())
                .append("&marka_id[0]=").append(marka_id)
                .append("&model_id[0]=").append(model_id)
                .append("&state[0]=").append(state)
                .append("&s_yers[0]=").append(s_yers)
                .append("&po_yers[0]=").append(po_yers)
                .append("&price_ot=").append(price_ot)
                .append("&price_do=").append(price_do)
                .append("&currency=").append(currency)
                .append("&countpage=").append(countpage)
                .append("&page=").append(page);

        String urlString = strBuilder.toString();
        LOGGER.info("[auto.ria.com] url was build: " + urlString);
        return urlString;
    }

    private enum AvtoRiaCategory {

        CARS(1);

        private long id;

        AvtoRiaCategory(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }
    }

}
