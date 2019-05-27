package com.hala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hala.activity.LoginActivity;
import com.hala.dialog.CommonDialog;

public class MainActivity extends AppCompatActivity {

    private long oldTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    // Example of a call to a native method
    TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
      long newTime =  System.currentTimeMillis();
      if (newTime- oldTime <3000){
          finish();
      }else{
          new CommonDialog(this)
                  .setMsg(getString(R.string.want_to_log_out))
                  .show();
      }
      oldTime =newTime;

    }

}


