package com.example.geossms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.geossms.entity.Comment;
import com.example.geossms.mapper.CommentMapper;
import com.example.geossms.service.ICommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author 吴斌文
 * @since 2023-05-06
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findCommentDetail(Integer articleId) {

        return commentMapper.findCommentDetail(articleId);
    }
}
