package com.example.dblocal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Student {

    private int studentID;
    private String studentFname;
    private String studentLname;
    private String studentEmail;
    private int studentAge;
    private int studentPhone;
    private Bitmap StudentPic;


    public Student() {
    }
    public Student(String studentFname, String studentLname, String studentEmail, int studentAge, int studentPhone, Bitmap studentPic) {
        this.studentID = studentID;
        this.studentFname = studentFname;
        this.studentLname = studentLname;
        this.studentEmail = studentEmail;
        this.studentAge = studentAge;
        this.studentPhone = studentPhone;
        StudentPic = studentPic;
    }

    public Student(int studentID, String studentFname, String studentLname, String studentEmail, int studentAge, int studentPhone, Bitmap studentPic) {
        this.studentID = studentID;
        this.studentFname = studentFname;
        this.studentLname = studentLname;
        this.studentEmail = studentEmail;
        this.studentAge = studentAge;
        this.studentPhone = studentPhone;
        StudentPic = studentPic;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getStudentFname() {
        return studentFname;
    }

    public String getStudentLname() {
        return studentLname;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public int getStudentPhone() {
        return studentPhone;
    }

    public Bitmap getStudentPic() {
        return StudentPic;
    }


    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setStudentFname(String studentFname) {
        this.studentFname = studentFname;
    }

    public void setStudentLname(String studentLname) {
        this.studentLname = studentLname;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    public void setStudentPhone(int studentPhone) {
        this.studentPhone = studentPhone;
    }

    public void setStudentPic(Bitmap studentPic) {
        StudentPic = studentPic;
    }


}
