package com.mozper.android.example.data.login

import android.app.Application
import com.mozper.android.example.common.db.MozperDB
import com.mozper.android.example.common.db.dao.UserDataDao
import com.mozper.android.example.common.db.entity.UserData
import com.mozper.android.example.data.Result
import com.mozper.android.example.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource, val application: Application) {
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null


    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)
        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private suspend fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        var userDataDao: UserDataDao =  MozperDB.getInstance(application)?.userDataDao()
        val data =
            this.user?.let {
                UserData(
                    it.userId,
                    it.displayName,
                    isLogged = true
                )
            }

        if (data != null) {
            userDataDao.insert(data)
        }
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    fun logout(){
        user = null
    }

}