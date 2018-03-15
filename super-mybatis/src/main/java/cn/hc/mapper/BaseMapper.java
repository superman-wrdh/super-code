package cn.hc.mapper;

import cn.hc.pojo.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * base mapper
 */
public interface BaseMapper<T, K> {
    int insert(T entity);
    int insertBy(T entity);
    int update(T entity);
    int updateBy(T template);
    int delete(@Param("id") K id);
    int deleteBy(@Param("criteria") T template);
    boolean exists(K id);
    boolean existsBy(T template);
    T load(@Param("id") K id);
    T loadBy(@Param("criteria") T template);
    List<T> selectAll(@Param("pageInfo") PageInfo pageInfo);
    List<T> selectBy(@Param("criteria") T criteria, @Param("pageInfo") PageInfo pageInfo);
}