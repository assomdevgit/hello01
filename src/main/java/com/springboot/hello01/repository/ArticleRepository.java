package com.springboot.hello01.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.springboot.hello01.entity.Article;

public interface ArticleRepository extends CrudRepository<Article, Long>{
    @Override
    ArrayList<Article> findAll();
}
