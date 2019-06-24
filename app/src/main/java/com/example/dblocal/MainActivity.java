package com.example.dblocal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.app.ActivityCompat;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity {

    EditText edtTxtFname, edtTxtAge, edtTxtLname, edtTxtEmail, edtTxtPhone;
    Button btnAdd, btnUpdate, btnDelete, btnDisplay;
    ImageView mImgView;

    private static String TAG = "MainActivity";
    final int REQUEST_CODE_GALLERY = 999;
    private int index;
    private int getTag ;

    DataBase myDataBadse;
    ArrayList<Student> studentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTxtFname = findViewById(R.id.edtTxtFname);
        edtTxtLname = findViewById(R.id.edtTxtLname);
        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtAge = findViewById(R.id.edtTxtAge);
        edtTxtPhone = findViewById(R.id.edtTxtPhone);
        mImgView = findViewById(R.id.imgView);
        btnAdd = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelet);
        btnDisplay = findViewById(R.id.btnDisplay);
        studentList = new ArrayList<>();
        myDataBadse = new DataBase(this);
        studentList = myDataBadse.getAllData();

        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        Intent mIntent = getIntent();
        getTag = mIntent.getIntExtra("Tag", 0);
        if(getTag == 999) {

            index = mIntent.getIntExtra("index", 1);
            setEdtTxt(index);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);

        }


        mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY
                );
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = edtTxtFname.getText().toString();
                String lname = edtTxtLname.getText().toString();
                String email = edtTxtEmail.getText().toString();
                String age = edtTxtAge.getText().toString();
                String phone = edtTxtPhone.getText().toString();

                Drawable drawable = mImgView.getDrawable();
                if (check(fname, lname, email, age, phone)) {
                    if (Integer.parseInt(age) < 200) {
                        if(myDataBadse.checkAlreadyExist(email, phone)){
                                Bitmap bitmap = drawableToBitmap(drawable);
                                AddData(new Student(fname, lname, email, Integer.parseInt(age), Integer.parseInt(phone), bitmap));
                                edtTxtFname.setText("");
                                edtTxtLname.setText("");
                                edtTxtEmail.setText("");
                                edtTxtAge.setText("");
                                edtTxtPhone.setText("");
                                mImgView.setImageDrawable(getResources().getDrawable(R.drawable.add_photo));
                                startActivity(new Intent(MainActivity.this,DisplayActivity.class));
                                finish();
                        }else {
                            Toast.makeText(MainActivity.this, "it already exists", Toast.LENGTH_SHORT).show();
                        }

                    } else
                        Toast.makeText(MainActivity.this, "Rak kbir", Toast.LENGTH_SHORT).show();
                }


            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = edtTxtFname.getText().toString();
                String lname = edtTxtLname.getText().toString();
                String email = edtTxtEmail.getText().toString();
                String age = edtTxtAge.getText().toString();
                String phone = edtTxtPhone.getText().toString();
                BitmapDrawable drawable = (BitmapDrawable) mImgView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                if(check(fname,lname,email, age, phone)){
                    if(!myDataBadse.checkAlreadyExist(email, phone)){
                        deleteData(new Student(fname,lname,email,Integer.parseInt(age),Integer.parseInt(phone),bitmap));
                        startActivity(new Intent(MainActivity.this,DisplayActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "This item is not in the database", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nfname = edtTxtFname.getText().toString();
                String nlname = edtTxtLname.getText().toString();
                String nemail = edtTxtEmail.getText().toString();
                String nage = edtTxtAge.getText().toString();
                String nphone = edtTxtPhone.getText().toString();
                BitmapDrawable drawable = (BitmapDrawable) mImgView.getDrawable();
                Bitmap nbitmap = drawable.getBitmap();


                String ofname = studentList.get(index).getStudentFname();
                String olname = studentList.get(index).getStudentLname();
                String oemail = studentList.get(index).getStudentEmail();
                int oAge = studentList.get(index).getStudentAge();
                int ophone = studentList.get(index).getStudentPhone();
                Bitmap obitmap = studentList.get(index).getStudentPic();

                if(check(nfname,nlname,nemail,nage,nphone)){
                    if (Integer.parseInt(nage) < 200) {
                        updateData(
                                new Student(nfname,nlname,nemail,Integer.parseInt(nage),Integer.parseInt(nphone),nbitmap),
                                new Student(ofname,olname,oemail,oAge,ophone,obitmap));
                        startActivity(new Intent(MainActivity.this,DisplayActivity.class));
                        finish();
                    }else
                        Toast.makeText(MainActivity.this, "Rak kbir!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DisplayActivity.class));
                finish();
            }
        });



    }



    private void AddData(Student student) {
        boolean insert = myDataBadse.addData(student);

        if (insert) {
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData(Student student){
        boolean delete = myDataBadse.deleteData(student);

        if (delete) {
            Toast.makeText(this, "Data Successfully deleted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData(Student newS, Student oldS){
        boolean update = myDataBadse.updateData(newS, oldS);

        if (update) {
            Toast.makeText(this, "Data Successfully updated!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean check(String fname, String lname, String email, String age, String phone) {
        boolean res = true;
        StringBuilder ch1 = new StringBuilder();
        StringBuilder ch2 = new StringBuilder();
        StringBuilder ch3 = new StringBuilder();
        StringBuilder ch4 = new StringBuilder();
        StringBuilder ch5 = new StringBuilder();

        if (!fname.isEmpty()) {
            if(!lname.isEmpty()) {
                if(!email.isEmpty()) {
                    if (!age.isEmpty()) {
                        if (!phone.isEmpty()) {
                            for (char c : fname.toCharArray()) {
                                if (Character.isLetter(c) || Character.isSpaceChar(c)) {
                                    ch1.append(c);
                                } else {
                                    Toast.makeText(MainActivity.this, "Enter your First name correctly", Toast.LENGTH_LONG).show();
                                    ch1.setLength(0);
                                    res = false;
                                    break;
                                }
                            }
                            for (char c : lname.toCharArray()) {
                                if (Character.isLetter(c) || Character.isSpaceChar(c)) {
                                    ch2.append(c);
                                } else {
                                    Toast.makeText(MainActivity.this, "Enter your Last name correctly", Toast.LENGTH_LONG).show();
                                    ch2.setLength(0);
                                    res = false;
                                    break;
                                }
                            }
                            for (char c : age.toCharArray()) {
                                if (Character.isDigit(c)) {
                                    ch4.append(c);
                                } else {
                                    Toast.makeText(MainActivity.this, "Enter your age correctly", Toast.LENGTH_LONG).show();
                                    ch4.setLength(0);
                                    res = false;
                                    break;
                                }
                            }
                            for (char c : phone.toCharArray()) {
                                if (Character.isDigit(c)) {
                                    ch5.append(c);
                                } else {
                                    Toast.makeText(MainActivity.this, "Enter your number phone correctly", Toast.LENGTH_LONG).show();
                                    ch5.setLength(0);
                                    res = false;
                                    break;
                                }
                            }

                            if (!ch1.toString().isEmpty() && !ch2.toString().isEmpty() && !ch4.toString().isEmpty() && !ch5.toString().isEmpty()) {

                                String regExpn =
                                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

                                CharSequence inputStr = email;

                                Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
                                Matcher matcher = pattern.matcher(inputStr);

                                if(matcher.matches())
                                    res = true;
                                else {
                                    Toast.makeText(this, "Entre your email correctly", Toast.LENGTH_SHORT).show();
                                    res = false;
                                }
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "age chain is empty", Toast.LENGTH_SHORT).show();
                            res = false;
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "Phone chain is empty", Toast.LENGTH_SHORT).show();
                        res = false;
                    }
                }else
                {
                    Toast.makeText(MainActivity.this, "Email chain is empty", Toast.LENGTH_SHORT).show();
                    res = false;
                }
            }else {
                Toast.makeText(MainActivity.this, "Last name chain is empty", Toast.LENGTH_SHORT).show();
                res = false;
            }
        } else {
            Toast.makeText(MainActivity.this, "First name chain is empty", Toast.LENGTH_SHORT).show();
            res = false;
        }

        return res;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent GalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                GalleryIntent.setType("image/*");
                startActivityForResult(GalleryIntent, REQUEST_CODE_GALLERY);
            }else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK){
            assert data != null;
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                assert result != null;
                Uri resultUri = result.getUri();
                mImgView.setImageURI(resultUri);

            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception err = result.getError();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setEdtTxt(int i){
        edtTxtFname.setText(studentList.get(i).getStudentFname());
        edtTxtLname.setText(studentList.get(i).getStudentLname());
        edtTxtEmail.setText(studentList.get(i).getStudentEmail());
        edtTxtAge.setText(String.valueOf(studentList.get(i).getStudentAge()));
        edtTxtPhone.setText(String.valueOf(studentList.get(i).getStudentPhone()));
        mImgView.setImageBitmap(studentList.get(i).getStudentPic());

    }

    private static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }



}
