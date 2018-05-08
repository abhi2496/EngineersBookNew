package com.example.abhishekkoranne.engineersbook.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.abhishekkoranne.engineersbook.R;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button btn_student = (Button) findViewById(R.id.btn_student);
        Button btn_faculty = (Button) findViewById(R.id.btn_faculty);
        Button btn_hod = (Button) findViewById(R.id.btn_HOD);
        Button btn_principal = (Button) findViewById(R.id.btn_Principal);
        Button btn_chancellor = (Button) findViewById(R.id.btn_Chancellor);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        btn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(RegistrationActivity.this,StudentRegistrationActivity.class));
            }
        });

        btn_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,FacultyRegistrationActivity.class));
            }
        });

        btn_hod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,HODRegistrationActivity.class));
            }
        });

        btn_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,PrincipalRegistrationActivity.class));
            }
        });

        btn_chancellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,ChancellorRegistrationActivity.class));
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();

                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
