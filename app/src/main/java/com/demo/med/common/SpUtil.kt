package com.demo.med.common

import android.content.Context
import android.content.SharedPreferences


class SpUtil private constructor() {

    var mContext: Context? = null
    private var mPref: SharedPreferences? = null

    fun init(context: Context) {
        if (mContext == null) {
            mContext = context
        }
        if (mPref == null) {
            mPref = mContext!!.getSharedPreferences("Conch", Context.MODE_PRIVATE)
        }
    }

    companion object {
        @Volatile
        private var mInstance: SpUtil? = null

        val instance: SpUtil?
            get() {
                if (null == mInstance) {
                    synchronized(SpUtil::class.java) {
                        if (null == mInstance) {
                            mInstance = SpUtil()
                        }
                    }
                }
                return mInstance
            }
    }

    fun putString(key: String, value: String) {
        val editor = mPref?.edit()
        editor?.putString(key, value)
        editor?.commit()
    }


    fun putLong(key: String, value: Long) {
        val editor = mPref!!.edit()
        editor.putLong(key, value)
        editor.commit()
    }

    fun putInt(key: String, value: Int) {
        val editor = mPref?.edit()
        editor?.putInt(key, value)
        editor?.commit()
    }

    fun putBoolean(key: String, value: Boolean) {
        val editor = mPref?.edit()
        editor?.putBoolean(key, value)
        editor?.commit()
    }

    fun getBoolean(key: String): Boolean {
        return mPref!!.getBoolean(key, false)
    }

    fun getBoolean(key: String, def: Boolean): Boolean {
        return mPref!!.getBoolean(key, def)
    }

    fun getString(key: String): String? {
        return mPref!!.getString(key, "")
    }

    fun getString(key: String, def: String): String? {
        return mPref!!.getString(key, def)
    }

    fun getLong(key: String): Long {
        return mPref!!.getLong(key, 0)
    }

    fun getLong(key: String, defInt: Int): Long {
        return mPref!!.getLong(key, defInt.toLong())
    }

    fun getInt(key: String): Int {
        return mPref!!.getInt(key, 0)
    }

    fun getInt(key: String, defInt: Int): Int {
        return mPref!!.getInt(key, defInt)
    }

    operator fun contains(key: String): Boolean {
        return mPref!!.contains(key)
    }


    fun remove(key: String) {
        val editor = mPref?.edit()
        editor?.remove(key)
        editor?.commit()
    }

    fun clear() {
        val editor = mPref?.edit()
        editor?.clear()
        editor?.apply()
    }


}
