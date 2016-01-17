package com.buavto.dao;

import com.buavto.model.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticlesDao extends CrudRepository<Article, Long> {
}
