package com.example.ruben.blue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReservaViaje extends AppCompatActivity {
    private static final String TAG = ReservaViaje.class.getSimpleName();
    TextView tvDestino,tvOrigen,tvPrecio,tvNumeroPlazas,tvFecha;
    Button btnReservar;
    EditText etNumPlazasReservar;
    Viaje viaje = new Viaje();
    int plazas;
    String uid;
    private DatabaseReference myRef;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserva_viaje);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = database.getInstance().getReference();
        tvDestino = (TextView) findViewById(R.id.tvDestinoReserva);
        tvOrigen = (TextView) findViewById(R.id.tvOrigenReserva);
        tvPrecio = (TextView) findViewById(R.id.tvPrecioReserva);
        tvNumeroPlazas=(TextView)findViewById(R.id.tvNumeroPlazasReserva);
        tvFecha=(TextView) findViewById(R.id.tvFechaReserva);
        etNumPlazasReservar=(EditText) findViewById(R.id.etNumPlazasReservar);
        btnReservar = (Button) findViewById(R.id.btnReservar);

        Intent intent = getIntent();
        viaje = intent.getParcelableExtra(Tab2BuscarViaje.VIAJE);
        Log.d(TAG,viaje.getDestino()+" "+viaje.getPlazas()+" "+viaje.getPrecio());
        tvDestino.setText(viaje.getDestino());
        tvOrigen.setText(viaje.getOrigen());
        tvPrecio.setText(viaje.getPrecio());
        tvNumeroPlazas.setText(viaje.getPlazas()+"");
        tvFecha.setText(viaje.getFecha());
        Log.d(TAG,viaje.getPrecio()+" "+viaje.getPlazas());
        plazas = Integer.parseInt(tvNumeroPlazas.getText().toString());

    }
    public void reservar(View view){
        String key = myRef.child("Reserva").push().getKey();
        if (Integer.parseInt(etNumPlazasReservar.getText().toString())>plazas){
            Toast.makeText(this, getString(R.string.toastMuchasPlazas), Toast.LENGTH_LONG).show();
        }else if(Integer.parseInt(etNumPlazasReservar.getText().toString())<1){
            Toast.makeText(this, getString(R.string.toastMenosPlazas), Toast.LENGTH_LONG).show();
        }else{
            viaje.setPlazas(viaje.getPlazas() - Integer.parseInt(etNumPlazasReservar.getText().toString()));
            myRef.child("Viaje").child(viaje.getKey()).setValue(viaje);
            if (user != null) {
                uid = user.getUid();
            }
            viaje.setUid(uid);
            myRef.child("Reservas").child(key).setValue(viaje);
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
