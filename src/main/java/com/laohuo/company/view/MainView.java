package com.laohuo.company.view;

import com.laohuo.company.common.ViewInfo;
import com.laohuo.company.util.KeyBoardEventListener;

/**
 * 主页面
 */
public class MainView {
    public static void mainView() throws Exception {
        System.out.println("我们的功能: " +
                "\n1.查看个人信息" +
                "\n2.修改密码");
        System.out.println("3.查看汇报");
        System.out.println("4.查看薪资" +
                "\n5.返回上一层");
        KeyBoardEventListener keyBoardEventListener = KeyBoardEventListener.getInstance();
        keyBoardEventListener.ListenKeyBoardEvent(ViewInfo.MainView);
    }
}
