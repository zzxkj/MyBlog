package zzxkj.blog.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzxkj.blog.dao.BlogDao;
import zzxkj.blog.dao.CommentDao;
import zzxkj.blog.pojo.Comment;
import zzxkj.blog.service.CommentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BlogDao blogDao;

    @Override
    public List<Comment> findByBlogId(Long blogId) {
        return commentDao.findByBlogId(blogId);
    }

    @Override
    public List<Comment> listFinCommentByBlogId(Long blogId) {
        //获取该博客的顶级评论
        List<Comment> comments = commentDao.listTopCommentByBlogId(blogId);
        //进行遍历合并操作
        return eachComment(comments);
    }

    @Override
    //接收回复的表单
    public int saveComment(Comment comment) {
        Long parentId = comment.getParentComment().getId();
        Long blogId = comment.getBlog().getId();
        comment.setBlogId(blogId);
        comment.setCreateTime(new Date());
        if (parentId!=-1){
            comment.setParentCommentId(parentId);
            comment.setParentComment(commentDao.findByCommentId(parentId));
        }else comment.setParentCommentId(null);
        comment.setCreateTime(new Date());
        //comment.setBlog(blogDao.getDetailedBlog(comment.getBlogId()));
        return commentDao.saveComment(comment);
    }


    /**
     *      * 循环每个顶级的评论节点
     *      * copy一个新的集合，防止对原数据直接修改
     *      * list是传递引用，一处修改处处修改
     *      * @param comments
     *      * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = commentDao.listChildCommentByBlogId(comment.getId());
            for(Comment reply1 : replys1) {
                reply1.setParentNickname(comment.getNickname());
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (commentDao.listChildCommentByBlogId(comment.getId()).size()>0) {
            List<Comment> replys = commentDao.listChildCommentByBlogId(comment.getId());
            for (Comment reply : replys) {
                reply.setParentNickname(comment.getNickname());
                tempReplys.add(reply);
                if (commentDao.listChildCommentByBlogId(reply.getId()).size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
