package com.sanbit.tevendo.Clientes.DbLocal.Precios;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PreciosListViewModel extends AndroidViewModel {

    private PrecioRepository mRepository;
    private LiveData<List<PrecioEntity>> users;

    public PreciosListViewModel(@NonNull Application application) {
        super(application);

        mRepository = new PrecioRepository(application);
    }

    public LiveData<List<PrecioEntity>> getProductos() {
        if (users == null) {
            users = mRepository.getAllPrecios();
        }

        return users;
    }

    public PrecioEntity getPrecio(int id) throws ExecutionException, InterruptedException {
        return mRepository.getPrecio(id);
    }
    public List<PrecioEntity> getMAllPrecio(int code) throws ExecutionException, InterruptedException {
        return mRepository.getMPrecioAll(code);
    }
    public void insertPrecio(PrecioEntity user) {
        mRepository.insertPrecios(user);
    }



    public void updatePrecio(PrecioEntity user) {
        mRepository.updatePrecios(user);
    }
    public void updateListPrecio(PrecioEntity[] user) {
        mRepository.updateListPrecios(user);
    }
    public void deletePrecio(PrecioEntity user) {
        mRepository.deletePrecios(user);
    }

    public void deleteAllPrecios() {
        mRepository.deleteAllPrecios();
    }
}
