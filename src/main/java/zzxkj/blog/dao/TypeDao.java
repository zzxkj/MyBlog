package zzxkj.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zzxkj.blog.pojo.Type;

import java.util.List;

@Mapper
@Repository
public interface TypeDao {

    int saveType(Type type);

    Type getTypeById(Long id);

    Type getTypeByName(String name);

    List<Type> getAllType();

    List<Type> getAdminType();

    List<Type> listTypeTop(Integer size);

    int deleteType(Long id);

    int updateType(Type type);


}
