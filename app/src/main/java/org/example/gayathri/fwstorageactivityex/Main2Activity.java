package org.example.gayathri.fwstorageactivityex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    EditText etName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btnSendBack = (Button) findViewById(R.id.btnSend);
        etName = (EditText) findViewById(R.id.etName);
        btnSendBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSend :
                Intent i = new Intent();
                i.putExtra("name",etName.getText().toString());
                setResult(RESULT_FIRST_USER,i);
                finish();
                break;

            default:
                break;
        }

    }


}
