package com.laohuo.company.dao;

import com.laohuo.company.common.BaseResponse;
import com.laohuo.company.common.ErrorCode;
import com.laohuo.company.common.ResultUtils;
import com.laohuo.company.entity.User;
import com.laohuo.company.exception.BusinessException;
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

    /**
     * 查询用户是否存在
     * @param username 用户名
     * @return
     * @throws Exception
     */
    public static BaseResponse<Boolean> existUser(String username) throws Exception {
        String sql = "SELECT * FROM user where username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet userData = preparedStatement.executeQuery();
        return ResultUtils.success(userData != null);
    }

    /**
     * 用户注册
     * @param name 用户名
     * @param pwd 用户密码
     * @param nk 用户名字
     * @return
     * @throws Exception
     */
    public static BaseResponse<ResultSet> register(String name, String pwd, String nk, int sy) throws Exception {
        String sql = "INSERT INTO user(username,password,nickname,salary) VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, pwd);
        preparedStatement.setString(3, nk);
        preparedStatement.setDouble(4, sy + 0.0);
        boolean isSuccess = preparedStatement.executeUpdate() == 1;

        // 处理系统错误
        if (!isSuccess) {
            System.out.println(new BusinessException(ErrorCode.SYSTEM_ERROR).getMessage());
            return null;
        }

        // 注册成功后直接登录
        BaseResponse<ResultSet> userData = login(name, pwd);
        return userData;
    }

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

    /**
     * 根据用户ID和密码查询用户信息
     * @param userId 用户ID
     * @param password 用户密码
     * @return
     * @throws Exception
     */
    public static BaseResponse<Boolean> isPassword(Long userId, String password) throws Exception {
        String sql = "SELECT * FROM user where id = ? and password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        preparedStatement.setString(2, password);
        ResultSet userData = preparedStatement.executeQuery();
        return ResultUtils.success(userData.next());
    }

    /**
     * 根据用户id进行修改密码
     * @param userId 用户ID
     * @param password 密码
     * @return
     * @throws Exception
     */
    public static BaseResponse<Boolean> updatePassword(Long userId, String password) throws Exception {
        String sql = "UPDATE user SET password = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, password);
        preparedStatement.setLong(2, userId);
        int count = preparedStatement.executeUpdate();
        return ResultUtils.success(count == 1);
    }
}
