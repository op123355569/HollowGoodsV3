package com.hg.hollowgoods.UI.Activity.Example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hg.hollowgoods.R;

/**
 * 探探索引示例界面
 */
public class Ex13Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_13);

        findViewById(R.id.btnSwipeCard).setOnClickListener(view -> startActivity(new Intent(Ex13Activity.this, Ex13_1Activity.class)));
        findViewById(R.id.btnKing).setOnClickListener(view -> startActivity(new Intent(Ex13Activity.this, Ex13_2Activity.class)));
    }
}
