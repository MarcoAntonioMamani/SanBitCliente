package com.sanbit.tevendo.Clientes.DbLocal.Stock;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StockListViewModel extends AndroidViewModel {

    private StockRepository mRepository;
    private LiveData<List<StockEntity>> users;
   private List<StockEntity> stockList;
    public StockListViewModel(@NonNull Application application) {
        super(application);

        mRepository = new StockRepository(application);
    }

    public LiveData<List<StockEntity>> getStock() {
        if (users == null) {
            users = mRepository.getAllStock();
        }

        return users;
    }


    public List<StockEntity> getAllStock() {
        if (stockList == null) {
            try {
                stockList = mRepository.getMStockAll(1);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return stockList;
    }
    public StockEntity getStock(int id) throws ExecutionException, InterruptedException {
        return mRepository.getStock(id);
    }

    public void insertStock(StockEntity user) {
        mRepository.insertStock(user);
    }



    public void updateStock(StockEntity user) {
        mRepository.updateStock(user);
    }

    public void deleteStock(StockEntity user) {
        mRepository.deleteStock(user);
    }

    public void deleteAllStocks() {
        mRepository.deleteAllStock();
    } 
    
}
