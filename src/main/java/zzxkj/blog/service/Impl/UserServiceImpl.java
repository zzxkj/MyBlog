package zzxkj.blog.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzxkj.blog.dao.UserDao;
import zzxkj.blog.pojo.User;
import zzxkj.blog.service.UserService;
import zzxkj.blog.util.MD5Utils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.queryByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
