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

public class SecondActivity extends AppCompatActivity {
    private EditText CourseTitleSeconed;
    private EditText CourseCategorySeconed;
    private EditText NoOfLecturesSeconed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setUpViews();



    }

    private void setUpViews() {
        CourseTitleSeconed =  findViewById(R.id.CourseTitleSeconed);
        CourseCategorySeconed = findViewById(R.id.CourseCategorySeconed);
        NoOfLecturesSeconed = findViewById(R.id.NoOfLecturesSeconed);
    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        String title = CourseTitleSeconed.getText().toString();
        String category = CourseCategorySeconed.getText().toString();
        String pages = NoOfLecturesSeconed.getText().toString();

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

            Toast.makeText(SecondActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAddOnClick(View view) {

                final String Name=CourseTitleSeconed.getText().toString();
                final String word=CourseCategorySeconed.getText().toString();
                if(Name.length()==0)
                {
                    CourseTitleSeconed.requestFocus();
                    CourseTitleSeconed.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!Name.matches("[a-zA-Z ]+"))
                {
                    CourseTitleSeconed.requestFocus();
                    CourseTitleSeconed.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }
                else if(word.length()==0)
                {
                    CourseCategorySeconed.requestFocus();
                    CourseCategorySeconed.setError("FIELD CANNOT BE EMPTY");
                }
                else
                {
                    Toast.makeText(SecondActivity.this,"Validation Successful",Toast.LENGTH_LONG).show();
                }




        String restUrl = "http://192.168.1.91:80/rest/addbook.php";
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
