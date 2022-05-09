package com.ctgu.oa.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

/**
 * @author Boliang Weng
 */
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object executeQuery(Function<SqlSession,Object> function){
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return function.apply(sqlSession);
        }
    }

    public static Object executeUpdate(Function<SqlSession,Object> function){
        try (SqlSession sqlSession = sqlSessionFactory.openSession(false)) {
            try {
                Object object = function.apply(sqlSession);
                sqlSession.commit();
                return object;
            } catch (RuntimeException e) {
                sqlSession.rollback();
                throw e;
            }
        }
    }


}
