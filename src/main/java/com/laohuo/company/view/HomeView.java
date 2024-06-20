package com.laohuo.company.view;

import com.laohuo.company.common.ViewInfo;
import com.laohuo.company.util.KeyBoardEventListener;

/**
 * 作者：霍盛展
 * 描述：牛逼
 */
public class HomeView {
    public static void homeView() throws Exception {
        System.out.println("欢迎光临霍盛展科技有限公司" +
                "\n 1.登录" +
                "\n 2.注册" +
                "\n 3.退出");
        KeyBoardEventListener keyBoardEventListener = KeyBoardEventListener.getInstance();
        keyBoardEventListener.ListenKeyBoardEvent(ViewInfo.HomeView);

    }
}
