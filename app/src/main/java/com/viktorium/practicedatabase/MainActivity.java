package com.viktorium.practicedatabase;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText etName,etSurname,etMarks,etId;
    Button btnAddData,btnViewAll,btnUpdate,btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);

        etId=(EditText)findViewById(R.id.etId);
        etName=(EditText)findViewById(R.id.etName);
        etSurname=(EditText)findViewById(R.id.etSurname);
        etMarks=(EditText)findViewById(R.id.etMarks);
        btnAddData=(Button)findViewById(R.id.btnAddData);
        btnViewAll=(Button)findViewById(R.id.btnViewAll);
        btnUpdate=(Button)findViewById(R.id.btnUpdate);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        AddData();
        ViewAll();
        updateData();
        deleData();

    }
    public  void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     boolean isInserted=myDb.insertData(etName.getText().toString(),
                             etSurname.getText().toString(),
                             etMarks.getText().toString());
                        if (isInserted)
                            Toast.makeText(MainActivity.this,"Data inserted",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not inserted",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    public void ViewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res =myDb.getAllData();
                        if (res.getCount()==0){
                            //Show message
                            showMessage("Error","Nothing found");
                            return;
                        }
                        StringBuilder buffer=new StringBuilder();
                        while (res.moveToNext()){
                            //old format code buffer.append("Marks:"+res.getString(3)+"\n\n");
                            buffer.append("Id:").append(res.getString(0)).append("\n");
                            buffer.append("Name:").append(res.getString(1)).append("\n");
                            buffer.append("Surname:").append(res.getString(2)).append("\n");
                            buffer.append("Marks:").append(res.getString(3)).append("\n\n");
                        }
                        //Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void updateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate=myDb.updateData(etId.getText().toString(),etName.getText().toString(),
                                etSurname.getText().toString(),
                                etMarks.getText().toString());
                        if (isUpdate)
                            Toast.makeText(MainActivity.this,"Data updated",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not updated",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    public void deleData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(etId.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not deleted",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
