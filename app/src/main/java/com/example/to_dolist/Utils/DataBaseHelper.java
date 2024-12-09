package com.example.to_dolist.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.to_dolist.Model.ToDoModel;
import com.example.to_dolist.Model.UserDataModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TASK_DATABASE";
    private static final String TABLE_NAME = "TASK_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TITLE";
    private static final String COL_3 = "CATEGORY";
    private static final String COL_4 = "DESCRIPTION";
    private static final String COL_5 = "LOCATION";
    private static final String COL_6 = "STATUS";
    private static final String COL_7 = "TARGET_DATE";



    public DataBaseHelper( Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, CATEGORY TEXT, DESCRIPTION TEXT, LOCATION TEXT, STATUS INTEGER, TARGET_DATE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
         sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
         onCreate(sqLiteDatabase);
    }



    public void insertTask(ToDoModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, model.getTitle());
        contentValues.put(COL_3, model.getCategory());
        contentValues.put(COL_4, model.getDescription());
        contentValues.put(COL_5, model.getLocation());
        contentValues.put(COL_6, 0);
        contentValues.put(COL_7, model.getTargetDate());

        db.insert(TABLE_NAME,null,contentValues);
    }
    public void updateTitle(int id, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,title);

        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{String.valueOf(id)});
    }

    public void updateDescription(int id, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4,description);

        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{String.valueOf(id)});
    }

    public void updateCategory(int id, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3,category);

        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{String.valueOf(id)});
    }

    public void updateLocation(int id, String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5,location);

        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{String.valueOf(id)});
    }
    public void updateStatus(int id, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6,status);
        db.update(TABLE_NAME, contentValues, "ID=?",new String[]{String.valueOf(id)});
    }

    public void updateTargetDate(int id, String targetDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_7,targetDate);
        db.update(TABLE_NAME, contentValues, "ID=?",new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        List<ToDoModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            cursor = db.query(TABLE_NAME,null,null,null,null,null,null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        ToDoModel toDoModel = new ToDoModel();
                        toDoModel.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        toDoModel.setTitle(cursor.getString(cursor.getColumnIndex(COL_2)));
                        toDoModel.setCategory(cursor.getString(cursor.getColumnIndex(COL_3)));
                        toDoModel.setDescription(cursor.getString(cursor.getColumnIndex(COL_4)));
                        toDoModel.setLocation(cursor.getString(cursor.getColumnIndex(COL_5)));
                        toDoModel.setStatus(cursor.getInt(cursor.getColumnIndex(COL_6)));
                        toDoModel.setTargetDate(cursor.getString(cursor.getColumnIndex(COL_7)));
                        modelList.add(toDoModel);
                    }while(cursor.moveToNext());
                }
            }
        }finally{
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }

    public List<ToDoModel> getTasksByDate(String date) {
        // Log the date being used for the query for debugging
        Log.d("Database Query", "Querying tasks for date: " + date);
        List<ToDoModel> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_7 + " = ?", new String[]{date});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ToDoModel task = new ToDoModel();
                task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_1)));
                task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_2)));
                task.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COL_3)));
                task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_4)));
                task.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COL_5)));
                task.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(COL_6)));
                task.setTargetDate(cursor.getString(cursor.getColumnIndexOrThrow(COL_7)));
                taskList.add(task);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return taskList;
    }
}

















