package com.example.a123003sqlapp;
/**
 * A SQLiteDBHandler to execute the queries performed in MainActivity
 * Sources: https://www.geeksforgeeks.org/how-to-create-and-add-data-to-sqlite-database-in-android/?ref=lbp
 * @author 123003
 * @version 05/14/22
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class SQLiteDBHandler extends SQLiteOpenHelper {


    private static final String DB_NAME = "donations";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "mytable";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String EMAIL_COL = "email";
    private static final String DONATION_COL = "donations";

    public SQLiteDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + DONATION_COL + " TEXT)";

        db.execSQL(query);
    }

    //DELETE

    /**
     * Delete query
     * @param name
     * @return count
     */
    public int deletes(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(TABLE_NAME, "name=?", new String[]{name});

        db.close();
        return count;
    }

    /**
     * Insert query
     * @param name, email, donation
     * @return true
     */
    //INSERT
    public boolean inserts(String name, String email, String donation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, name);
        values.put(EMAIL_COL, email);
        values.put(DONATION_COL, donation);

        // select with person name
        Cursor nameSearch = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE name=\"" + name + "\"", null);

        // moving our cursor to first position.
        if (nameSearch.moveToFirst()) {
            return false;
        }

        db.insert(TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public String[] selects (String name2) {
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // select with person name
        Cursor nameSearch = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE name=\"" + name2 + "\"", null);

        // moving our cursor to first position.
        if (nameSearch.moveToFirst()) {
            return new String[]{nameSearch.getString(1), nameSearch.getString(2), nameSearch.getString(3)};
        }

        return new String[0];
    }
    public int updates(String name, String email, String animal) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, name);
        values.put(EMAIL_COL, email);
        values.put(DONATION_COL, animal);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        int val = db.update(TABLE_NAME, values, "name=?", new String[]{name});
        db.close();

        return val;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long getNumElements() {
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

}