package zzxkj.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zzxkj.blog.pojo.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirstPageBlog {
    //Blog
    private Long id;
    private String title;
    private String firstPicture;
    private Integer views;
    private Date updateTime;
    private String description;
    private User user;
    //Type
    private String typeName;
    //User
    private String nickname;
    private String avatar;
}
