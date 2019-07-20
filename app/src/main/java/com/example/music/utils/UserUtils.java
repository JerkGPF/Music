package com.example.music.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.music.R;
import com.example.music.activities.LoginActivity;
import com.example.music.helps.RealmHelp;
import com.example.music.models.UserModel;

import java.util.List;

public class UserUtils {
    /**
     * 验证登录用户的合法性
     */
    public static  boolean validateLogin(Context context,String phone,String password){
        if (!RegexUtils.isMobileExact(phone)){
            Toast.makeText(context,"无效的手机号",Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(context,"请输入密码",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    /**
     * 退出登录
     */
    public static void logout(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        //添加intent标志符，清理task栈，并且生成新的一个task栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //定义跳转activity动画
        ((Activity)context).overridePendingTransition(R.anim.open_enter,R.anim.open_exit);
    }

    /**
     * 注册用户
     * @param context
     * @param phone
     * @param password
     * @param passwordConfirm
     * @return
     */
    public static boolean registerUser(Context context,String phone,String password,String passwordConfirm){
        if (!RegexUtils.isMobileExact(phone)){
            Toast.makeText(context,"无效的手机号",Toast.LENGTH_LONG).show();
            return false ;
        }
        if (StringUtils.isEmpty(password )|| !password.equals(passwordConfirm)){
            Toast.makeText(context,"请确认密码",Toast.LENGTH_LONG).show();
            return false;
        }
        //用户输入的手机号是否被注册
        /**
         * 1.通过realm获取到当前已经注册的所有用户
         * 2.根据用户输入的手机号匹配查询所有用户
         */

        if (UserUtils.userExistFromPhone(phone)){
            Toast.makeText(context,"该手机号已存在",Toast.LENGTH_LONG).show();
            return false;
        }

        UserModel userModel = new UserModel();
        userModel.setPhone(phone);
        userModel.setPassword(EncryptUtils.encryptMD5ToString(password));

        saveUser(userModel);

        return true;
    }

    /**
     * 保存用户到数据库
     * @param userModel
     */
    public static void saveUser (UserModel userModel){
        RealmHelp realmHelp = new RealmHelp();
        realmHelp.saveUser(userModel);
        realmHelp.close();
    }
    /**
     * 根据手机号判断是否已存在
     */
    public static boolean userExistFromPhone(String phone){
        boolean result = false;

        RealmHelp realmHelp = new RealmHelp();
        List<UserModel> allUser = realmHelp.getAllUser();

        for (UserModel userModel : allUser){
            if (userModel.getPhone().equals(phone)){
                //当前手机号已经存在于数据库中了
                result = true;
                break;
            }
        }
        return result;
    }
}
