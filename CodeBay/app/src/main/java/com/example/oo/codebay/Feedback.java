package com.example.oo.codebay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Feedback extends AppCompatActivity implements View.OnClickListener,TextWatcher {

    private View viewById;
    private EditText femail_id;
    private EditText fphone_id;
    private EditText fcomposemail_id;
    private Button fsubmit_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        femail_id = (EditText) findViewById(R.id.femail_id);
        fphone_id = (EditText) findViewById(R.id.fphone_id);
        fcomposemail_id = (EditText) findViewById(R.id.fcomposemail_id);
        fsubmit_id = (Button) findViewById(R.id.fsubmit_id);

        fsubmit_id.setOnClickListener(this);
        femail_id.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String email = s.toString();
        if(email.isEmpty() || email.length() < 10 || !email.contains("@") ||!email.contains(".com")){
            femail_id.setError("Please give a correct email address.");
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fsubmit_id) {
            submitf();
        }
    }
          private void submitf(){
        String str2= femail_id.getText().toString();
        String str1= fcomposemail_id.getText().toString();

        if(str1.isEmpty()){
            femail_id.setError("Required");
            return;
        }
        if (!str2.contains("@") || str2.isEmpty() || str2.length()<10) {
            femail_id.setError("Please entera valid email address.");
            return;
        }
              Intent emailint = new Intent(Intent.ACTION_SEND);
              emailint.setType("text/HTML");
              emailint.putExtra(Intent.EXTRA_EMAIL, new String[]{"manu.17nov@gmail.com"});
              emailint.putExtra(Intent.EXTRA_SUBJECT,"FEEDBACK");
              emailint.putExtra(Intent.EXTRA_TEXT, "Hi,"+
                      "" + fcomposemail_id.getText().toString() + "\". To write him back please use the email \"" +
                              "" + femail_id.getText().toString() + "\". \n\tHave a nice day. \n\tThank You.");
              startActivity(Intent.createChooser(emailint, "Send feedback using..."));

              startActivity(Intent.createChooser(emailint,"Send Feedback Using"));
              fcomposemail_id.setText("");
              femail_id.setText("");
    }
}
