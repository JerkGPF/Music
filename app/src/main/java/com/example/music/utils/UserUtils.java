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
import com.example.music.helps.UserHelp;
import com.example.music.models.UserModel;

import java.util.List;

public class UserUtils {
    /**
     * 验证登录用户的合法性
     */
    public static boolean validateLogin(Context context, String phone, String password) {
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        /**
         * 1.用户当前手机号是否已经被注册
         * 2.用户输入的手机号和密码是否匹配
         */
        if (!UserUtils.userExistFromPhone(phone)) {
            Toast.makeText(context, "当前手机号未注册", Toast.LENGTH_SHORT).show();
            return false;
        }
        RealmHelp realmHelp = new RealmHelp();
        boolean result = realmHelp.validateUser(phone, EncryptUtils.encryptMD5ToString(password));
        realmHelp.close();

        if (!result) {
            Toast.makeText(context, "手机号或者密码不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        //保存用户登录标记
        boolean isSave = SPUtils.saveUser(context, phone);
        if (!isSave) {
            Toast.makeText(context, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
            return false;
        }
//在全局中保存用户标记
        UserHelp.getInstance().setPhone(phone);
        return true;
    }

    /**
     * 退出登录
     */
    public static void logout(Context context) {
        //删除sp用户标记
        boolean isRemove = SPUtils.removeUser(context);
        if (!isRemove) {
            Toast.makeText(context, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, LoginActivity.class);
        //添加intent标志符，清理task栈，并且生成新的一个task栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //定义跳转activity动画
        ((Activity) context).overridePendingTransition(R.anim.open_enter, R.anim.open_exit);
    }

    /**
     * 注册用户
     *
     * @param context
     * @param phone
     * @param password
     * @param passwordConfirm
     * @return
     */
    public static boolean registerUser(Context context, String phone, String password, String passwordConfirm) {
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效的手机号", Toast.LENGTH_LONG).show();
            return false;
        }
        if (StringUtils.isEmpty(password) || !password.equals(passwordConfirm)) {
            Toast.makeText(context, "请确认密码", Toast.LENGTH_LONG).show();
            return false;
        }
        //用户输入的手机号是否被注册
        /**
         * 1.通过realm获取到当前已经注册的所有用户
         * 2.根据用户输入的手机号匹配查询所有用户
         */

        if (UserUtils.userExistFromPhone(phone)) {
            Toast.makeText(context, "该手机号已存在", Toast.LENGTH_LONG).show();
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
     *
     * @param userModel
     */
    public static void saveUser(UserModel userModel) {
        RealmHelp realmHelp = new RealmHelp();
        realmHelp.saveUser(userModel);
        realmHelp.close();
    }

    /**
     * 根据手机号判断是否已存在
     */
    public static boolean userExistFromPhone(String phone) {
        boolean result = false;

        RealmHelp realmHelp = new RealmHelp();
        List<UserModel> allUser = realmHelp.getAllUser();

        for (UserModel userModel : allUser) {
            if (userModel.getPhone().equals(phone)) {
                //当前手机号已经存在于数据库中了
                result = true;
                break;
            }
        }
        realmHelp.close();
        return result;
    }

    /**
     * 验证是否存在已登录用户
     */
    public static boolean validateUserLogin(Context context) {
        return SPUtils.isLoginUser(context);
    }

    /**
     * 修改密码
     * 1.数据验证
     *   1.原密码是否输入
     *   2.新密码是否输入且与旧密码是否相同
     *   3.原密码是否正确
     *     1.realm获取到当前登录的用户模型
     *     2.根据用户模型中保存的密码匹配用户原密码
     * 2.利用realm模型自动更新
     */
    public static boolean changePassword(Context context, String oldPassword,
                                         String password, String passwordConfirm) {
        if (TextUtils.isEmpty(oldPassword)) {
            Toast.makeText(context, "请输入原密码", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(password) || !password.equals(passwordConfirm)) {
            Toast.makeText(context, "请确认密码", Toast.LENGTH_LONG).show();
            return false;
        }

        //验证原密码是否正确
        RealmHelp realmHelp = new RealmHelp();
        UserModel userModel = realmHelp.getUser();

        if (!EncryptUtils.encryptMD5ToString(oldPassword).equals(userModel.getPassword())){

            Toast.makeText(context, "原密码不正确", Toast.LENGTH_LONG).show();
            return false;
        }

        realmHelp.changePassword(EncryptUtils.encryptMD5ToString(password));

        realmHelp.close();
        return true;
    }

}
