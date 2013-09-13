package org.gdgrennes.android.bootcamp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;


public class HelloActivity extends Activity {

    TextView txtHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        txtHello = (TextView) findViewById(R.id.textHello);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("message");

        txtHello.setText(name);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hello, menu);
        return true;
    }


    
}
