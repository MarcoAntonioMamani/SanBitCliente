package com.sanbit.tevendo.Clientes.DbLocal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientesListViewModel extends AndroidViewModel {

    private ClienteRepository mRepository;
    private LiveData<List<ClienteEntity>> clientes;

    public ClientesListViewModel(@NonNull Application application) {
        super(application);

        mRepository = new ClienteRepository(application);
    }

    public LiveData<List<ClienteEntity>> getClientes() {
        if (clientes == null) {
            clientes = mRepository.getAllClientes();
        }
        return clientes;
    }

    public ClienteEntity getCliente(int id) throws ExecutionException, InterruptedException {
        return mRepository.getCliente(id);
    }
    public ClienteEntity getClienteNumi(int id) throws ExecutionException, InterruptedException {
        return mRepository.getClienteNumi(id);
    }
    public ClienteEntity getClientebycode(String code) throws ExecutionException, InterruptedException {
        return mRepository.getClientebyCode(code);
    }
    public List<ClienteEntity> getMAllCliente(int code) throws ExecutionException, InterruptedException {
        return mRepository.getMClienteAll(code);
    }
    public List<ClienteEntity> getMAllStateCliente(int code) throws ExecutionException, InterruptedException {
        return mRepository.getMClienteAllState(code);
    }
    public List<ClienteEntity> getMAllStateClienteUpdate(int code) throws ExecutionException, InterruptedException {
        return mRepository.getMClienteAllStateUpdate(code);
    }
    public void insertCliente(ClienteEntity user) {
        mRepository.insertCliente(user);
    }

    public void updateCliente(ClienteEntity user) {
        mRepository.updateCliente(user);
    }
    public void updateListCliente(ClienteEntity[] user) {
        mRepository.updateListCliente(user);
    }
    public void deleteCliente(ClienteEntity user) {
        mRepository.deleteCliente(user);
    }

    public void deleteAllClientes() {
        mRepository.deleteAllClientes();
    }
}
