package com.sanbit.tevendo.Clientes.DbLocal;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sanbit.tevendo.Clientes.DbLocal.Categoria.CategoriaDao;
import com.sanbit.tevendo.Clientes.DbLocal.Categoria.CategoriaEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Pedido.PedidoDao;
import com.sanbit.tevendo.Clientes.DbLocal.Pedido.PedidoEntity;
import com.sanbit.tevendo.Clientes.DbLocal.PedidoDetalle.DetalleDao;
import com.sanbit.tevendo.Clientes.DbLocal.PedidoDetalle.DetalleEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Precios.PrecioDao;
import com.sanbit.tevendo.Clientes.DbLocal.Precios.PrecioEntity;
import com.sanbit.tevendo.Clientes.DbLocal.ProductosImagenes.ProductosImagenesDao;
import com.sanbit.tevendo.Clientes.DbLocal.ProductosImagenes.ProductosImagenesEntity;
import com.sanbit.tevendo.Clientes.DbLocal.Stock.StockDao;
import com.sanbit.tevendo.Clientes.DbLocal.Stock.StockEntity;
import com.sanbit.tevendo.Clientes.DbLocal.User.UserDao;
import com.sanbit.tevendo.Clientes.DbLocal.User.UserEntity;
import com.sanbit.tevendo.Productos.DbLocal.ProductoDao;
import com.sanbit.tevendo.Productos.DbLocal.ProductoEntity;


/**
 * Created by ravi on 05/02/18.
 */

@Database(entities = { ClienteEntity.class, ProductoEntity .class, UserEntity.class, PrecioEntity.class, StockEntity.class,
        PedidoEntity.class, DetalleEntity.class, CategoriaEntity.class, ProductosImagenesEntity.class}, version = 4)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {


    public abstract ClientesDao clientDao();
    public abstract ProductoDao productoDao();
    public abstract UserDao userDao();
    public abstract PrecioDao precioDao();
    public  abstract StockDao stockDao();
    public abstract PedidoDao pedidoDao();
    public abstract DetalleDao detalleDao();
    public abstract CategoriaDao categoriaDao();
    public abstract ProductosImagenesDao imagenesDao();
    public static AppDatabase INSTANCE;

   public  static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "TeVendo_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
       INSTANCE.clearAllTables();
        INSTANCE = null;
    }
}