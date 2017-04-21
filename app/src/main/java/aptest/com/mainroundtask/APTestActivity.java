package aptest.com.mainroundtask;


import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;





public class APTestActivity extends AppCompatActivity {

    TextView t1;
    Button b1;
    RadioGroup radioGroup1;
    //ProgressDialog pd;

    private RadioButton radioButton;
    String loginValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aptest);

        t1 = (TextView) findViewById(R.id.tv_display);
        b1 = (Button) findViewById(R.id.button1);

        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = "";

                Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();

                try {
                    int selectId = radioGroup1.getCheckedRadioButtonId();

                    radioButton = (RadioButton) findViewById(selectId);

                    loginValue = URLEncoder.encode(radioButton.getText().toString(), "UTF-8");

                    Log.d("loginValue", loginValue);
                    HttpClient client = new DefaultHttpClient();
                    String URL = "https://triggers.losant.com/webhooks/Oamr6kp4Z3cYrk4rZnY5SlLlAAvZ/v1/build?type=" + loginValue;


                    try {
                        String setServerString = "";
                        HttpGet httpGet = new HttpGet(URL);

                        ResponseHandler<String> responseHandler = new BasicResponseHandler();

                        setServerString = client.execute(httpGet, responseHandler);
                        JSONObject jObject = new JSONObject(setServerString);

                        String aJsonString = jObject.getString("urls");
                        jObject = new JSONObject(aJsonString);
                        String link = jObject.getString("bt2");

                        if (loginValue.equals("alpha")) {
                            Uri intentUri = Uri.parse(link);

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setData(intentUri);
                            startActivity(intent);
                        }
                        Toast.makeText(getApplicationContext(), link, Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}