package com.buavto.services;

import com.buavto.model.Article;
import org.jsoup.nodes.Document;

import java.util.List;

public interface ArticlesParsingStrategy {

    List<Article> parseDom(Document dom);

    boolean hasNextPage(Document dom);
}
