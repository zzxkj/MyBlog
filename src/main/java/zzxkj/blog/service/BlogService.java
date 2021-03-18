package zzxkj.blog.service;


import zzxkj.blog.dto.*;
import zzxkj.blog.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {


    /*获取博客数量*/
    Long countBlog();
    /*保存博客*/
    int saveBlog(Blog blog);
    /*更新博客*/
    int updateBlog(Blog blog);
    /*删除博客*/
    int deleteBlog(Long id);
    /*获取前端展示的博客详情，并且views+1*/
    DetailedBlog getBlogAndIncrease(Long id);
    /*获取首页展示列表*/
    List<FirstPageBlog> getAllFirstPageBlog();
    /*获取搜索展示列表*/
    List<FirstPageBlog> getSearchBlog(String query);
    /*根据分类Id获取博客列表，在分类页进行的操作*/
    List<FirstPageBlog> getByTypeId(Long typeId);
    /*根据标签Id获取博客列表*/
    List<FirstPageBlog> getByTagId(Long tagId);
    /*获取后台界面的博客列表展示*/
    List<BlogQuery> getAllBlog();
    /*获取后台界面的博客详情*/
    ShowBlog getBlogById(Long id);
    /*按照日期归档获取博客*/
    Map<String, List<DetailedBlog>> archiveBlog();
    /*获取推荐博客列表*/
    List<RecommendBlog> listRecommendBlogTop(Integer size);

}
