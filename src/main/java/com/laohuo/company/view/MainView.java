package com.laohuo.company.view;

import com.laohuo.company.common.ViewInfo;
import com.laohuo.company.entity.User;
import com.laohuo.company.util.KeyBoardEventListener;
import com.laohuo.company.util.LocalCache;

/**
 * 主页面
 */
public class MainView {

    public static void mainView() throws Exception {

        // 根据本地缓存中职位的不同进行刷新不同的页面数据
        LocalCache cacheMap = LocalCache.getInstance();
        User user = (User) cacheMap.getCacheMap().get("userInfo");
        System.out.println("我们的功能: " +
                "\n1.查看个人信息" +
                "\n2.修改密码");
        if (user.getIsAdmin() == 1) {
            System.out.println("3.查看汇报");
        } else {
            System.out.println("3.汇报工作");
        }

        System.out.println("4.查看薪资" +
                "\n5.返回上一层");
        KeyBoardEventListener keyBoardEventListener = KeyBoardEventListener.getInstance();
        keyBoardEventListener.ListenKeyBoardEvent(ViewInfo.MainView);
    }
}
