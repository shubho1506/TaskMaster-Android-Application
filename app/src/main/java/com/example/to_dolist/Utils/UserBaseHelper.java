package com.example.to_dolist.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.to_dolist.Model.UserDataModel;

public class UserBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "USER_DATABASE";
    private static final String  TABLE_NAME2 = "USER_DATA";

    private static final String  COL_1 = "USERNAME";

    private static final String  COL_2 = "PASSWORD";

    private static final String  COL_3 = "EMAIL";

    public UserBaseHelper( Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 +
                "(USERNAME TEXT PRIMARY KEY, PASSWORD TEXT, EMAIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2); // Users table
    }

    public boolean insertUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, username);
        contentValues.put(COL_2, password);
        contentValues.put(COL_3, email);

        long result = db.insert(TABLE_NAME2, null, contentValues);
        db.close();
        return result != -1; // Returns true if insertion is successful
    }

    public boolean insertUser(UserDataModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, model.getUsername());
        contentValues.put(COL_2, model.getPassword());
        contentValues.put(COL_3, model.getEmail());
        long isInserted = db.insert(TABLE_NAME2, null, contentValues);
        db.close();
        return isInserted != -1;
    }

    public boolean isUserExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+COL_1+" FROM " + TABLE_NAME2 + " WHERE "+COL_3+" = ?", new String[]{email});
        // Log the email being checked and the result of the existence check
        Log.d("isUserExists", "Checking if user exists with email: " + email + ", Exists: " );

        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return exists;
    }

    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+COL_1+" FROM " + TABLE_NAME2 + " WHERE "+COL_1+" = ? AND "+COL_2+" = ?", new String[]{username, password});
        boolean isValid = false;
        if (cursor != null) {
            isValid = cursor.getCount() > 0;
            cursor.close();
        }
        db.close();
        Log.d("ValidateUser", "Email: " + username + ", Password: " + password + ", IsValid: " + isValid);
        return isValid;
    }

}
