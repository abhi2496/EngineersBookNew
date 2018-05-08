package com.example.abhishekkoranne.engineersbook.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.abhishekkoranne.engineersbook.R;

public class CompanyLoginActivity extends AppCompatActivity
{
    EditText et_cmp_id;
    Button btn_cmp_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);

        et_cmp_id=(EditText)findViewById(R.id.et_cmp_id);
        btn_cmp_login=(Button)findViewById(R.id.btn_cmp_login);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btn_cmp_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_cmp_id.getText().toString().equals("TCS24"))
                {
                    startActivity(new Intent(CompanyLoginActivity.this, CompanyHomeActivity.class));
                }
            }
        });
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        finish();
        /*if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
*/
        /*Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);/*//***Change Here***
         startActivity(intent);
         finish();*/
//        System.exit(0);
        /*new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();*/
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

    public void onRegisterClick(View v)
    {
        startActivity(new Intent(CompanyLoginActivity.this,RegisterCompanyActivity.class));
    }

    public void onlogin(View view) {
        startActivity(new Intent(CompanyLoginActivity.this, CompanyHomeActivity.class));

    }
}

