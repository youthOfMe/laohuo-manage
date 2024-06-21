package com.laohuo.company.service.impl;

import com.laohuo.company.common.BaseResponse;
import com.laohuo.company.common.ErrorCode;
import com.laohuo.company.dao.UserMapper;
import com.laohuo.company.entity.User;
import com.laohuo.company.exception.BusinessException;
import com.laohuo.company.service.HomeService;
import com.laohuo.company.util.BaseContext;
import com.laohuo.company.util.LocalCache;
import com.laohuo.company.util.PassWordUtil;
import com.laohuo.company.view.MainView;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.util.Random;
import java.util.Scanner;

/**
 * 作者：霍盛展
 * 描述：牛逼
 */
public class HomeServiceImpl implements HomeService {

    /**
     * 机会
     */
    static Integer count = 3;

    private static HomeService homeService;

    private HomeServiceImpl() {

    }

    public static HomeService getInstance() {
        if (homeService == null) homeService = new HomeServiceImpl();
        return homeService;
    }

    final Scanner scanner = new Scanner(System.in);

    @Override
    public void login() throws Exception {
        if (count-- <= 0) {
            System.out.println("您没有机会了");
            System.exit(0);
        }

        System.out.println("请输入用户名: ");
        String username = scanner.next();
        if (StringUtils.isBlank(username)) {
            System.out.println("用户名不可为空, 您还有" + count + "次机会");
            this.login();
            return;
        }
        if (username.length() < 4 || username.length() > 18) {
            System.out.println("用户名长度不可小于4大于18, 您还有" + count + "次机会");
            this.login();
            return;
        }
        System.out.println("请输入密码: ");
        String password = scanner.next();
        if (StringUtils.isBlank(password)) {
            System.out.println("密码不可为空, 您还有" + count + "次机会");
            this.login();
            return;
        }
        if (password.length() < 4 || password.length() > 18) {
            System.out.println("密码长度不可小于4大于18, 您还有" + count + "次机会");
            this.login();
            return;
        }
        if (!PassWordUtil.isLetterDigit(password)) {
            System.out.println("密码必须同时包含字母和数字, 您还有" + count + "次机会");
            this.login();
            return;
        }

        BaseResponse<ResultSet> queryResult = UserMapper.login(username, password);

        if (queryResult.getData().next()) {
            // 将登录用户数据读入本地缓存
            User user = new User();
            user.setUsername(queryResult.getData().getString("username"));
            user.setPassword(queryResult.getData().getString("password"));
            user.setNickname(queryResult.getData().getString("nickname"));
            user.setIsAdmin(queryResult.getData().getInt("isAdmin"));
            user.setSalary(queryResult.getData().getDouble("salary"));
            BaseContext.setCurrentId(queryResult.getData().getLong("id"));
            LocalCache cacheMap = LocalCache.getInstance();
            cacheMap.getCacheMap().put("userInfo", user);
            System.out.println("登录成功~");
            MainView.mainView();
        } else {
            System.out.println("登录失败, 您还有" + count + "次机会");
            this.login();
        }
    }

    @Override
    public void register() throws Exception {
        if (count-- <= 0) {
            System.out.println("您没有机会了");
            System.exit(0);
        }

        System.out.println("请输入用户名: ");
        String username = scanner.next();
        if (StringUtils.isBlank(username)) {
            System.out.println("用户名不可为空, 您还有" + count + "次机会");
            this.register();
            return;
        }
        if (username.length() < 4 || username.length() > 18) {
            System.out.println("用户名长度不可小于4大于18, 您还有" + count + "次机会");
            this.register();
            return;
        }

        // 查询用户名是否被使用
        boolean isExistUser = UserMapper.existUser(username).getData();
        if (isExistUser) {
            System.out.println("对不起! 您注册的用户名已经存在！");
            this.register();
            return;
        }

        System.out.println("请输入密码: ");
        String password = scanner.next();
        if (StringUtils.isBlank(password)) {
            System.out.println("密码不可为空, 您还有" + count + "次机会");
            this.register();
            return;
        }
        if (password.length() < 4 || password.length() > 18) {
            System.out.println("密码长度不可小于4大于18, 您还有" + count + "次机会");
            this.register();
            return;
        }
        if (!PassWordUtil.isLetterDigit(password)) {
            System.out.println("密码必须同时包含字母和数字, 您还有" + count + "次机会");
            this.register();
            return;
        }

        System.out.println("请再次输入密码: ");
        String checkPassword = scanner.next();
        if (!StringUtils.equals(password, checkPassword)) {
            System.out.println("两次填写的密码必须相同！您还有" + count + "次机会");
            this.register();
            return;
        }

        System.out.println("请输入您的真实姓名: ");
        String nickname = scanner.next();
        if (StringUtils.isBlank(nickname)) {
            System.out.println("用户名不可为空, 您还有" + count + "次机会");
            this.register();
            return;
        }

        // 随机生成薪资 1000 - 10000
        Random random = new Random();
        int salary = random.nextInt(9000) + 1000;

        BaseResponse<ResultSet> queryResult = UserMapper.register(username, password, nickname, salary);

        // 处理系统内部异常
        if (queryResult == null) {
            System.out.println(new BusinessException(ErrorCode.SYSTEM_ERROR).getCode());
        }

        if (queryResult.getData().next()) {
            BaseContext.setCurrentId(queryResult.getData().getLong("id"));
            System.out.println("注册成功~");
            MainView.mainView();
        } else {
            System.out.println("注册失败, 您还有" + count + "次机会");
            this.login();
        }
    }


}
