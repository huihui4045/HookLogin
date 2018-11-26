package com.alizhezi.hooklogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText mEditUser;
    private EditText mEditPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditUser = ((EditText) findViewById(R.id.edit_user));
        mEditPwd = ((EditText) findViewById(R.id.edit_pwd));


    }

    @Override
    public void onClick(View v) {

    }
}
