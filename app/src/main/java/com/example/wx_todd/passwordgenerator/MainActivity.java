package com.example.wx_todd.passwordgenerator;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView passwordCount, passwordLength, password;
    SeekBar countSeekbar, lengthSeekbar;
    Button generateButton;
    ArrayList<String> passwords = new ArrayList<>();
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordCount = (TextView) findViewById(R.id.passwordCountTextView);
        passwordLength = (TextView) findViewById(R.id.passwordLengthTextView);
        password = (TextView) findViewById(R.id.showPasswordTextView);
        passwordCount.setText("1");
        passwordLength.setText("8");

        countSeekbar = (SeekBar) findViewById(R.id.passwordCountSeekbar);
        countSeekbar.setMax(9);
        countSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = progress + 1;
                seekBar.setProgress(progress);
                passwordCount.setText(Integer.toString(value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        lengthSeekbar = (SeekBar) findViewById(R.id.passwordLengthSeekbar);
        lengthSeekbar.setMax(15);
        lengthSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = progress + 8;
                seekBar.setProgress(progress);
                passwordLength.setText(Integer.toString(value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        generateButton = (Button) findViewById(R.id.button);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordCount.equals("") || passwordLength.equals("")){

                }
                else
                    new GeneratePassword().execute();
            }
        });


    }
    class GeneratePassword extends AsyncTask<Void, Integer, ArrayList>{
        int count, length;
        String result;
        ArrayList<String> arrayList = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             count = Integer.parseInt(passwordCount.getText().toString());
             length = Integer.parseInt(passwordLength.getText().toString());
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Generating Password....");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            passwords = arrayList;
            progressDialog.dismiss();
            final CharSequence[] charPasswords = passwords.toArray(new CharSequence[passwords.size()]);
            builder= new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Choose a password");
            builder.setItems(charPasswords, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    password.setText(charPasswords[i]);
                }
            });
            builder.setCancelable(false);
            builder.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            for(int i =0; i < count; i++){
                Util util = new Util();
                result = util.getPassword(length);
                arrayList.add(result);
                publishProgress((100/count)*i);
            }
            return arrayList;
        }
    }
}
