package com.example.drip_application;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    Button bRegistreren;
    EditText etEmail, etWachtwoord, etCode;

    FirebaseAuth mFirebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etWachtwoord = (EditText) findViewById(R.id.etWachtwoord);
        etCode = (EditText) findViewById(R.id.etCode);
        bRegistreren = (Button) findViewById(R.id.bRegistreren);

        bRegistreren.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bRegistreren:

                String email = etEmail.getText().toString().trim();
                String wachtwoord = etWachtwoord.getText().toString().trim();
                String codeCheck = etCode.getText().toString().trim();
                String code = "1234";

                if(email.isEmpty()){
                    etEmail.setError("Voer een email in.");
                    etEmail.requestFocus();
                }
                else if (codeCheck.isEmpty()){
                    etCode.setError("Voer een code in.");
                    etCode.requestFocus();
                }
                else if (!codeCheck.equals(code)){
                    etCode.setError("Code is incorrect.");
                    etCode.requestFocus();
                }
                else if(wachtwoord.isEmpty()){
                    etWachtwoord.setError("Voer een wachtwoord in.");
                    etWachtwoord.requestFocus();
                }
                else if(wachtwoord.length() < 6){
                    etWachtwoord.setError("Wachtwoord moet minimaal 6 tekens lang zijn.");
                    etWachtwoord.requestFocus();
                }
                else if(email.isEmpty() && wachtwoord.isEmpty() && codeCheck.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Er zijn geen gegevens ingevuld.", Toast.LENGTH_SHORT).show();
                }
                else if(!email.isEmpty() && !wachtwoord.isEmpty() && !codeCheck.isEmpty()){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, wachtwoord).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Registreren is niet gelukt.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Er is een fout opgetreden.", Toast.LENGTH_SHORT).show();
                }
            break;
        }
    }
}
