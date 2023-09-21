package com.example.geossms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.geossms.entity.Article;
import com.example.geossms.mapper.ArticleMapper;
import com.example.geossms.service.IArticleService;
import org.springframework.stereotype.Service;

/**
 *
 * @author 吴斌文
 * @since 2023-05-06
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
