package com.dragonregnan.sistemasdinamicos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.sql.SQLException;

/**
 * Created by josh on 09/07/2015.
 */
public class DataBaseSource {

    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    public DataBaseSource(Context context) {
        this.context = context;
    }

    public DataBaseSource open() throws SQLException {
        dbHelper = DataBaseHelper.getInstance(context);//new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(String table, ContentValues cValues){
        long isInserted = -1;
        try {
            open();
            database.beginTransaction();
            isInserted = database.insert(table, null, cValues);
        } catch(Exception e) {
        }finally{
            database.setTransactionSuccessful();
            database.endTransaction();
            close();
        }
        return isInserted;
    }

    public void insertSentence(String sentence) {
        try {
            open();
            database.beginTransaction();
            //database.execSQL(sentence);
            String[] sentences = sentence.split(";");
            for (String sen : sentences) {
                database.execSQL(sen);
            }
        } catch (Exception e) {
        } finally {
            database.setTransactionSuccessful();
            database.endTransaction();
            close();
        }
    }

    public void update(String table, ContentValues cValues, String condition){
        try {
            open();
            database.beginTransaction();
            database.update(table, cValues, condition, null);
        } catch(Exception e) {
        }finally{
            database.setTransactionSuccessful();
            database.endTransaction();
            close();
        }
    }

    public int delete(String table,  String condition){
        try{
            open();
            database.delete(table, condition, null);
            close();
            return 1;
        }catch(Exception e){
            close();
            return -1;
        }
    }

    public int deleteAll(String table) {
        try{
            open();
            database.delete(table, null, null);
            close();
            return 1;
        }catch(Exception e){
            close();
            return -1;
        }
    }

    public void deleteDatabase(Context context){
        context.deleteDatabase(DataBaseHelper.dataBaseName);
    }

    public Cursor getData(String tables, String[] columns, String conditional){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(tables);
        return queryBuilder.query(database, columns , conditional, null, null, null, null);
    }

    public Cursor getDataWithFilter(String tables, String[] columns, String conditional, String orderBy){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(tables);
        return queryBuilder.query(database, columns , conditional, null, null, null, orderBy);
    }

    public Cursor getData(String tables, String[] columns, String conditional, String groupBy){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(tables);
        return queryBuilder.query(database, columns , conditional, null, groupBy, null, null);
    }

    public Cursor getData(String tables, String[] columns, String conditional, String groupBy ,String orderBy){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(tables);
        return queryBuilder.query(database, columns , conditional, null, groupBy, null, orderBy);
    }

    public Cursor getDataRaw(String condition , String[] args){
        return database.rawQuery(condition, args);
    }
}
