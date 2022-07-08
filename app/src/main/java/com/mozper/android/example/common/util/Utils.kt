package com.mozper.android.example.common.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class Utils {

    companion object {
        private const val preference_key = "app_demo_mozper"
        private val application = instance

        private fun getSecuredSharedPreferences(mContext: Context): SharedPreferences? {
            var sharedPreferences: SharedPreferences? = null
            try {
                val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
                sharedPreferences = EncryptedSharedPreferences.create(
                    preference_key,
                    masterKeyAlias,
                    mContext,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
                return sharedPreferences
            } catch (ex: Exception) {
                Log.e("App", ex.message!!)
            }
            return sharedPreferences
        }



        fun getPrefsString(key: String?, context: Context?): String? {
            val prefs: SharedPreferences = getSecuredSharedPreferences(context!!)!!
            return prefs.getString(key, null)
        }

        fun setPrefsString(key: String?, value: String?, context: Context?) {
            val prefs: SharedPreferences = getSecuredSharedPreferences(context!!)!!
            val editor = prefs.edit()
            editor.putString(key, value)
            editor.commit()
        }

    }
}