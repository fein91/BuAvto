package com.buavto.dao;

import com.buavto.model.Option;
import org.springframework.data.repository.CrudRepository;

public interface OptionsDao extends CrudRepository<Option, Long> {
}
