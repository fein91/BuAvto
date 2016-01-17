package com.buavto.dao;

import com.buavto.model.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BrandsDao extends CrudRepository<Brand, Long> {
}
