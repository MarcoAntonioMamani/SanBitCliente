package com.sanbit.tevendo.Productos.DbLocal;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.sanbit.tevendo.Clientes.DbLocal.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by ravi on 05/02/18.
 */

public class ProductoRepository {

    private ProductoDao mProductoDao;
    private LiveData<List<ProductoEntity>> mAllProductos;

    public ProductoRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mProductoDao = db.productoDao();
        mAllProductos = mProductoDao.getAllProductos();
    }

    public LiveData<List<ProductoEntity>> getAllProductos() {
        return mAllProductos;
    }

    public ProductoEntity getProducto(int noteId) throws ExecutionException, InterruptedException {
        return new getProductosAsync(mProductoDao).execute(noteId).get();
    }
    public List<ProductoEntity> getMProductoAll(int clienteId) throws ExecutionException, InterruptedException {
        return new getMProductoAllAsync(mProductoDao).execute(clienteId).get();
    }
   /* public List<ProductoEntity> getMProductoByCliente(int clienteId) throws ExecutionException, InterruptedException {
        return new getMProductobyClienteAsync(mProductoDao).execute(clienteId).get();
    }*/
    public void insertProducto(ProductoEntity user) {
        new insertProductosAsync(mProductoDao).execute(user);
    }

    public void updateProductos(ProductoEntity user) {
        new updateProductosAsync(mProductoDao).execute(user);
    }

    public void deleteProductos(ProductoEntity user) {
        new deleteproductosAsync(mProductoDao).execute(user);
    }

    public void deleteAllProductos() {
        new deleteAllProductoAsync(mProductoDao).execute();
    }

    /**
     * NOTE: all write operations should be done in background thread,
     * otherwise the following error will be thrown
     * `java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.`
     */

    private static class getProductosAsync extends AsyncTask<Integer, Void, ProductoEntity> {

        private ProductoDao mProductoDaoAsync;

        getProductosAsync(ProductoDao animalDao) {
            mProductoDaoAsync = animalDao;
        }

        @Override
        protected ProductoEntity doInBackground(Integer... ids) {
            return mProductoDaoAsync.getProductoById(ids[0]);
        }
    }
    private static class getMProductoAllAsync extends AsyncTask<Integer, Void, List<ProductoEntity>> {

        private ProductoDao mProductoDaoAsync;

        getMProductoAllAsync(ProductoDao clienteDao) {
            mProductoDaoAsync = clienteDao;
        }

        @Override
        protected List<ProductoEntity> doInBackground(Integer... ids) {
            return mProductoDaoAsync.getAllMProductos();
        }
    }
   /* private static class getMProductobyClienteAsync extends AsyncTask<Integer, Void, List<ProductoEntity>> {

        private ProductoDao mProductoDaoAsync;

        getMProductobyClienteAsync(ProductoDao clienteDao) {
            mProductoDaoAsync = clienteDao;
        }

        @Override
        protected List<ProductoEntity> doInBackground(Integer... ids) {
            return mProductoDaoAsync.getProductoByCliente(ids[0]);
        }
    }*/
    private static class insertProductosAsync extends AsyncTask<ProductoEntity, Void, Long> {

        private ProductoDao mProductoDaoAsync;

        insertProductosAsync(ProductoDao userDao) {
            mProductoDaoAsync = userDao;
        }

        @Override
        protected Long doInBackground(ProductoEntity... notes) {
            long id = mProductoDaoAsync.insert(notes[0]);
            return id;
        }
    }

    private static class updateProductosAsync extends AsyncTask<ProductoEntity, Void, Void> {

        private ProductoDao mProductoDaoAsync;

        updateProductosAsync(ProductoDao userDao) {
            mProductoDaoAsync = userDao;
        }

        @Override
        protected Void doInBackground(ProductoEntity... notes) {
            mProductoDaoAsync.update(notes[0]);
            return null;
        }
    }

    private static class deleteproductosAsync extends AsyncTask<ProductoEntity, Void, Void> {

        private ProductoDao mProductoDaoAsync;

        deleteproductosAsync(ProductoDao userDao) {
            mProductoDaoAsync = userDao;
        }

        @Override
        protected Void doInBackground(ProductoEntity... notes) {
            mProductoDaoAsync.delete(notes[0]);
            return null;
        }
    }

    private static class deleteAllProductoAsync extends AsyncTask<ProductoEntity, Void, Void> {

        private ProductoDao mProductoDaoAsync;

        deleteAllProductoAsync(ProductoDao productoDao) {
            mProductoDaoAsync = productoDao;
        }

        @Override
        protected Void doInBackground(ProductoEntity... notes) {
            mProductoDaoAsync.deleteAll();
            return null;
        }
    }
}
