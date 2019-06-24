package com.example.dblocal;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Student> listStudent;


    public StudentListAdapter(Context mContext, ArrayList<Student> listStudent) {
        this.mContext = mContext;
        this.listStudent = listStudent;
    }

    @Override
    public int getCount() {
        return this.listStudent.size();
    }

    @Override
    public Object getItem(int position) {
        return listStudent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.display_student_row, null);

            TextView txtFullName = convertView.findViewById(R.id.txtFullName);
            TextView txtEmail = convertView.findViewById(R.id.txtEmail);
            TextView txtAge = convertView.findViewById(R.id.txtAge);
            ImageView mImgView = convertView.findViewById(R.id.imgView);

            Student student = listStudent.get(position);
            txtFullName.setText(student.getStudentFname()+" "+student.getStudentLname());
            txtEmail.setText(student.getStudentEmail());
            txtAge.setText(String.valueOf(student.getStudentAge()));
            mImgView.setImageBitmap(student.getStudentPic());

        return convertView;
    }
}
