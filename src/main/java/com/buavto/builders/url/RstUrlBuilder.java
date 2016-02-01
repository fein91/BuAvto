package com.buavto.builders.url;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("prototype")
public class RstUrlBuilder extends AbstractUrlBuilder {

    private final static String URL_PREFIX = "http://rst.ua/oldcars/?task=newresults";

    private Set<Integer> bodySet = new TreeSet<>();

    public RstUrlBuilder() {
        //Cars body ids
        bodySet.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 10, 11, 27));
        page = 1;
    }


    public RstUrlBuilder addBody(int bodyId) {
        bodySet.add(bodyId);
        return this;
    }

    private final static String TEMPLATE = "http://rst.ua/oldcars/?task=newresults" +
            "&make[]=0" +
            "&year[]=2014" +
            "&year[]=0" +
            "&price[]=11000" +
            "&price[]=14000" +
            "&photos=1" +
            "&engine[]=0" +
            "&engine[]=0" +
            "&gear=0" +
            "&fuel=0" +
            "&drive=0" +
            "&condition=0" +
            "&from=sform" +
            "&start=0" +
            "&results=4" +
            "&body[]=1" +
            "&body[]=2" +
            "&body[]=3" +
            "&body[]=4" +
            "&body[]=5" +
            "&body[]=6" +
            "&body[]=10" +
            "&body[]=11" +
            "&body[]=27";

    @Override
    public String build() {
        StringBuilder strBuilder = new StringBuilder(URL_PREFIX);
        strBuilder.append("&make[]=").append(0)
                .append("&year[]=").append(s_yers)
                .append("&year[]=").append(po_yers)
                .append("&price[]=").append(price_ot)
                .append("&price[]=").append(price_do)
                .append("&photos=").append(1)
                .append("&engine[]=").append(0)
                .append("&engine[]=").append(0)
                .append("&gear=").append(0)
                .append("&fuel=").append(0)
                .append("&drive=").append(0)
                .append("&condition=").append(0)
                .append("&from=").append("sform")
                .append("&results=").append(4)
                .append("&start=").append(page);

        for (int bodyId : bodySet) {
            strBuilder.append("&body[]=").append(bodyId);
        }

        String urlString = strBuilder.toString();
        LOGGER.info("[rst.ua] url was build: " + urlString);
        return urlString;
    }
}
