package com.sanbit.tevendo.Clientes.DbLocal.Categoria;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoriaListViewModel extends AndroidViewModel {
    private CategoriaRepository mRepository;
    private LiveData<List<CategoriaEntity>> users;

    public CategoriaListViewModel(@NonNull Application application) {
        super(application);

        mRepository = new CategoriaRepository(application);
    }
    public List<CategoriaEntity> getMAllCategorias(int code) throws ExecutionException, InterruptedException {
        return mRepository.getMCategoriasAll(code);
    }
    /*public List<CategoriaEntity> getProductoByCliente(int code) throws ExecutionException, InterruptedException {
        return mRepository.getMProductoByCliente(code);
    }*/
    public LiveData<List<CategoriaEntity>> getCategorias() {
        if (users == null) {
            users = mRepository.getAllCategorias();
        }

        return users;
    }

    public CategoriaEntity getCategoria(int id) throws ExecutionException, InterruptedException {
        return mRepository.getCategoria(id);
    }

    public void insertCategorias(CategoriaEntity user) {
        mRepository.insertCategoria(user);
    }

    public void updateCategorias(CategoriaEntity user) {
        mRepository.updateCategoria(user);
    }

    public void deleteCategorias(CategoriaEntity user) {
        mRepository.deleteCategoria(user);
    }

    public void deleteAllCategorias() {
        mRepository.deleteAllCategorias();
    }
    
}
