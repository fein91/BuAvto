package com.buavto.builders;

import com.buavto.model.Article;
import com.buavto.model.Brand;
import com.buavto.model.Model;
import com.buavto.model.Option;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ArticleBuilder {

    private final static Logger LOGGER = LogManager.getLogger(ArticleBuilder.class.getName());

    private Brand brand;
    private Model model;
    private Option option;
    private long price;
    private String detailsUrl;
    private String photoUrl;
    private Date year;
    private String title;

    public ArticleBuilder brand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public ArticleBuilder model(Model model) {
        this.model = model;
        return this;
    }

    public ArticleBuilder option(Option option) {
        this.option = option;
        return this;
    }

    public ArticleBuilder price(long price) {
        this.price = price;
        return this;
    }

    public ArticleBuilder detailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
        return this;
    }

    public ArticleBuilder photoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public ArticleBuilder year(Date year) {
        this.year = year;
        return this;
    }

    public ArticleBuilder year(String year) {
        DateFormat df = new SimpleDateFormat("YYYY");
            try {
                if (year != null) {
                    this.year = df.parse(year);
                }
            } catch (ParseException e) {
                LOGGER.warn("Error occurred while parsing year: [" + year + "]");
            }
        return this;
    }

    public ArticleBuilder title(String title) {
        this.title = title;
        return this;
    }

    public Article build() {
        return new Article(this);
    }

    public Brand getBrand() {
        return brand;
    }

    public Model getModel() {
        return model;
    }

    public long getPrice() {
        return price;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Date getYear() {
        return year;
    }

    public Option getOption() {
        return option;
    }

    public String getTitle() {
        return title;
    }
}
