package zzxkj.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import zzxkj.blog.pojo.Tag;

import java.util.List;

@Mapper
@Repository
public interface TagDao {

    int saveTag(Tag tag);

    int deleteTag(Long id);

    int updateTag(Tag tag);

    int saveBlogTags(@Param("blogsId") Long blogsId, @Param("tagsId") Long tagsId);

    Tag getById(Long id);

    Tag getByName(String name);

    List<Tag> getAllTag();

    List<Tag> getAdminTag();

    List<Tag> listTagTop(Integer size);
    /*获取该博客的所有标签*/
    List<Tag> getTagsByBlogId(Long BlogId);
}
