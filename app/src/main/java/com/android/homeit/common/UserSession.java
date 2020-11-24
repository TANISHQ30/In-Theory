package com.android.homeit.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.HashMap;

/**
 * Created by win 07 on 08/07/2016.
 */


public class UserSession {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    public static final String PREFER_NAME = "userdata_odd";
    public static final String KEY_PERSON_ID = "pid";
    public static final String KEY_NAME = "name";
    public static final String KEY_GIVENNAME = "persongivenname";
    public static final String KEY_FAMILYNAME = "personfamilyname";
    public static final String KEY_EMAIL = "email";
    public static final Uri KEY_PIMG = Uri.parse("profileimg");


    public static final String KEY_NOTIFICATIONS = "notifications";
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String ENABLED = "enabled";
    public static final String ISFIRSTIME = "isfirsttime";
    /*public static final String KEY_VENDERID = "vendorid";
    public static final String KEY_STOREID = "storeid";
    public static final String KEY_ROLEID = "roleid";
    public static final String  SCHOOL_NAME= "getSchoolName";
    public static final String  ROLE_ID= "getRoleId";*/


    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public UserSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String pid, String name, String pname, String fname, String email, boolean flag) {

        editor.putString(KEY_PERSON_ID, pid);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_GIVENNAME, pname);
        editor.putString(KEY_FAMILYNAME, fname);
        editor.putString(KEY_EMAIL,email);
        //editor.putString(KEY_PIMG,personPhoto)
        editor.putBoolean(IS_USER_LOGIN, flag);
        // editor.putString(KEY_CITY, city);
        editor.commit();
    }

    public String getKeyPersonIdId(){
        return pref.getString(KEY_PERSON_ID,null);
    }

    public String getKeyName()
    {
        return pref.getString(KEY_NAME,null);
    }

    public String getKeyGivenname()
    {
        return pref.getString(KEY_GIVENNAME,null);
    }

    public String getKeyFamilyname()
    {
        return pref.getString(KEY_FAMILYNAME,null);
    }

    public String getKeyEmail()
    {
        return pref.getString(KEY_EMAIL,null);
    }

    public void createUserId(String pid,String name) {

        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_PERSON_ID, pid);
        editor.putString(KEY_NAME, name);
        // editor.putString(KEY_CITY, city);
        editor.commit();
    }

    /*public void createSubjectSession(String subid)
    {

        editor.putString(SUBJECT_ID, subid);
    }*/

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        //  user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PERSON_ID, pref.getString(KEY_PERSON_ID, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        //  user.put(KEY_CITY, pref.getString(KEY_CITY, null));
        return user;
    }


    public void logoutUser() {
        editor.clear();
        editor.commit();
    }

    public void addNotification(String notification) {

        // get old notifications
        String oldNotifications = getNotifications();

        if (oldNotifications != null) {
            oldNotifications += "|" + notification;
        } else {
            oldNotifications = notification;
        }

        editor.putString(KEY_NOTIFICATIONS, oldNotifications);
        editor.commit();
    }

    public String getNotifications() {
        return pref.getString(KEY_NOTIFICATIONS, null);
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    public void userLoggedOff() {
        editor.putBoolean(IS_USER_LOGIN, false);
        editor.commit();
    }

    public boolean isFirstTime() {
        return pref.getBoolean(ISFIRSTIME, true);
    }

    public void setFirstTime() {

        editor.putBoolean(ISFIRSTIME, false);
        editor.commit();
    }

    public void putProfileImg(String imgname)
    {
        editor.putString(String.valueOf(KEY_PIMG),imgname);
        editor.commit();
    }

    public String getProfileImg()
    {
        return pref.getString(String.valueOf(KEY_PIMG), null);
    }

    public void setProfileImg(String imgname)
    {
        editor.putString(String.valueOf(KEY_PIMG),imgname);
        editor.commit();
    }

    public boolean getEnabled() {
        boolean e = pref.getBoolean(ENABLED, false);
        return e;
    }

    public void putEnabled(boolean enabled) {
        editor.putBoolean(ENABLED, enabled);
        editor.commit();
    }

    /*public void putVendorId(String vendorid)
    {
        editor.putString(KEY_VENDERID,vendorid);
        editor.commit();
    }

    public String getCurrentVendorId()
    {
        return pref.getString(KEY_VENDERID, null);
    }

    public void putStoreId(String storeid)
    {
        editor.putString(KEY_STOREID,storeid);
        editor.commit();
    }

    public String getCurrentStoreId()
    {
        return pref.getString(KEY_STOREID, null);
    }


    public void putRoleId(String roleid)
    {
        editor.putString(KEY_ROLEID,roleid);
        editor.commit();
    }

    public String getRoleId()
    {
        return pref.getString(KEY_ROLEID, null);
    }*/

   public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }


    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
