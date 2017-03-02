package com.example.ruben.blue;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by ruben on 10/02/2017.
 */

public class Tab1PublicarViaje extends Fragment {
    private static final String TAG = Tab2BuscarViaje.class.getSimpleName();
    public static final String KEY = "key";
    Button btnPublicar;
    EditText etOrigen, etDestino, etPrecio, etNumPlazas;
    static EditText etFecha, etHora;
    String uid;
    private DatabaseReference myRef;
    private FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.publicarviaje, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = database.getInstance().getReference();
        btnPublicar = (Button) view.findViewById(R.id.btnPublicar);
        etOrigen = (EditText) view.findViewById(R.id.etOrigen);
        etDestino = (EditText) view.findViewById(R.id.etDestino);
        etFecha = (EditText) view.findViewById(R.id.etFecha);
        etHora = (EditText) view.findViewById(R.id.etHora);
        etPrecio = (EditText) view.findViewById(R.id.etPrecio);
        etNumPlazas = (EditText) view.findViewById(R.id.etNumPlazas);
        etFecha.setKeyListener(null);
        etFecha.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                new DateDialog().show(getFragmentManager(),"DatePickerInFull");
            }
        });
        etHora.setKeyListener(null);
        etHora.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                new TimeDialog().show(getFragmentManager(),"TimePickerInFull");
            }
        });
        if (user != null) {
            uid = user.getUid();

            // Name, email address, and profile photo Url
            //String name = profile.getDisplayName();
        }
        btnPublicar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String origen = etOrigen.getText().toString();
                String destino= etDestino.getText().toString();
                String fecha = etFecha.getText().toString();
                String hora = etHora.getText().toString();
                int numPlazas = Integer.parseInt(etNumPlazas.getText().toString());
                String precio = etPrecio.getText().toString();
                String key = myRef.child("Viaje").push().getKey();
                if(origen.equals("")){
                    Toast.makeText(getActivity(), getString(R.string.toastOrigenVacio), Toast.LENGTH_LONG).show();
                }else if(destino.equals("")){
                    Toast.makeText(getActivity(), getString(R.string.toastDestinoVacio), Toast.LENGTH_SHORT).show();
                }else if(fecha.equals("")){
                    Toast.makeText(getActivity(), getString(R.string.toastFechaVacia), Toast.LENGTH_SHORT).show();
                }else if(numPlazas == 0){
                    Toast.makeText(getActivity(), getString(R.string.toastNumPlazasVacia), Toast.LENGTH_SHORT).show();
                }else if(precio.equals("")){
                    Toast.makeText(getActivity(), getString(R.string.toastPrecioVacia), Toast.LENGTH_SHORT).show();
                }
                else {
                    publicarViaje(origen, destino,fecha,hora,numPlazas ,precio , key);
                }
            }
        });
    }

    private void publicarViaje(String origen, String destino,String fecha,String hora,int numPlazas,String precio ,String key){
        Viaje viaje = new Viaje();
        viaje.setOrigen(origen);
        etOrigen.setText("");
        viaje.setDestino(destino);
        etDestino.setText("");
        viaje.setFecha(fecha);
        etFecha.setText("");
        viaje.setHora(hora);
        etHora.setText("");
        viaje.setPlazas(numPlazas);
        etNumPlazas.setText("");
        viaje.setPrecio(precio);
        etPrecio.setText("");
        viaje.setKey(key);
        //String name = user.getDisplayName();
        viaje.setUid(uid);
        viaje.setConductor(user.getDisplayName());
        Log.d(TAG,viaje.getUid());
        myRef.child("Viaje").child(key).setValue(viaje);
        Toast.makeText(getContext(), "Viaje publicado", Toast.LENGTH_LONG).show();
    }

    public static class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Obtener fecha actual
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Retornar en nueva instancia del dialogo selector de fecha
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            etFecha.setText(day+"/"+(month +1) +"/"+year);
        }
    }
    public static class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Iniciar con el tiempo actual
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Retornar en nueva instancia del dialogo selector de tiempo
            return new TimePickerDialog(getActivity(),this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            etHora.setText(hourOfDay+":"+minute);
        }
    }
}
