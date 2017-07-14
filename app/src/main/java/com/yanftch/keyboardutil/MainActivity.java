package com.yanftch.keyboardutil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yanftch.keyboardutil.keyboard.KeyBoardFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick2Withdraw(View view) {
        KeyBoardFragment keyBoardFragment = new KeyBoardFragment(new KeyBoardFragment.DialogClickListener() {
            @Override
            public void onCancleClick() {
                Toast.makeText(MainActivity.this, "点击了x", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPwdCompleteListener(String pwd) {
                Toast.makeText(MainActivity.this, "callback   for     password == " + pwd, Toast.LENGTH_SHORT).show();
            }
        });
        keyBoardFragment.show(getFragmentManager(), "keyboard");
    }
}
