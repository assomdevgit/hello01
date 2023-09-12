package com.springboot.hello01.dto;

import com.springboot.hello01.entity.Article;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {
    private Long id;
    private String title;   // 제목
    private String content;    // 내용
    
    // public ArticleForm(String title, String content){
    //     this.title = title;
    //     this.content = content;
    // }

    public Article toEntity() {
        return new Article(id, title, content);
    }

}
