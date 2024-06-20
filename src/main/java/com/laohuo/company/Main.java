package com.laohuo.company;

import com.laohuo.company.view.HomeView;

/**
 * 作者：霍盛展
 * 描述：牛逼
 */
public class Main {
    public static void main(String[] args) throws Exception {
        String[] animationFrames = {"|", "/", "-", "\\"};
        for (int i = 0; i < 2; i++) {
            System.out.print("Loading " + animationFrames[i % 4] + "\r");
            Thread.sleep(200);
        }
        System.out.println("Loading Complete!");
        HomeView.homeView();
    }
}
