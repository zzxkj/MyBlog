package zzxkj.blog.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zzxkj.blog.dto.*;
import zzxkj.blog.pojo.Blog;

import java.util.List;

@Repository
@Mapper
public interface BlogDao {

    ShowBlog getBlogById(Long id);

    List<BlogQuery> getAllBlogQuery();

    int saveBlog(Blog blog);

    int deleteBlog(Long id);

    int updateBlog(Blog blog);

    int saveBlogAndTag(BlogAndTag blogAndTag);

    int deleteBlogAndTag(Long blogId);

    List<BlogQuery> searchByTitleOrTypeOrRecommend(SearchBlog searchBlog);

    List<FirstPageBlog> getFirstPageBlog();

    List<RecommendBlog> getAllRecommendBlog();

    List<RecommendBlog> listRecommendBlogTop(Integer size);

    List<FirstPageBlog> getSearchBlog(String query);

    DetailedBlog getDetailedBlog(Long id);

    List<FirstPageBlog> getByTypeId(Long typeId);

    List<FirstPageBlog> getByTagId(Long tagId);

    List<String > findGroupYear();

    List<DetailedBlog> findByYear(String year);
    Long countBlog();
    Integer IncreaseView(Long id);
}
