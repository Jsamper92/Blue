package com.example.ruben.blue;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    public static final String NOMUSU = "nomUsu";
    Button btnRegistro, btnCancelar;
    EditText etEmail, etContra, etUsuario;
    String nomUsu;
    ProgressBar progressBar2;
    LinearLayout lineralog;
    private static FirebaseAuth Auth;
    private FirebaseUser user;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = database.getInstance().getReference();
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        etEmail = (EditText) findViewById(R.id.etCorreosin);
        etContra = (EditText) findViewById(R.id.etContrasin);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        lineralog = (LinearLayout) findViewById(R.id.lineralog);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        Auth= FirebaseAuth.getInstance();
        getSupportActionBar().hide();
    }

    public void Registrar(View view){
        nomUsu=etUsuario.getText().toString();
        String email=etEmail.getText().toString();
        String password=etContra.getText().toString();

        if(etEmail.getText().toString().equals("")){
            Toast.makeText(this, getString(R.string.toastEmailVacio), Toast.LENGTH_SHORT).show();
        }else if(etContra.getText().toString().equals("") || etContra.length()<6){
            Toast.makeText(this, getString(R.string.toastContraVacia), Toast.LENGTH_SHORT).show();
        }else if(etUsuario.getText().toString().equals("")){
            Toast.makeText(this, getString(R.string.toastUsuarioVacio), Toast.LENGTH_SHORT).show();
        }else {
            crearCuenta(email, password);
        }
    }
    public void Cancelar(View view){
        startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
        finish();
    }
    private void crearCuenta(String email, String password){
        Auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Fallido", Toast.LENGTH_SHORT).show();
                    etEmail.setText("");
                    etContra.setText("");
                    etUsuario.setText("");

                }else{
                    nomUsu=etUsuario.getText().toString();
                    crearPerfilUsuario(task.getResult().getUser());
                    Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                    intent.putExtra(NOMUSU, nomUsu);
                    Log.d("NOM USU REGSITRO: ",nomUsu);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void crearPerfilUsuario(FirebaseUser user) {
        UserProfileChangeRequest addNombreUsu = new UserProfileChangeRequest.Builder().setDisplayName(nomUsu).build();
        user.updateProfile(addNombreUsu).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("Nombre de usuario",nomUsu);
                }
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}