package com.sanbit.tevendo.Clientes.DbLocal.ProductosImagenes;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.sanbit.tevendo.Clientes.DbLocal.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductoImagenesRepository {

    private ProductosImagenesDao mProductosImagenesDao;
    private LiveData<List<ProductosImagenesEntity>> mAllProductos;

    public ProductoImagenesRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mProductosImagenesDao = db.imagenesDao();
        mAllProductos = mProductosImagenesDao.getAllImagenes();
    }

    public LiveData<List<ProductosImagenesEntity>> getAllImagenes() {
        return mAllProductos;
    }

    public ProductosImagenesEntity getImagenes(int noteId) throws ExecutionException, InterruptedException {
        return new getImagenesAsync(mProductosImagenesDao).execute(noteId).get();
    }
    public List<ProductosImagenesEntity> getMImagenesAll(int clienteId) throws ExecutionException, InterruptedException {
        return new getMImagenesAllAsync(mProductosImagenesDao).execute(clienteId).get();
    }

    public void insertImagenes(ProductosImagenesEntity user) {
        new insertImagenesAsync(mProductosImagenesDao).execute(user);
    }

    public void updateImagenes(ProductosImagenesEntity user) {
        new updateImagenesAsync(mProductosImagenesDao).execute(user);
    }

    public void deleteImagenes(ProductosImagenesEntity user) {
        new deleteImagenesAsync(mProductosImagenesDao).execute(user);
    }

    public void deleteAllImagenes() {
        new deleteAllImagenesAsync(mProductosImagenesDao).execute();
    }

    /**
     * NOTE: all write operations should be done in background thread,
     * otherwise the following error will be thrown
     * `java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.`
     */

    private static class getImagenesAsync extends AsyncTask<Integer, Void, ProductosImagenesEntity> {

        private ProductosImagenesDao mProductosImagenesDaoAsync;

        getImagenesAsync(ProductosImagenesDao animalDao) {
            mProductosImagenesDaoAsync = animalDao;
        }

        @Override
        protected ProductosImagenesEntity doInBackground(Integer... ids) {
            return mProductosImagenesDaoAsync.getImagenesById(ids[0]);
        }
    }
    private static class getMImagenesAllAsync extends AsyncTask<Integer, Void, List<ProductosImagenesEntity>> {

        private ProductosImagenesDao mProductosImagenesDaoAsync;

        getMImagenesAllAsync(ProductosImagenesDao clienteDao) {
            mProductosImagenesDaoAsync = clienteDao;
        }

        @Override
        protected List<ProductosImagenesEntity> doInBackground(Integer... ids) {
            return mProductosImagenesDaoAsync.getAllMImagenes();
        }
    }

    private static class insertImagenesAsync extends AsyncTask<ProductosImagenesEntity, Void, Long> {

        private ProductosImagenesDao mProductosImagenesDaoAsync;

        insertImagenesAsync(ProductosImagenesDao userDao) {
            mProductosImagenesDaoAsync = userDao;
        }

        @Override
        protected Long doInBackground(ProductosImagenesEntity... notes) {
            long id = mProductosImagenesDaoAsync.insert(notes[0]);
            return id;
        }
    }

    private static class updateImagenesAsync extends AsyncTask<ProductosImagenesEntity, Void, Void> {

        private ProductosImagenesDao mProductosImagenesDaoAsync;

        updateImagenesAsync(ProductosImagenesDao userDao) {
            mProductosImagenesDaoAsync = userDao;
        }

        @Override
        protected Void doInBackground(ProductosImagenesEntity... notes) {
            mProductosImagenesDaoAsync.update(notes[0]);
            return null;
        }
    }

    private static class deleteImagenesAsync extends AsyncTask<ProductosImagenesEntity, Void, Void> {

        private ProductosImagenesDao mProductosImagenesDaoAsync;

        deleteImagenesAsync(ProductosImagenesDao userDao) {
            mProductosImagenesDaoAsync = userDao;
        }

        @Override
        protected Void doInBackground(ProductosImagenesEntity... notes) {
            mProductosImagenesDaoAsync.delete(notes[0]);
            return null;
        }
    }

    private static class deleteAllImagenesAsync extends AsyncTask<ProductosImagenesEntity, Void, Void> {

        private ProductosImagenesDao mProductosImagenesDaoAsync;

        deleteAllImagenesAsync(ProductosImagenesDao ProductosImagenesDao) {
            mProductosImagenesDaoAsync = ProductosImagenesDao;
        }

        @Override
        protected Void doInBackground(ProductosImagenesEntity... notes) {
            mProductosImagenesDaoAsync.deleteAll();
            return null;
        }
    }
}
