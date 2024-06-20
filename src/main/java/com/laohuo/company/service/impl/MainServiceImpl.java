package com.laohuo.company.service.impl;

import com.laohuo.company.common.BaseResponse;
import com.laohuo.company.common.ViewInfo;
import com.laohuo.company.dao.UserMapper;
import com.laohuo.company.entity.User;
import com.laohuo.company.service.MainService;
import com.laohuo.company.util.BaseContext;
import com.laohuo.company.util.KeyBoardEventListener;

/**
 * 主页面服务层
 */
public class MainServiceImpl implements MainService {

    private static MainService mainService;

    private MainServiceImpl() {

    }

    public static MainService getInstance() {
        if (mainService == null) mainService = new MainServiceImpl();
        return mainService;
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
        BaseResponse<User> userBaseResponse = UserMapper.personInfo();
        if (userBaseResponse.getCode() != 0) {
            System.out.println("查询失败!");
            return;
        }
        User user = userBaseResponse.getData();
        System.out.println("个人信息:" +
                "\n用户名: " + user.getUsername() +
                "\n真实姓名: " + user.getNickname() +
                "\n职位: " + (user.getIsAdmin() == 1 ? "老板" : "职员") +
                "\n薪水: " + user.getSalary());
        KeyBoardEventListener keyBoardEventListener = KeyBoardEventListener.getInstance();
        keyBoardEventListener.ListenKeyBoardEvent(ViewInfo.MainView);
    }

}
