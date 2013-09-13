package org.gdgrennes.android.bootcamp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends Activity {

    private static String myUrl = "http://lostinbrittany.org/java/AndroidBootcampServer/rest/helloservice/echo";
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
                if (!haveConnection()) {
                    Toast.makeText(v.getContext(), R.string.msg_no_network,Toast.LENGTH_LONG).show();
                } else {
                  new HelloTask().execute(name);
                }
            }
        });
    }

    private class HelloTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            Log.d("org.gdgrennes.android.bootcamp", "doInBackground() begins");

            String result = connect(strings[0]);
            Log.d("org.gdgrennes.android.bootcamp", "doInBackground() ends - Result="+result);
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.d("org.gdgrennes.android.bootcamp", "onPostExecute() - Result="+result);
            Intent helloIntent = new Intent("org.gdgrennes.bootcamp.android.HELLO");
            helloIntent.putExtra("message", result);

            startActivity(helloIntent);
        }

    }

    private boolean haveConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private String connect(String name)  {

        // Un stream pour récevoir la réponse
        InputStream is = null;

        try {
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

            URL url = new URL(myUrl+"?echo="+name);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("org.gdgrennes.android.bootcamp", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (Exception e) {
            Log.e("org.gdgrennes.android.bootcamp", e.getMessage());
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e("org.gdgrennes.android.bootcamp", e.getMessage());
                }
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }


}
