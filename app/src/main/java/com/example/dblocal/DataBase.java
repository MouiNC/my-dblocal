package com.example.dblocal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "studentDB.db";
    private static final String TABLE_NAME = "Students";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_FNAME = "FName";
    private static final String COLUMN_LNAME = "LName";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_AGE = "Age";
    private static final String COLUMN_PHONE = "PHONE";
    private static final String COLUMN_PIC = "Picture";

    DataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_STUDENTS_TABLE = " CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FNAME + " TEXT, "+ COLUMN_LNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, " + COLUMN_AGE + " INTEGER, "
                + COLUMN_PHONE + " INTEGER, " + COLUMN_PIC +" blob)";

        db.execSQL(CREATE_STUDENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    ArrayList<Student> getAllData(){


        String query = "SELECT * FROM " + TABLE_NAME;
        ArrayList<Student> listStudent = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String Fname = cursor.getString(1);
            String Lname = cursor.getString(2);
            String email = cursor.getString(3);
            int age = cursor.getInt(4);
            int phone = cursor.getInt(5);
            byte[] blob = cursor.getBlob(6);
            Student student = new Student(id, Fname, Lname, email, age, phone, Utility.getPhoto(blob) );
            listStudent.add(student);
        }
        return listStudent;
    }


    boolean addData(Student student ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FNAME, student.getStudentFname());
        contentValues.put(COLUMN_LNAME, student.getStudentLname());
        contentValues.put(COLUMN_EMAIL, student.getStudentEmail());
        contentValues.put(COLUMN_AGE, student.getStudentAge());
        contentValues.put(COLUMN_PHONE, student.getStudentPhone());
        contentValues.put(COLUMN_PIC, Utility.getBytes(student.getStudentPic()));

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    boolean deleteData(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_PHONE + " = ? AND " + COLUMN_EMAIL + " = ?";
        long result = db.delete(TABLE_NAME,where,
                new String[]{String.valueOf(student.getStudentPhone()), student.getStudentEmail()});

        //if date as deleted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    boolean updateData(Student newStudent, Student oldStudent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues upadatevalue = new ContentValues();
        upadatevalue.put(COLUMN_FNAME,newStudent.getStudentFname());
        upadatevalue.put(COLUMN_LNAME,newStudent.getStudentLname());
        upadatevalue.put(COLUMN_EMAIL,newStudent.getStudentEmail());
        upadatevalue.put(COLUMN_AGE,newStudent.getStudentAge());
        upadatevalue.put(COLUMN_PHONE,newStudent.getStudentPhone());
        upadatevalue.put(COLUMN_PIC, Utility.getBytes(newStudent.getStudentPic()));

        String where = COLUMN_FNAME + " = ? AND " + COLUMN_LNAME + " = ? AND " + COLUMN_EMAIL + " = ?" ;
        long result = db.update(TABLE_NAME,upadatevalue,where,
                new String[]{oldStudent.getStudentFname(),oldStudent.getStudentLname(), oldStudent.getStudentEmail()});

        //if date as updated incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }



    boolean checkAlreadyExist(String email, String phone)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+ COLUMN_FNAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PHONE + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{email, phone});
        if (cursor.getCount() > 0)
        {
            return false;
        }
        else
            return true;
   }


}
