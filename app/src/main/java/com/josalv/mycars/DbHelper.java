package com.josalv.mycars;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Fachada de la base de datos. Permite obtener los coches, crear nuevos, o borrarlos.
 */
public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){
        super(context, CarContract.DB_NAME, null, CarContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s (%s text primary key, %s text, %s text, %s text, %s text, %s text)",
                CarContract.TABLE,
                CarContract.Column.ID,
                CarContract.Column.MARCA,
                CarContract.Column.MODELO,
                CarContract.Column.TIPO,
                CarContract.Column.COLOR,
                CarContract.Column.DESCRIPCION);



        Log.d(TAG, "onCreate con SQL: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + CarContract.TABLE);
        onCreate(db);
        Log.d(TAG, "onUpgrade");
    }

    public static void saveDb(SQLiteDatabase db, Car car) {
        //INSERTAR BD
        String sql = "INSERT INTO car (_id, MARCA, MODELO, TIPO, COLOR, DESCRIPCION) VALUES " +
                "('"+car.getId()+ "','" + car.getMarca() + "','" + car.getModelo() + "','"+car.getTipo()+"','"+car.getColor()+"','"+car.getDescripcion()+"') ";
        db.execSQL(sql);
    }


    /**
     * Devuelve un arrayList con los coches que hay en la base de datos
     * @param db base de datos
     * @return ArrayList con el contenido de coches
     */
    public static ArrayList<Car> getAllCars(SQLiteDatabase db) {


        Cursor cursor =  db.rawQuery("SELECT * FROM car",null );
        ArrayList<Car> cars = new ArrayList();

        while(cursor.moveToNext()) {
            cars.add(new Car(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)));
        }
        return cars;
    }


    /**
     * Devuelve el coche que esté en una determinada DB con un determinado ID.
     * @param db
     * @param id
     * @return Car
     */
    public static Car findByID(SQLiteDatabase db, String id){
        String churroSQL = "SELECT * FROM car c WHERE c._id == '"+ id +"'";
        Cursor cursor =  db.rawQuery(churroSQL,null );
        Car car = null;

        if (cursor.moveToFirst()) {

            car = new Car(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5));
            //saveDb(db,car);
        }
        return car;
    }
    /**
     * Elimina el coche que esté en una determinada DB con un determinado ID.
     * @param db
     * @param id
     * @return Car
     */
    public static void deleteByID(SQLiteDatabase db, String id){
        String sql = "DELETE FROM car WHERE car._id == '"+ id +"'";

        db.execSQL(sql);
    }






}
