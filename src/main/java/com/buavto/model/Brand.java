package com.buavto.model;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import java.util.List;

@Entity
@Table(name = Brand.TABLE_NAME)
public class Brand {
    public static final String TABLE_NAME = "brands";
    private static final String SEQUENCE_GENERATOR = "brands_seq_gen";
    private static final String SEQUENCE_NAME = "brands_seq";
    private static final String BRAND_FK = "brand_fk";
    private static final String MODEL_REFERENCED_COLUMN = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR)
    @SequenceGenerator(name = SEQUENCE_GENERATOR, sequenceName = SEQUENCE_NAME, allocationSize=1)
    private long id;
    private String name;
    private String url;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = BRAND_FK, referencedColumnName = MODEL_REFERENCED_COLUMN)
    private List<Model> models;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", models=" + models +
                '}';
    }
}
