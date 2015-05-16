package test.com.welocate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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


public class Register extends Activity implements View.OnClickListener {

    private EditText user;
    private EditText pass;
    private EditText email;
    private EditText phone;
    private EditText name;
    private Button  register_btn;

    private ProgressDialog Dialog;

    JSON JSON = new JSON();

    private static final String URL = "http://nipuna2013.webatu.com/register.php";

    private static final String Activity = "act";
    private static final String State = "stmnt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        user = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);
        name = (EditText)findViewById(R.id.name);
        phone = (EditText)findViewById(R.id.phone);
        email = (EditText)findViewById(R.id.email);

        register_btn = (Button)findViewById(R.id.register);
        register_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        new CreateUser().execute();

    }

    class CreateUser extends AsyncTask<String, String, String> {

        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Dialog = new ProgressDialog(Register.this);
            Dialog.setMessage("Registering......");
            Dialog.setIndeterminate(false);
            Dialog.setCancelable(true);
            Dialog.show();
        }

        @Override
        protected String doInBackground(String... args) {


            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            String Name=name.getText().toString();
            String Phone=phone.getText().toString();
            String Email=email.getText().toString();
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
                params.add(new BasicNameValuePair("name", Name));
                params.add(new BasicNameValuePair("phone", Phone));
                params.add(new BasicNameValuePair("email", Email));

                Log.d("", "");

                JSONObject json = JSON.makeHttpRequest(
                        URL, "POST", params);

                Log.d("atttempting login", json.toString());

                success = json.getInt(Activity);
                if (success == 1) {
                    Log.d("registered!", json.toString());
                    finish();
                    return json.getString(State);
                }else{
                    Log.d("login failed!", json.getString(Activity));
                    return json.getString(State);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {

            Dialog.dismiss();
            if (file_url != null){
                Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }

}