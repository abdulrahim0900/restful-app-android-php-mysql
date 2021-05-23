package edu.cs.birzeit.restapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ThirdActivity extends AppCompatActivity {
    private EditText CourseTitleThird;
    private EditText CourseCategoryThird;
    private EditText NoOfLecturesThird;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setUpViews();

    }

    private void setUpViews() {
        CourseTitleThird =  findViewById(R.id.CourseTitleThird);
        CourseCategoryThird = findViewById(R.id.CourseCategoryThird);
        NoOfLecturesThird = findViewById(R.id.NoOfLecturesThird);
    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        String title = CourseTitleThird.getText().toString();
        String category = CourseCategoryThird.getText().toString();
        String pages = NoOfLecturesThird.getText().toString();

        String data = URLEncoder.encode("title", "UTF-8")
                + "=" + URLEncoder.encode(title, "UTF-8");

        data += "&" + URLEncoder.encode("cat", "UTF-8") + "="
                + URLEncoder.encode(category, "UTF-8");

        data += "&" + URLEncoder.encode("pages", "UTF-8")
                + "=" + URLEncoder.encode(pages, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(restUrl);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        // Show response on activity
        return text;



    }


    private class SendPostRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return processRequest(urls[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(ThirdActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    public void ThirdbtnAddOnClick(View view) {
        String restUrl = "http://192.168.1.91:80/rest/updateBook.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            SendPostRequest runner = new SendPostRequest();
            runner.execute(restUrl);
        }
    }
}
