package com.laohuo.company.dao;

import com.laohuo.company.common.BaseResponse;
import com.laohuo.company.common.ResultUtils;
import com.laohuo.company.entity.User;
import com.laohuo.company.jdbc.JdbcUtil;
import com.laohuo.company.util.BaseContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 用户相关数据库操作代码
 */
public class UserMapper {

    private static Connection connection = JdbcUtil.getConnection();

    /**
     * 用户登录
     * @param username 账号
     * @param password 密码
     * @return
     * @throws Exception
     */
    public static BaseResponse<ResultSet> login(String username, String password) throws Exception {
        String sql = "SELECT * FROM user where username = ? and password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet userData = preparedStatement.executeQuery();
        return ResultUtils.success(userData);
    }

    // public static BaseResponse<ResultSet> register

    /**
     * 获取个人信息
     * @return
     * @throws Exception
     */
    public static BaseResponse<User> personInfo() throws Exception {
        Long userId = BaseContext.getCurrentId();
        String sql = "SELECT * FROM user where id = ? and isDelete = 0";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        ResultSet userInfo = preparedStatement.executeQuery();
        User user = new User();
        while (userInfo.next()) {
            user.setUsername(userInfo.getString("username"));
            user.setNickname(userInfo.getString("nickname"));
            user.setCreateTime(userInfo.getDate("createTime"));
            user.setUpdateTime(userInfo.getDate("updateTime"));
            user.setIsAdmin(userInfo.getInt("isAdmin"));
            user.setSalary(userInfo.getDouble("salary"));
        }
        return ResultUtils.success(user);

    }
}
