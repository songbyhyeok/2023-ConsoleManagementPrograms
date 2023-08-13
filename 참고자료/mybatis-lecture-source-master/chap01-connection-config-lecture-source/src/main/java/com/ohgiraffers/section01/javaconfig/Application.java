package com.ohgiraffers.section01.javaconfig;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.session.Configuration;

import java.util.Date;

public class Application {

    private static String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static String USER = "C##OHGIRAFFERS";
    private static String PASSWORD = "OHGIRAFFERS";

    public static void main(String[] args) {

        /* DB 접속에 관한 환경 설정
        * JdbcTransactionFactory : 수동 커밋
        * ManagedTransactionFactory : 자동 커밋
        * PooledDataSource : ConnectionPool 사용
        * UnPooledDataSource : ConnectionPool 미사용
        * */
        Environment environment =
                new Environment("dev"                   //환경 정보 이름
                    , new JdbcTransactionFactory()      //트랜잭션 매니저 종류 결정(JDBC or MANAGED)
                    , new PooledDataSource(DRIVER, URL, USER, PASSWORD));//Connection Pool 사용 유무(Pooled or UnPooled)

        /* 생성한 환경 정보로 MyBatis 설정 객체 생성 */
        Configuration configuration = new Configuration(environment);

        /* 설정 객체에 매퍼 등록 */
        configuration.addMapper(Mapper.class);

        /* SqlSessionFactory : SqlSession 객체를 생성하기 위한 팩토리 역할의 인터페이스
        *  SqlSessionFactoryBuilder : SqlSessionFactory 인터페이스 타입의 하위 구현 객체를 생성하기 위한 빌드 역할
        *  build() : 설정에 대한 정보를 담고 있는 Configuration 타입의 객체 혹은 외부 설정파일과 연결 된 Stream을
        *  매개 변수로 전달하면 SqlSessionFactory 인터페이스 타입의 객체를 반환하는 메소드
        * */
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        /* openSession() : SqlSession 인터페이스 타입의 객체를 반환하는 메소드로 boolean 타입을 인자로 전달
        * - false : DML 수행 후 auto commit 옵션을 false로 지정(권장)
        * - true : DML 수행 후 auto commit 옵션을 true로 지정
        * */
        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        /* getMapper() : Configuration에 등록 된 매퍼를 동일 타입에 대해 반환하는 메소드 */
        Mapper mapper = sqlSession.getMapper(Mapper.class);

        /* Mapper 인터페이스에 작성 된 메소드를 호출하여 쿼리 실행 */
        Date date = mapper.selectSysdate();

        System.out.println(date);

        /* close() : SqlSession 객체 반납 */
        sqlSession.close();
    }

}
