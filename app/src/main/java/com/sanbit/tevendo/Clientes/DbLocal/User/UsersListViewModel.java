package com.sanbit.tevendo.Clientes.DbLocal.User;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UsersListViewModel extends AndroidViewModel {

    private UserRepository mRepository;
    private LiveData<List<UserEntity>> users;

    public UsersListViewModel(@NonNull Application application) {
        super(application);

        mRepository = new UserRepository(application);
    }

    public LiveData<List<UserEntity>> getUsers() {
        if (users == null) {
            users = mRepository.getAllUsers();
        }

        return users;
    }

    public UserEntity getUser(int id) throws ExecutionException, InterruptedException {
        return mRepository.getUser(id);
    }

    public void insertUser(UserEntity user) {
        mRepository.insertUser(user);
    }

    public void updateUser(UserEntity user) {
        mRepository.updateNote(user);
    }

    public void deleteUSer(UserEntity user) {
        mRepository.deleteUser(user);
    }

    public void deleteAllUsers() {
        mRepository.deleteAllUsers();
    }
}
