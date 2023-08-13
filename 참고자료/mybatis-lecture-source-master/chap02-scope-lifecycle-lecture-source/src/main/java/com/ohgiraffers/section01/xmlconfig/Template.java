package com.ohgiraffers.section01.xmlconfig;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Template {
    /* SqlSessionFactory는 애플리케이션이 실행 되는 동안 존재해야 하며,
    * 여러 차례 다시 빌드하지 않는 것이 가장 좋은 형태이다.
    * 애플리케이션 스코프를 관리하기 위해 가장 간단한 방법은 싱글톤 패턴을 이용하는 것이다.
    * */
    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSession getSqlSession() {

        if(sqlSessionFactory == null) {
            String resource = "mybatis-config.xml";

            try {
                InputStream inputStream = Resources.getResourceAsStream(resource);
                /* SqlSessionFactoryBuilder를 SqlSession을 생성한 후에도 유지할 필요는 없다.
                * 따라서 메소드 스코프로 만든다. */
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /* SqlSession은 스레드에 안전하지 않고 공유 되지 않아야 하므로 요청 시마다 생성해야 한다. */
        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        System.out.println("sqlSessionFactory의 hashCode() : " + sqlSessionFactory.hashCode());
        System.out.println("sqlSession의 hashCode() : " + sqlSession.hashCode());

        return sqlSession;
    }
}












