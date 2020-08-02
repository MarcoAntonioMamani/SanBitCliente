package com.sanbit.tevendo.Clientes.DbLocal.User;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user ORDER BY id DESC")
    LiveData<List<UserEntity>> getAllUsers();

    @Query("SELECT * FROM user WHERE id=:id")
    UserEntity getUserById(int id);

    @Query("SELECT * FROM user WHERE id=:id")
    LiveData<UserEntity> getUser(int id);

    @Insert
    long insert(UserEntity note);

    @Update
    void update(UserEntity note);


    @Delete
    void delete(UserEntity note);

    @Query("DELETE FROM user")
    void deleteAll();
}
