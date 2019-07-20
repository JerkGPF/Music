package com.example.music.helps;

/**
 * 1.用户登录
 *   1.当用户没有登录过时，利用sp保存登录标记
 *   2.利用全局单例类UserHelp保存登录信息
 *       1.用户登录后
 *       2.用户重新打开应用程序，检测sp是否存在用户标记
 * 2.用户退出
 *   1.删除sp的用户标记，退出
 */

public class UserHelp {

    private static UserHelp instance;

    private UserHelp(){};

    public static UserHelp getInstance(){
        if (instance == null){
            synchronized (UserHelp.class){
                if (instance == null){
                    instance = new UserHelp();
                }
            }
        }
        return instance;
    }
    private String phone;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
