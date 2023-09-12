package com.springboot.hello01.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.hello01.dto.ArticleForm;
import com.springboot.hello01.entity.Article;
import com.springboot.hello01.repository.ArticleRepository;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if (article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null || id != target.getId()){
            return null;
        }
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if(target == null) {
            return null;
        }
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        List<Article> articleList = dtos.stream()
                                        .map(dto -> dto.toEntity())
                                        .collect(Collectors.toList());

        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        // 에러 발생시켜서 rollback 테스트
        // articleRepository.findById(-1L)
        //         .orElseThrow(() -> new IllegalArgumentException("실패"));
    
        return articleList;
    }
    

}
