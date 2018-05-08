package com.example.abhishekkoranne.engineersbook.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.abhishekkoranne.engineersbook.R;

public class RegisterCompanyActivity extends AppCompatActivity
{
    EditText et_company_id, et_company_name, et_address, et_contact_number, et_hr_name, et_hr_contact_number;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);

        btn_register=(Button)findViewById(R.id.btn_register);

        et_company_id=(EditText)findViewById(R.id.et_company_id);
        et_company_name=(EditText)findViewById(R.id.et_company_name);
        et_address=(EditText)findViewById(R.id.et_address);
        et_contact_number=(EditText)findViewById(R.id.et_contact_number);
        et_hr_name=(EditText)findViewById(R.id.et_hr_name);
        et_hr_contact_number=(EditText)findViewById(R.id.et_hr_contact_number);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

