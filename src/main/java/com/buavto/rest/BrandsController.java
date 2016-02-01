package com.buavto.rest;

import com.buavto.model.Brand;
import com.buavto.services.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BrandsController {

    @Autowired
    private BrandsService brandsService;

    @RequestMapping(value = "/brands", method = RequestMethod.GET, produces="application/json")
    public Iterable<Brand> findAllBrands() {
        return brandsService.findAll();
    }
}
