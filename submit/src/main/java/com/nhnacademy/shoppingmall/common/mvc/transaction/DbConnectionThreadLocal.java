package com.nhnacademy.shoppingmall.common.mvc.transaction;

import com.nhnacademy.shoppingmall.common.util.DbUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

@Slf4j
public class DbConnectionThreadLocal {
    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> sqlErrorThreadLocal = ThreadLocal.withInitial(()->false);

    public static void initialize(){

        try {
            // connection pool에서 connectionThreadLocal에 connection을 할당합니다.
            DataSource dataSource = DbUtils.getDataSource();
            Connection connection = dataSource.getConnection();
            connectionThreadLocal.set(connection);

            // connection의 Isolation level을 READ_COMMITED를 설정 합니다.
            connection.setTransactionIsolation(TRANSACTION_READ_COMMITTED);

            // auto commit 을 false로 설정합니다.
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        return connectionThreadLocal.get();
    }

    public static void setSqlError(boolean sqlError){
        sqlErrorThreadLocal.set(sqlError);
    }

    public static boolean getSqlError(){
        return sqlErrorThreadLocal.get();
    }

    public static void reset(){

        try {
            // 사용이 완료된 connection은 close를 호출하여 connection pool에 반환합니다.
            if(getSqlError()){
                // getSqlError() 에러가 존재하면 rollback 합니다.
                getConnection().rollback();
            } else {
                // getSqlError() 에러가 존재하지 않다면 commit 합니다.
                getConnection().commit();
            }
            getConnection().close();

            // 현재 사용하고 있는 connection을 재 사용할 수 없도록 connectionThreadLocal을 초기화 합니다.
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        } finally {
            sqlErrorThreadLocal.remove();
            connectionThreadLocal.remove();
        }
    }
}
