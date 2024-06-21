package com.laohuo.company.dao;

import com.laohuo.company.common.BaseResponse;
import com.laohuo.company.common.ResultUtils;
import com.laohuo.company.jdbc.JdbcUtil;
import com.laohuo.company.util.BaseContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 报告相关数据库操作代码
 */
public class ReportMapper {

    private static Connection connection = JdbcUtil.getConnection();

    /**
     * 添加汇报
     * @param title 汇报标题
     * @param content 汇报内容
     * @return
     * @throws Exception
     */
    public static BaseResponse<Boolean> addPresentation(String title, String content) throws Exception {
        // 配合单元测试
        // BaseContext.setCurrentId(Long.valueOf(1));
        Long userId = BaseContext.getCurrentId();
        String sql = "INSERT report(title, content, userId) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, content);
        preparedStatement.setLong(3, userId);
        boolean isSuccess = !preparedStatement.execute();
        return ResultUtils.success(isSuccess);
    }

    /**
     * 查看汇报列表
     * @return
     * @throws Exception
     */
    public static BaseResponse<ResultSet> listReport() throws Exception {
        String sql = "SELECT report.id as id, user.nickname as nickname, title, content, " +
                "report.createTime as createTime FROM report " +
                "LEFT JOIN user ON user.id = report.userId WHERE report.status = 0 and report.replyId = 0";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        return ResultUtils.success(resultSet);
    }

    /**
     * 回复汇报
     * @param content 内容
     * @param replyId 回复ID
     * @return
     * @throws Exception
     */
    public static BaseResponse<Boolean> replyReport(String content, Long replyId) throws Exception {
        Long userId = BaseContext.getCurrentId();

        // 开事务
        connection.setAutoCommit(false);

        String sql = "INSERT report(title, content, userId, status, replyId) VALUES(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "经理回复帖");
        preparedStatement.setString(2, content);
        preparedStatement.setLong(3, userId);
        preparedStatement.setInt(4, 1);
        preparedStatement.setLong(5, replyId);
        boolean isSuccess = !preparedStatement.execute();

        String sql2 = "UPDATE report SET status = 1 where id = ?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
        preparedStatement2.setLong(1, replyId);
        boolean isSuccess2 = !preparedStatement2.execute();

        // 回滚事务
        if (!isSuccess2 || !isSuccess) {
            connection.rollback();
        } else {
            // 提交事务
            connection.commit();
        }

        // 关闭事务
        connection.setAutoCommit(true);

        return ResultUtils.success(isSuccess);
    }
}
