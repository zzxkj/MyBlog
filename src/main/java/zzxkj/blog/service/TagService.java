package zzxkj.blog.service;


import org.apache.ibatis.annotations.Param;
import zzxkj.blog.pojo.Tag;

import java.util.List;

public interface TagService {
    /*保存标签*/
    int saveTag(Tag tag);
    /*删除标签*/
    int deleteTag(Long id);
    /*更新标签*/
    int updateTag(Tag tag);
    /*获取ID标签*/
    Tag getById(Long id);
    /*根据名称获取标签*/
    Tag getByName(String name);
    /*获取所有标签*/
    List<Tag> getAllTag();
    /*通过分割以'1，2，3'字符串类型保存的TagId字符串获取Tag集合*/
    List<Tag> getTagByString(Long blogId, String text);
    /*获取指定数量的Tag*/
    List<Tag> listTagTop(Integer size);
    /*获取该博客的所有标签*/
    List<Tag> getTagsByBlogId(Long blogId);
}
