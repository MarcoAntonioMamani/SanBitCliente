package com.sanbit.tevendo.Clientes.DbLocal.Categoria;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.sanbit.tevendo.Clientes.DbLocal.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoriaRepository {

    private CategoriaDao mCategoriaDao;
    private LiveData<List<CategoriaEntity>> mAllProductos;

    public CategoriaRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mCategoriaDao = db.categoriaDao();
        mAllProductos = mCategoriaDao.getAllCategorias();
    }

    public LiveData<List<CategoriaEntity>> getAllCategorias() {
        return mAllProductos;
    }

    public CategoriaEntity getCategoria(int noteId) throws ExecutionException, InterruptedException {
        return new getCategoriaAsync(mCategoriaDao).execute(noteId).get();
    }
    public List<CategoriaEntity> getMCategoriasAll(int clienteId) throws ExecutionException, InterruptedException {
        return new getMCategoriasAllAsync(mCategoriaDao).execute(clienteId).get();
    }
    /* public List<CategoriaEntity> getMProductoByCliente(int clienteId) throws ExecutionException, InterruptedException {
         return new getMProductobyClienteAsync(mCategoriaDao).execute(clienteId).get();
     }*/
    public void insertCategoria(CategoriaEntity user) {
        new insertCategoriaAsync(mCategoriaDao).execute(user);
    }

    public void updateCategoria(CategoriaEntity user) {
        new updateCategoriaAsync(mCategoriaDao).execute(user);
    }

    public void deleteCategoria(CategoriaEntity user) {
        new deleteCategoriasAsync(mCategoriaDao).execute(user);
    }

    public void deleteAllCategorias() {
        new deleteAllCategoriaAsync(mCategoriaDao).execute();
    }

    /**
     * NOTE: all write operations should be done in background thread,
     * otherwise the following error will be thrown
     * `java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.`
     */

    private static class getCategoriaAsync extends AsyncTask<Integer, Void, CategoriaEntity> {

        private CategoriaDao mCategoriaDaoAsync;

        getCategoriaAsync(CategoriaDao animalDao) {
            mCategoriaDaoAsync = animalDao;
        }

        @Override
        protected CategoriaEntity doInBackground(Integer... ids) {
            return mCategoriaDaoAsync.getCategoriaById(ids[0]);
        }
    }
    private static class getMCategoriasAllAsync extends AsyncTask<Integer, Void, List<CategoriaEntity>> {

        private CategoriaDao mCategoriaDaoAsync;

        getMCategoriasAllAsync(CategoriaDao clienteDao) {
            mCategoriaDaoAsync = clienteDao;
        }

        @Override
        protected List<CategoriaEntity> doInBackground(Integer... ids) {
            return mCategoriaDaoAsync.getAllMCategorias();
        }
    }

    private static class insertCategoriaAsync extends AsyncTask<CategoriaEntity, Void, Long> {

        private CategoriaDao mCategoriaDaoAsync;

        insertCategoriaAsync(CategoriaDao userDao) {
            mCategoriaDaoAsync = userDao;
        }

        @Override
        protected Long doInBackground(CategoriaEntity... notes) {
            long id = mCategoriaDaoAsync.insert(notes[0]);
            return id;
        }
    }

    private static class updateCategoriaAsync extends AsyncTask<CategoriaEntity, Void, Void> {

        private CategoriaDao mCategoriaDaoAsync;

        updateCategoriaAsync(CategoriaDao userDao) {
            mCategoriaDaoAsync = userDao;
        }

        @Override
        protected Void doInBackground(CategoriaEntity... notes) {
            mCategoriaDaoAsync.update(notes[0]);
            return null;
        }
    }

    private static class deleteCategoriasAsync extends AsyncTask<CategoriaEntity, Void, Void> {

        private CategoriaDao mCategoriaDaoAsync;

        deleteCategoriasAsync(CategoriaDao userDao) {
            mCategoriaDaoAsync = userDao;
        }

        @Override
        protected Void doInBackground(CategoriaEntity... notes) {
            mCategoriaDaoAsync.delete(notes[0]);
            return null;
        }
    }

    private static class deleteAllCategoriaAsync extends AsyncTask<CategoriaEntity, Void, Void> {

        private CategoriaDao mCategoriaDaoAsync;

        deleteAllCategoriaAsync(CategoriaDao CategoriaDao) {
            mCategoriaDaoAsync = CategoriaDao;
        }

        @Override
        protected Void doInBackground(CategoriaEntity... notes) {
            mCategoriaDaoAsync.deleteAll();
            return null;
        }
    }
}
