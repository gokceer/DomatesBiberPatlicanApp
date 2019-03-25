package com.example.user.recipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends DBobject {


    public static String DB_PATH;
    private static DatabaseHelper helperInstance;

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DomatesBiberPatlican.db";

    // Table Names
    private static final String TABLE_TARİF= "Tarif";
    private static final String TABLE_MALZEME = "Malzeme";
    private static final String TABLE_ORTAK = "Ortak";
    private static final String TABLE_FRIDGE = "Buzdolabi";

    // Common column names
    private static final String KEY_ID = "id";

    // Tarif Table - column names
    private static final String KEY_TARİF_ADİ = "tarif_adi";
    private static final String KEY_MALZEME_LİSTESİ = "malzeme_listesi";
    private static final String KEY_YAPİLİSİ = "yapilisi";
    private static final String KEY_RESİM = "resim";

    // Malzeme Table - column names
    private static final String KEY_MALZEME_ADİ = "malzeme_adi";

    // Ortak Table - column names
    private static final String KEY_TARİF_ID = "tarif_id";
    private static final String KEY_MALZEME_ID = "malzeme_id";
    //Buzdolabi
    public static final String COL_1 = "id";
    public static final String COL_2 = "adi";
    public static final String COL_3 = "skt";


    private SQLiteDatabase mDataBase;
    private boolean mNeedUpdate = false;

    public DatabaseHelper(Context context) {
        super(context);
    }


    /*********************TARİF METHODS************************/

    /**
     * getting all tarif
     */
    public List<Tarif> getAllTarif() {
        List<Tarif> tarifs = new ArrayList<Tarif>();
        String selectQuery = "SELECT  * FROM " + TABLE_TARİF;

        Log.e(LOG, selectQuery);

        Cursor c = this.getDbConnection().rawQuery(selectQuery,null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Tarif tm = new Tarif();
                tm.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                tm.setTarifAdi((c.getString(c.getColumnIndex(KEY_TARİF_ADİ))));
                tm.setMalzemeListesi(c.getString(c.getColumnIndex(KEY_MALZEME_LİSTESİ)));
                tm.setYapilisi(c.getString(c.getColumnIndex(KEY_YAPİLİSİ)));
                tm.setResim(c.getString(c.getColumnIndex(KEY_RESİM)));

                // adding to tarif list
                tarifs.add(tm);
            } while (c.moveToNext());
        }

        return tarifs;
    }

    /**
     * getting all tarifs under single malzeme
     */
    public List<Tarif> getAllTarifsByMalzeme(ArrayList<String> malzemeler) {
        List<Tarif> tarifs = new ArrayList<Tarif>();
        String selectQuery;
        String storeQuery = "";
        for (String malzeme : malzemeler) {

            selectQuery = " SELECT tt.id, tt.tarif_adi, tt.malzeme_listesi, tt.yapilisi, tt.resim FROM " + TABLE_TARİF + " tt, "
                    + TABLE_MALZEME + " tm, " + TABLE_ORTAK + " tort " + "WHERE tm."
                    + KEY_MALZEME_ADİ + " = '" + malzeme + "'" + " AND tm." + KEY_ID
                    + " = " + "tort." + KEY_MALZEME_ID + " AND tt." + KEY_ID + " = "
                    + "tort." + KEY_TARİF_ID;
            storeQuery = storeQuery + selectQuery + " INTERSECT";
        }
            selectQuery = storeQuery.substring(0, storeQuery.lastIndexOf(" "));
            Log.e(LOG, selectQuery);


            Cursor c = this.getDbConnection().rawQuery(selectQuery,null);

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Tarif tm = new Tarif();
                    tm.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                    tm.setTarifAdi((c.getString(c.getColumnIndex(KEY_TARİF_ADİ))));
                    tm.setMalzemeListesi(c.getString(c.getColumnIndex(KEY_MALZEME_LİSTESİ)));
                    tm.setYapilisi(c.getString(c.getColumnIndex(KEY_YAPİLİSİ)));
                    tm.setResim(c.getString(c.getColumnIndex(KEY_RESİM)));

                    // adding to tarif list
                    tarifs.add(tm);
                } while (c.moveToNext());
            }

        return tarifs;
    }

    /**
     * getting all malzemes
     */
    public List<Malzeme> getAllMalzemes() {
        List<Malzeme> malzemes = new ArrayList<Malzeme>();
        String selectQuery = "SELECT  * FROM " + TABLE_MALZEME;

        Log.e(LOG, selectQuery);

        Cursor c = this.getDbConnection().rawQuery(selectQuery,null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Malzeme t = new Malzeme();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setMalzemeAdi(c.getString(c.getColumnIndex(KEY_MALZEME_ADİ)));

                // adding to tags list
                malzemes.add(t);
            } while (c.moveToNext());
        }
        return malzemes;
    }


    /***************ORTAK TABLE METHODS******************/

    /**
     * Creating tarifmalzeme
     */

    public void seeOrtak(){

        String selectQuery = "SELECT  * FROM " + TABLE_ORTAK;
        Cursor c = this.getDbConnection().rawQuery(selectQuery,null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Log.d("ORTAK TABLO ID: ", String.valueOf(c.getInt(c.getColumnIndex(KEY_ID))));
                Log.d("ORTAK TABLO TARİF ID: ", c.getString(c.getColumnIndex(KEY_TARİF_ID)));
                Log.d("ORTAK TABLO MALZEME ID:", c.getString(c.getColumnIndex(KEY_MALZEME_ID)));
            } while (c.moveToNext());
        }
    }

    /************************BUZDOLABI TAKİBİ*************************/

    public boolean addData(String item,String item2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item);
        contentValues.put(COL_3, item2);

        Log.d(DATABASE_NAME, "addData: Adding " + item + " to " + TABLE_FRIDGE );

        long result = this.getDbConnection().insert(TABLE_FRIDGE, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(){
        String query = "SELECT * FROM " + TABLE_FRIDGE;
        Cursor data = this.getDbConnection().rawQuery(query, null);
        return data;
    }

    public void deleteData(String name)
    {
        String query = "DELETE FROM " + TABLE_FRIDGE + " WHERE " + COL_2 + " = '" + name + "'";
        // + " AND " + COL_3 + " = '" + date + "'";
        this.getDbConnection().execSQL(query);
    }

}


