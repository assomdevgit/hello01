package com.springboot.hello01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.hello01.dto.ArticleForm;
import com.springboot.hello01.entity.Article;
import com.springboot.hello01.repository.ArticleRepository;
import com.springboot.hello01.service.ArticleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }
    
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        System.out.println(form.toString());

        Article article = form.toEntity();
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id : " + id);
        // id를 이용해서 데이터 조회
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // model
        model.addAttribute("articles", articleEntity);
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        // 1. 모든 데이터 가져오기
        List<Article> articleList = (List<Article>) articleRepository.findAll();
        // Page<Article> articleList = (Page<Article>) articleRepository.findAll();
        // 2. 모델 데이터 등록
        model.addAttribute("articleList", articleList);
        // 3. 뷰 전송
        return "articles/index";
    }

    @GetMapping("/articles/api")
    public ResponseEntity<List<Article>> index2(){
        log.info("출력 : " + articleService.index());
        return ResponseEntity.status(HttpStatus.OK).body(articleService.index());
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("articles", articleEntity);
        log.info("id: " + id);
        return "articles/edit";
    }

    @PutMapping("/articles/update")
    public String update(ArticleForm form){
        Article article = form.toEntity();
        Article target = articleRepository.findById(article.getId()).orElse(null);
        if(target != null){
            articleRepository.save(article);
        }

        log.info("id : " + article.getId());
        return "redirect:/articles/" + article.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        Article target = articleRepository.findById(id).orElse(null);
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제완료");
        }
        return "redirect:/articles";
    }


}

