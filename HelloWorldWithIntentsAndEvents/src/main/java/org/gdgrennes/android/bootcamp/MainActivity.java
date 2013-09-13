package org.gdgrennes.android.bootcamp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

public class MainActivity extends Activity {

    private EditText txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (EditText) findViewById(R.id.editTextName);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Button b = (Button)findViewById(R.id.buttonOK);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                new HelloTask().execute(name);
            }
        });
    }

    private class HelloTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            Log.d("AsyncTask", "doInBackground() begins");

            //On va faire que la tâche attende un p'tit peu...
            int k = 1;
            for (int i=0;i<5000;i++) {
                for (int j=1;j<=5000;j++) {
                    k=i/j*i/j*i;
                }
            }
            Log.d("AsyncTask", "doInBackground() ends - Result="+strings[0]);
            return strings[0];
        }
        @Override
        protected void onPostExecute(String result) {
            Log.d("AsyncTask", "onPostExecute() - Result="+result);
            Intent helloIntent = new Intent("org.gdgrennes.bootcamp.android.HELLO");
            helloIntent.putExtra("name", result);

            startActivity(helloIntent);
        }

    }




}
