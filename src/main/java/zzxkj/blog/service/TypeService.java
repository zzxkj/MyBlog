package zzxkj.blog.service;


import zzxkj.blog.pojo.Type;

import java.util.List;


public interface TypeService {
    /*保存Type*/
    int saveType(Type type);
    /*获取Type*/
    Type getType(Long id);
    /*获取所有Type*/
    List<Type> getAllType();
    /*获取指定数量的Type*/
    List<Type> listTypeTop(Integer size);
    /*根据名称获取Type*/
    Type getTypeByName(String name);
    /*更新Type*/
    int updateType(Type type);
    /*删除Type*/
    int deleteType(Long id);


}
