package com.example.ruben.blue;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {
    EditText etEmaillog, etContralog;
    Button btnEntrar;
    TextView tvRegistro;
    private FirebaseAuth Auth;
    private FirebaseAuth.AuthStateListener AuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        etEmaillog = (EditText) findViewById(R.id.etCorreosin);
        etContralog = (EditText) findViewById(R.id.etContrasin);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        tvRegistro = (TextView) findViewById(R.id.tvRegistro);
        Auth = FirebaseAuth.getInstance();
        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };
        getSupportActionBar().hide();
    }
    @Override
    public void onStart() {
        super.onStart();
        Auth.addAuthStateListener(AuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (AuthListener != null) {
            Auth.removeAuthStateListener(AuthListener);
        }
    }
    public void entrar(View view){
        String email=etEmaillog.getText().toString();
        String password=etContralog.getText().toString();
        if(etEmaillog.getText().toString().equals("")){
            Toast.makeText(this, getString(R.string.toastEmailVacio), Toast.LENGTH_SHORT).show();
        }else if(etContralog.getText().toString().equals("") || etContralog.length()<6){
            Toast.makeText(this, getString(R.string.toastContraVacia), Toast.LENGTH_SHORT).show();
        }else {
            login(email, password);
        }
    }
    private void login(String email, String password){
        Auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LogInActivity.this, R.string.toastMal,
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            startActivity(new Intent(LogInActivity.this, MainActivity.class));
                            finish();
                        }

                    }
                });
    }
    public void irRegistrar(View view){
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }
}
