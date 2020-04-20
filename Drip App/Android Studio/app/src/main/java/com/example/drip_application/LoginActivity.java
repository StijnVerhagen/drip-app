package com.example.drip_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button bInloggen;
    EditText etEmail, etWachtwoord;
    TextView tvRegistrerenLink;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etWachtwoord = (EditText) findViewById(R.id.etWachtwoord);
        bInloggen = (Button) findViewById(R.id.bInloggen);
        tvRegistrerenLink = (TextView) findViewById(R.id.tvRegistrerenLink);

        bInloggen.setOnClickListener(this);
        tvRegistrerenLink.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bInloggen:

                String email = etEmail.getText().toString().trim();
                String wachtwoord = etWachtwoord.getText().toString().trim();
                if (email.isEmpty()) {
                    etEmail.setError("Voer een gebruikersnaam in.");
                    etEmail.requestFocus();
                } else if (wachtwoord.length() < 6) {
                    etWachtwoord.setError("Wachtwoord moet minimaal 6 tekens lang zijn.");
                    etWachtwoord.requestFocus();
                } else if (wachtwoord.isEmpty()) {
                    etWachtwoord.setError("Voer een wachtwoord in.");
                    etWachtwoord.requestFocus();
                } else if (email.isEmpty() && wachtwoord.isEmpty()) {
                    Toast.makeText(this, "Er zijn geen gegevens ingevuld.", Toast.LENGTH_SHORT).show();
                } else if (!email.isEmpty() && !wachtwoord.isEmpty()) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, wachtwoord)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        //updateUI(user);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Inloggen niet gelukt.", Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }
                                }
                            });
                    break;
                }

            case R.id.tvRegistrerenLink:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

        }
    }
}
