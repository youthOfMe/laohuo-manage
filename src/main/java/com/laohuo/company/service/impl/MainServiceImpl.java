package com.laohuo.company.service.impl;

import com.laohuo.company.common.BaseResponse;
import com.laohuo.company.common.ErrorCode;
import com.laohuo.company.common.ViewInfo;
import com.laohuo.company.dao.ReportMapper;
import com.laohuo.company.dao.UserMapper;
import com.laohuo.company.entity.User;
import com.laohuo.company.exception.BusinessException;
import com.laohuo.company.pojo.vo.WaitReplyReport;
import com.laohuo.company.service.MainService;
import com.laohuo.company.strategy.mainKeyStroke.MainKeyStrokeStrategyContext;
import com.laohuo.company.util.*;
import com.laohuo.company.view.MainView;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 主页面服务层
 */
public class MainServiceImpl implements MainService {

    /**
     * 机会
     */
    static Integer count = 3;

    private static MainService mainService;

    private MainServiceImpl() {

    }

    public static MainService getInstance() {
        if (mainService == null) mainService = new MainServiceImpl();
        return mainService;
    }

    final static Scanner scanner = new Scanner(System.in);

    static {
        scanner.useDelimiter("\n");
    }

    /**
     * 查看个人信息
     */
    @Override
    public void personInfo() throws Exception {
        Long currentId = BaseContext.getCurrentId();
        if (currentId == null) {
            System.out.println("未登录!非法用户！");
            return;
        }

        // 调用数据库的方式
        // BaseResponse<User> userBaseResponse = UserMapper.personInfo();
        // if (userBaseResponse.getCode() != 0) {
        //     System.out.println("查询失败!");
        //     return;
        // }
        // User user = userBaseResponse.getData();

        // 采取本地缓存的方式
        LocalCache cacheMap = LocalCache.getInstance();
        User user = (User) cacheMap.getCacheMap().get("userInfo");

        // 区分获取薪资还是获取个人信息
        if (MainKeyStrokeStrategyContext.isPerson) {
            System.out.println("个人信息:" +
                    "\n用户名: " + user.getUsername() +
                    "\n真实姓名: " + user.getNickname() +
                    "\n职位: " + (user.getIsAdmin() == 1 ? "老板" : "职员") +
                    "\n薪水: " + user.getSalary());
        } else {
            System.out.println("薪水: " + user.getSalary());
        }

        KeyBoardEventListener keyBoardEventListener = KeyBoardEventListener.getInstance();
        keyBoardEventListener.ListenKeyBoardEvent(ViewInfo.MainView);
    }

    /**
     * 修改密码
     * @throws Exception
     */
    @Override
    public void updatePassword() throws Exception {
        if (count-- <= 0) {
            System.out.println("您没有机会了");
            System.exit(0);
        }

        Long userId = BaseContext.getCurrentId();
        System.out.println("请输入原密码: ");
        String password = scanner.next();
        if (StringUtils.isBlank(password)) {
            System.out.println("原密码不可为空, 您还有" + count + "次机会");
            this.updatePassword();
            return;
        }

        // 查询原密码正确吗
        // 数据库的方式
        // boolean isPassword = UserMapper.isPassword(userId, password).getData();

        // 本地缓存的方式
        LocalCache cacheMap = LocalCache.getInstance();
        User user = (User) cacheMap.getCacheMap().get("userInfo");
        boolean isPassword = StringUtils.equals(password, user.getPassword());

        if (!isPassword) {
            System.out.println("原密码不正确！您还有" + count + "次机会");
            this.updatePassword();
            return;
        }

        // 查询更改后的密码是否符合规则
        System.out.println("请输入更改后的密码: ");
        String updatePassword = scanner.next();
        if (StringUtils.isBlank(updatePassword)) {
            System.out.println("密码不可为空, 您还有" + count + "次机会");
            this.updatePassword();
            return;
        }
        if (updatePassword.length() < 4 || updatePassword.length() > 18) {
            System.out.println("密码长度不可小于4大于18, 您还有" + count + "次机会");
            this.updatePassword();
            return;
        }
        if (!PassWordUtil.isLetterDigit(updatePassword)) {
            System.out.println("密码必须同时包含字母和数字, 您还有" + count + "次机会");
            this.updatePassword();
            return;
        }
        if (AlgorithmUtils.minDistance(password, updatePassword) <= 2) {
            System.out.println("修改的密码不能和原来的密码相似");
            count++;
            this.updatePassword();
            return;
        }

        // 进行更改密码
        Boolean isSuccess = UserMapper.updatePassword(userId, updatePassword).getData();
        if (!isSuccess) {
            System.out.println(new BusinessException(ErrorCode.SYSTEM_ERROR).getMessage());
            System.exit(0);
        }

        // 修改密码成功之后需要立刻更改缓存
        user.setPassword(updatePassword);
        cacheMap.getCacheMap().put("userInfo", user);
        count = 3;

        MainView.mainView();
    }

    /**
     * 汇报工作
     * @throws Exception
     */
    @Override
    public void presentation() throws Exception {
        System.out.println("请输入汇报标题: ");
        String title = scanner.next();
        if (StringUtils.isBlank(title)) {
            title = "空汇报";
        }

        System.out.println("请输入汇报内容: ");
        String content = scanner.next();
        if (StringUtils.isBlank(content)) {
            content = "空汇报";
        }

        Boolean isSuccess = ReportMapper.addPresentation(title, content).getData();
        if (!isSuccess) {
            System.out.println(new BusinessException(ErrorCode.SYSTEM_ERROR).getMessage());
            return;
        }
        if (isSuccess) {
            System.out.println("汇报成功！");
        }
        MainView.mainView();
    }

    /**
     * 查看汇报
     * @throws Exception
     */
    @Override
    public void lookReport() throws Exception {
        // 1. 查询汇报 汇报编号 - 汇报人 - 汇报标题 - 汇报内容 - 汇报时间
        BaseResponse<ResultSet> reportResultSet = ReportMapper.listReport();

        if (reportResultSet.getCode() != 0) {
            System.out.println(new BusinessException(ErrorCode.SYSTEM_ERROR).getMessage());
        }

        List<WaitReplyReport> reportList = new ArrayList<>();
        ResultSet reportData = reportResultSet.getData();

        int size = 0;
        while (reportData.next()) {
            size++;
            WaitReplyReport report = WaitReplyReport.builder()
                    .id(reportData.getLong("id"))
                    .title(reportData.getString("title"))
                    .content(reportData.getString("content"))
                    .nickname(reportData.getString("nickname"))
                    .createTime(reportData.getDate("createTime"))
                    .build();
            reportList.add(report);
        }
        if (size <= 0) {
            System.out.println("无数据~");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("以下为没有进行回复的汇报: ");
        for (WaitReplyReport waitReplyReport : reportList) {
            System.out.println("汇报编号: " + waitReplyReport.getId());
            System.out.println("汇报人: " + waitReplyReport.getNickname());
            System.out.println("汇报标题: " + waitReplyReport.getTitle());
            System.out.println("汇报内容: " + waitReplyReport.getContent());
            System.out.println("汇报时间: " + sdf.format(waitReplyReport.getCreateTime()));
            System.out.println("=================");
        }

        // 2. 根据汇报编码选择汇报
        System.out.println("请选择需要回复的汇报");
        Long reportId = Long.valueOf(scanner.nextInt());
        // 处理输入错误
        List<WaitReplyReport> isExistsReport = reportList.stream().filter((report) -> {
            return report.getId().equals(reportId);
        }).collect(Collectors.toList());
        if (isExistsReport.size() != 1) {
            System.out.println(new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误, 不存在这个编号的报告").getMessage());
            this.lookReport();
            return;
        }

        // 3. 回复汇报
        System.out.println("请输入回复的内容");
        String content = scanner.next();
        BaseResponse<Boolean> result = ReportMapper.replyReport(content, reportId);
        if (!result.getData()) {
            System.out.println(new BusinessException(ErrorCode.SYSTEM_ERROR, "系统错误, 数据库出现BUG"));
            System.exit(0);
        }
        System.out.println("回复成功~");

        // 4. 是否继续回复其他汇报
        System.out.println("是否继续回复其他汇报？(1是, 2否回到上一层)");
        Integer isGoOn = scanner.nextInt();
        if (isGoOn == 2) {
            MainView.mainView();
            return;
        } else if (isGoOn == 1) {
            this.lookReport();
            return;
        } else {
            System.out.println("非法输入！");
        }

    }
}
