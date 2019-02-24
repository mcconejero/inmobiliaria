package com.triana.salesianos.inmobilimario;

import android.content.Context;
import android.content.SharedPreferences;

public class UtilToken {

    public static void setToken(Context mContext, String token) {
        SharedPreferences sharedPreferences =
                mContext.getSharedPreferences(
                        mContext.getString(R.string.sharedpreferences_filename),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(mContext.getString(R.string.jwt_key), token);
        editor.commit();
    }

    public static void setIdUser(Context mContext, String idUser) {
        SharedPreferences sharedPreferences =
                mContext.getSharedPreferences(
                        mContext.getString(R.string.sharedpreferences_filename),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(mContext.getString(R.string.jwt_id), idUser);
        editor.commit();
    }


    public static String getToken(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getString(R.string.sharedpreferences_filename),
                Context.MODE_PRIVATE
        );

        String jwt = sharedPreferences
                .getString(mContext.getString(R.string.jwt_key), null);

        return jwt;
    }

    public static String getIdUser(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getString(R.string.sharedpreferences_filename),
                Context.MODE_PRIVATE
        );

        String idUser = sharedPreferences
                .getString(mContext.getString(R.string.jwt_id), null);

        return idUser;
    }

}
