package com.mozper.android.example.common.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mozper.android.example.common.db.entity.UserData

@Dao
interface UserDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg userData: UserData)

    @Query("SELECT * FROM UserData WHERE id= :id")
    fun getUserData(id: String): UserData

    @Query("UPDATE UserData SET email= :email WHERE id = :id")
    fun updateEmail(email: String, id: String)

    @Query("UPDATE UserData SET isLogged= :isLogged WHERE id = :id")
    fun updateStatus(isLogged: Boolean, id: String)

    @Query("SELECT * FROM UserData")
    fun getUsersData(): List<UserData>

    @Query("DELETE FROM UserData WHERE id= :id")
    fun deleteUserData(id: String)
}