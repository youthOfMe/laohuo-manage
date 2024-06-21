package com.laohuo.company.service.impl;

import com.laohuo.company.common.ErrorCode;
import com.laohuo.company.common.ViewInfo;
import com.laohuo.company.dao.UserMapper;
import com.laohuo.company.entity.User;
import com.laohuo.company.exception.BusinessException;
import com.laohuo.company.service.MainService;
import com.laohuo.company.strategy.mainKeyStroke.MainKeyStrokeStrategyContext;
import com.laohuo.company.util.*;
import com.laohuo.company.view.MainView;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

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

    final Scanner scanner = new Scanner(System.in);

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
}
