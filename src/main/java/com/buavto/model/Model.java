package com.buavto.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = Model.TABLE_NAME)
public class Model {
    public static final String TABLE_NAME = "models";
    private static final String SEQUENCE_GENERATOR = "models_seq_gen";
    private static final String SEQUENCE_NAME = "models_seq";
    private static final String MODEL_FK = "model_fk";
    private static final String OPTION_REFERENCED_COLUMN = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR)
    @SequenceGenerator(name = SEQUENCE_GENERATOR, sequenceName = SEQUENCE_NAME, allocationSize=1)
    private long id;
    private String name;
    private String url;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = MODEL_FK, referencedColumnName = OPTION_REFERENCED_COLUMN)
    private List<Option> options;

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

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
