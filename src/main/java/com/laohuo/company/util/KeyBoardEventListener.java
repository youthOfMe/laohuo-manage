package com.laohuo.company.util;

import com.laohuo.company.common.KeyBoardEvent;
import com.laohuo.company.common.ViewInfo;
import com.laohuo.company.strategy.homeKeyStroke.HomeKeyStrokeStrategy;
import com.laohuo.company.strategy.homeKeyStroke.HomeKeyStrokeStrategyContext;
import com.laohuo.company.strategy.homeKeyStroke.LoginStrategy;
import com.laohuo.company.strategy.homeKeyStroke.RegisterStrategy;
import com.laohuo.company.strategy.mainKeyStroke.MainKeyStokeStategy;
import com.laohuo.company.strategy.mainKeyStroke.MainKeyStrokeStrategyContext;
import com.laohuo.company.strategy.mainKeyStroke.PersonInfoStrategy;
import com.laohuo.company.strategy.mainKeyStroke.UpdatePasswordStrategy;

import java.util.Scanner;

/**
 * 单例设计模式
 * 按键监听者
 */
public class KeyBoardEventListener {

    private static KeyBoardEventListener keyBoardEventListener;

    private KeyBoardEventListener() {

    }

    public static KeyBoardEventListener getInstance() {
        if (keyBoardEventListener == null) keyBoardEventListener = new KeyBoardEventListener();
        return keyBoardEventListener;
    }

    /**
     * 监听键盘事件
     */
    public void ListenKeyBoardEvent(ViewInfo viewInfo) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int keyBoard = scanner.nextInt();
        switch (viewInfo) {
            case HomeView:
                keyBoard += 100;
                HomeKeyStrokeStrategyContext homeKeyStrokeStrategyContext = new HomeKeyStrokeStrategyContext();
                if (keyBoard == KeyBoardEvent.Login.getCode()) {
                    HomeKeyStrokeStrategy loginStrategy = new LoginStrategy();
                    loginStrategy.keyBoardEvent(homeKeyStrokeStrategyContext);
                    break;
                } else if (keyBoard == KeyBoardEvent.Register.getCode()) {
                    HomeKeyStrokeStrategy registerStrategy = new RegisterStrategy();
                    registerStrategy.keyBoardEvent(homeKeyStrokeStrategyContext);
                    break;
                } else if (keyBoard == KeyBoardEvent.Exit.getCode()) {
                    System.out.println("退出成功");
                    System.exit(0);
                    break;
                } else {
                    System.out.println("操作错误请重新操作");
                    this.ListenKeyBoardEvent(viewInfo);
                    return;
                }
            case MainView:
                keyBoard += 200;
                MainKeyStrokeStrategyContext mainKeyStrokeStrategyContext = new MainKeyStrokeStrategyContext();
                if (keyBoard == KeyBoardEvent.PersonInfo.getCode()) {
                    MainKeyStokeStategy personInfoStategy = new PersonInfoStrategy();
                    MainKeyStrokeStrategyContext.isPerson = true;
                    personInfoStategy.keyBoardEvent(mainKeyStrokeStrategyContext);
                    break;
                }
                else if (keyBoard == KeyBoardEvent.GetSalary.getCode()) {
                    MainKeyStokeStategy personInfoStategy = new PersonInfoStrategy();
                    MainKeyStrokeStrategyContext.isPerson = false;
                    personInfoStategy.keyBoardEvent(mainKeyStrokeStrategyContext);
                    break;
                } else if (keyBoard == KeyBoardEvent.UpdatePassword.getCode()) {
                    MainKeyStokeStategy updatePasswordStrategy = new UpdatePasswordStrategy();
                    updatePasswordStrategy.keyBoardEvent(mainKeyStrokeStrategyContext);
                    break;
                } else {
                    System.out.println("操作错误请重新操作");
                    this.ListenKeyBoardEvent(viewInfo);
                    return;
                }
            default:
                System.out.println("没有这个操作");
                break;
        }
    }

    /**
     * 触发按键事件
     */
    // public void TriggerEvent(ViewInfo viewInfo, int keyBoard) {
    //     switch (viewInfo) {
    //         case HomeView:
    //             if (keyBoard == KeyBoardEvent.Register.getCode()) {
    //                 System.out.println("霍盛展要注册了");
    //             }
    //             break;
    //         default:
    //             System.out.println("胡扯");
    //             break;
    //     }
    // }

}
