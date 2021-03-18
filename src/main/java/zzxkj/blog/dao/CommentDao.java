package zzxkj.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import zzxkj.blog.pojo.Comment;

import java.util.List;

@Repository
@Mapper
public interface CommentDao {

    //根据blogId查询,
    List<Comment> findByBlogId(Long blogId);

    //根据CommentId查询,
    Comment findByCommentId(Long id);

    //根据创建时间倒序来排
    List<Comment> findByBlogIdParentIdNull(@Param("blogId") Long blogId, @Param("blogParentId") Long blogParentId);

    //获取博客的顶级评论
    List<Comment> listTopCommentByBlogId(Long BlogId);

    //获取该顶级评论的子评论
    List<Comment> listChildCommentByBlogId(Long parentId);

    //添加一个评论
    int saveComment(Comment comment);
}
