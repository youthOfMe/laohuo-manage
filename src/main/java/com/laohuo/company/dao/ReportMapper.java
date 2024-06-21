package com.laohuo.company.dao;

import com.laohuo.company.common.BaseResponse;
import com.laohuo.company.common.ResultUtils;
import com.laohuo.company.jdbc.JdbcUtil;
import com.laohuo.company.util.BaseContext;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
}
