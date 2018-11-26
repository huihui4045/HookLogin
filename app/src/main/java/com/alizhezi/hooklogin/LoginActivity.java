package com.alizhezi.hooklogin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText mEditUser;
    private EditText mEditPwd;
    private SharedPreferences sharedPreferences;

    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditUser = ((EditText) findViewById(R.id.edit_user));
        mEditPwd = ((EditText) findViewById(R.id.edit_pwd));

        className = getIntent().getStringExtra("extraIntent");


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.btn_login:


                login();

                break;
        }


    }

    private void login() {

        String userName=mEditUser.getText().toString().trim();
        String userPwd=mEditPwd.getText().toString().trim();


        if ("gavin".equals(userName)&& "123456".equals(userPwd)){


             sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);


            SharedPreferences.Editor edit = sharedPreferences.edit();

            edit.putString("userName",userName);
            edit.putString("userPwd",userPwd);
            edit.putBoolean("isLogin",true);

            edit.commit();

            if (className!=null&& !"".equals(className)){

                Intent intent=new Intent();
                intent.setComponent(new ComponentName(this,className));
                startActivity(intent);
                finish();
            }

        }else {


            SharedPreferences.Editor edit = sharedPreferences.edit();

            edit.putBoolean("isLogin",false);

            edit.commit();
        }
    }
}
