package com.laohuo.company.interceptor;

import com.laohuo.company.jdbc.JdbcUtil;

import java.sql.SQLException;

/**
 * 主程序结束后的拦截处理器
 */
public class MainThreadInterceptor {

    /**
     * 关闭数据库连接
     */
    public static void shutConnection() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("关闭数据库连接啦");
            try {
                JdbcUtil.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
    }
}
