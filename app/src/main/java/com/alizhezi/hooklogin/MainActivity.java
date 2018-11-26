package com.alizhezi.hooklogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
                startActivity(intent);

                break;

            case R.id.btn_news:

                intent=new Intent(this,NewsActivity.class);
                startActivity(intent);

                break;

            case R.id.btn_pic:

                intent=new Intent(this,PicActivity.class);
                startActivity(intent);

                break;

            case R.id.btn_video:

                intent=new Intent(this,VideoActivity.class);
                startActivity(intent);

                break;

            case R.id.btn_logout:

                SharedPreferences.Editor edit = this.getSharedPreferences("user",Context.MODE_PRIVATE).edit();

                edit.putBoolean("isLogin",false);

                edit.commit();


                break;

        }



    }
}
