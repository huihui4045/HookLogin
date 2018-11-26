package com.alizhezi.hooklogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {

        Intent intent=null;

        switch (v.getId()){

            case R.id.btn_login:

                 intent=new Intent(this,LoginActivity.class);

                break;

            case R.id.btn_news:

                intent=new Intent(this,NewsActivity.class);

                break;

            case R.id.btn_pic:

                intent=new Intent(this,PicActivity.class);

                break;

            case R.id.btn_video:

                intent=new Intent(this,VideoActivity.class);

                break;

        }

        startActivity(intent);

    }
}
