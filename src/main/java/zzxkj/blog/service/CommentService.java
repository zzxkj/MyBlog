package zzxkj.blog.service;


import zzxkj.blog.pojo.Comment;

import java.util.List;

public interface CommentService {
    /*根据BlogId查询*/
    List<Comment> findByBlogId(Long id);
    //获取该博客最终的、有层次的评论
    List<Comment> listFinCommentByBlogId(Long blogId);
    /*保存comment*/
    int saveComment(Comment comment);


}
