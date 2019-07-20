package com.example.music.helps;

import com.example.music.models.UserModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelp {

    private Realm mRealm;

    public RealmHelp(){
        mRealm = Realm.getDefaultInstance();
    }

    public void close(){
        if (mRealm !=null && !mRealm.isClosed()){
            mRealm.close();
        }
    }
    /**
     * 保存用户信息
     */
    public void saveUser(UserModel userModel){
        mRealm.beginTransaction();
        mRealm.insert(userModel);
        //  mRealm.insertOrUpdate(userModel);
        mRealm.commitTransaction();
    }
    /**
     * 返回所有用户
     */
    public List<UserModel>getAllUser(){
        RealmQuery<UserModel>query = mRealm.where(UserModel.class);
        RealmResults<UserModel> results = query.findAll();
        return results;
    }
}
