package com.example.geossms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.geossms.entity.Comment;

import java.util.List;

/**
 *
 * @author 吴斌文
 * @since 2023-05-06
 */
public interface ICommentService extends IService<Comment> {

    List<Comment> findCommentDetail(Integer articleId);
}
