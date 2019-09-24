package com.example.firstsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText edtName,edtSurname,edtMarks,edtID;
    Button btnSubmit,btnViewAll,btnUpdate,btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        edtName = (EditText) findViewById(R.id.txt_name);
        edtSurname = (EditText) findViewById(R.id.txt_surname);
        edtMarks = (EditText) findViewById(R.id.txt_marks);
        btnSubmit = (Button) findViewById(R.id.submit);
        btnViewAll = (Button) findViewById(R.id.view_all);
        btnUpdate = (Button) findViewById(R.id.update);
        edtID = (EditText) findViewById(R.id.id);
        btnDelete = (Button)  findViewById(R.id.delete);
        addData();
        viewAll();
        updateData();
        deleteData();
    }

    public void addData()
    {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = databaseHelper.insertData(edtName.getText().toString(),edtSurname.getText().toString(),edtMarks.getText().toString());

                if (isInserted == true)
                    Toast.makeText(MainActivity.this, "Inserted successfully!!!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Failed!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void viewAll()
    {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = databaseHelper.getAllData();
                if (res.getCount() == 0)
                    //Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    showMsg("Error","Nothing to Show");
                else
                {
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext())
                    {
                        buffer.append("Id: "+res.getString(0)+"\n");
                        buffer.append("Name: "+res.getString(1)+"\n");
                        buffer.append("Surname: "+res.getString(2)+"\n");
                        buffer.append("Marks: "+res.getString(3)+"\n\n");
                    }
                    showMsg("Data",buffer.toString());
                }
            }
        });
    }

    public void updateData()
    {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = databaseHelper.updateData(edtID.getText().toString(),
                        edtName.getText().toString(),edtSurname.getText().toString(),edtMarks.getText().toString());
                if (isUpdated == true)
                    Toast.makeText(MainActivity.this, "Updated successfully!!!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Failed!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteData()
    {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = databaseHelper.deleteData(edtID.getText().toString());
                if (deletedRows >0 )
                    Toast.makeText(MainActivity.this, "Deleted successfully!!!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Failed!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showMsg(String title, String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }
}
