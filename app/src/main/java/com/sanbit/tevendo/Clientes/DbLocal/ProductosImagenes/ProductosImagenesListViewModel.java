package com.sanbit.tevendo.Clientes.DbLocal.ProductosImagenes;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductosImagenesListViewModel extends AndroidViewModel {

    private ProductoImagenesRepository mRepository;
    private LiveData<List<ProductosImagenesEntity>> users;

    public ProductosImagenesListViewModel(@NonNull Application application) {
        super(application);

        mRepository = new ProductoImagenesRepository(application);
    }
    public List<ProductosImagenesEntity> getMAllImagenes(int code) throws ExecutionException, InterruptedException {
        return mRepository.getMImagenesAll(code);
    }
    /*public List<ProductosImagenesEntity> getProductoByCliente(int code) throws ExecutionException, InterruptedException {
        return mRepository.getMProductoByCliente(code);
    }*/
    public LiveData<List<ProductosImagenesEntity>> getImagenes() {
        if (users == null) {
            users = mRepository.getAllImagenes();
        }

        return users;
    }

    public ProductosImagenesEntity getImagenes(int id) throws ExecutionException, InterruptedException {
        return mRepository.getImagenes(id);
    }

    public void insertImagenes(ProductosImagenesEntity user) {
        mRepository.insertImagenes(user);
    }

    public void updateImagenes(ProductosImagenesEntity user) {
        mRepository.updateImagenes(user);
    }

    public void deleteImagenes(ProductosImagenesEntity user) {
        mRepository.deleteImagenes(user);
    }

    public void deleteAllImagenes() {
        mRepository.deleteAllImagenes();
    }
}
