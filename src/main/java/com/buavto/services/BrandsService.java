package com.buavto.services;

import com.buavto.dao.BrandsDao;
import com.buavto.dao.ModelsDao;
import com.buavto.model.Brand;
import com.buavto.model.Model;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class BrandsService {
    private final static Logger LOGGER = LogManager.getLogger(BrandsService.class.getName());

    @Autowired
    private BrandsDao brandsDao;
    @Autowired
    private ModelsDao modelsDao;

    public Model addNewModel(Brand brand, String modelName) {
        Model model = new Model();
        model.setName(modelName);
        model.setOptions(Collections.EMPTY_LIST);
        brand.getModels().add(model);
        model = modelsDao.save(model);
        brandsDao.save(brand);
        LOGGER.info("New model was saved to db: " + model);
        return model;
    }

    public Model findModel(Brand brand, String rawModelName) {
        for (Model model : brand.getModels()) {
            if (rawModelName.contains(model.getName())) {
                return model;
            }
        }
        return null;
    }

    public Iterable<Brand> findAll() {
        return brandsDao.findAll();
    }
}
