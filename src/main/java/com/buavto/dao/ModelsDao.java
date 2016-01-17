package com.buavto.dao;

import com.buavto.model.Model;
import org.springframework.data.repository.CrudRepository;

public interface ModelsDao extends CrudRepository<Model, Long> {
}
