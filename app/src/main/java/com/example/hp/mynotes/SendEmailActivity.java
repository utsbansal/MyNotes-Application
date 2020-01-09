package com.example.hp.mynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendEmailActivity extends AppCompatActivity {

    private EditText mEditTextTo;
    private EditText mEditTextSubject;
    private EditText mEditTextMessage;
    String subject = "";
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        mEditTextTo = (EditText)findViewById(R.id.edit_text_to);
        mEditTextSubject = (EditText)findViewById(R.id.edit_text_subject);
        mEditTextMessage =(EditText)findViewById(R.id.edit_text_message);

        Button buttonSend = (Button)findViewById(R.id.button_send);

        Bundle info=getIntent().getExtras();
        if(info!=null){
            subject=info.getString("Subject");
            message = info.getString("Message");
        }
        mEditTextSubject.setText(subject);
        mEditTextMessage.setText(message);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipientList = mEditTextTo.getText().toString();
                String[] recipients = recipientList.split(",");

                String subject = mEditTextSubject.getText().toString();
                String message = mEditTextMessage.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);

                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Choose an email client"));
                finish();
            }
        });
    }
}
