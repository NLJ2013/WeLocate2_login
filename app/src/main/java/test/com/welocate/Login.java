package test.com.welocate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nipuna Jayawardana on 5/15/2015.
 */

public class Login extends Activity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private Button submit_btn;
    private Button register_btn;

    private ProgressDialog Dialog;

    JSON JSON = new JSON();


    private static final String URL = "http://nipuna2013.webatu.com/login.php";

    private static final String action = "act";
    private static final String state = "stmnt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        submit_btn = (Button)findViewById(R.id.login);
        register_btn = (Button)findViewById(R.id.register);

        submit_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login:
                new AttemptLogin().execute();
                break;
            case R.id.register:
                Intent i = new Intent(this, Register.class);
                startActivity(i);
                break;

            default:
                break;
        }
    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Dialog = new ProgressDialog(Login.this);
            Dialog.setMessage("logging in please wait");
            Dialog.setIndeterminate(false);
            Dialog.setCancelable(true);
            Dialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            int success;
            String Username = username.getText().toString();
            String Password =password.getText().toString();
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", Username));
                params.add(new BasicNameValuePair("password", Password));

                Log.d("", "");

                JSONObject json = JSON.makeHttpRequest(URL, "POST", params);

                Log.d("attempting login", json.toString());

                success = json.getInt(action);
                if (success == 1) {
                    Log.d("Successfully logged!", json.toString());

                    return json.getString(state);
                }else{
                    Log.d("", json.getString(state));
                    return json.getString(state);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            Dialog.dismiss();
            if (file_url != null){
                Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }
}
