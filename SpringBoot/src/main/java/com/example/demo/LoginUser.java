package com.example.demo;
public class LoginUser {
    //数据可视化
    private static int visitCount = 0;
    public static void addVisitCount() {

        LoginUser.visitCount++;
    }

    public static int getVisitCount() {
        return LoginUser.visitCount;
    }
}
