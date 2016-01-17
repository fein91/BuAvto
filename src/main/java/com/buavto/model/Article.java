package com.buavto.model;

import com.buavto.builders.ArticleBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = Article.TABLE_NAME)
public class Article {

    public static final String TABLE_NAME = "article";
    private static final String SEQUENCE_GENERATOR = "articles_seq_gen";
    private static final String SEQUENCE_NAME = "articles_seq";
    private static final String BRAND_FK = "brand_fk";
    private static final String MODEL_FK = "model_fk";

    public Article(ArticleBuilder builder) {
        this.brand = builder.getBrand();
        this.model = builder.getModel();
        this.detailsUrl = builder.getDetailsUrl();
        this.photoUrl = builder.getPhotoUrl();
        this.price = builder.getPrice();
        this.year = builder.getYear();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR)
    @SequenceGenerator(name = SEQUENCE_GENERATOR, sequenceName = SEQUENCE_NAME, allocationSize=1)
    private long id;

    @ManyToOne
    @JoinColumn(name = BRAND_FK)
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = MODEL_FK)
    private Model model;

    private long price;
    private String detailsUrl;
    private String photoUrl;
    private Date year;

    public long getId() {
        return id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", brand=" + brand +
                ", model=" + model +
                ", price=" + price +
                ", detailsUrl='" + detailsUrl + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", year=" + year +
                '}';
    }
}
