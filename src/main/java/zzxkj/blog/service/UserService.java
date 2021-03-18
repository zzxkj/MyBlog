package zzxkj.blog.service;


import zzxkj.blog.pojo.User;

public interface UserService {
    /*根据账号密码获取User，此处password已加密*/
    User checkUser(String username, String password);
}
