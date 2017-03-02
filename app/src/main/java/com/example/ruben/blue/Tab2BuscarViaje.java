package com.example.ruben.blue;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by ruben on 10/02/2017.
 */

public class Tab2BuscarViaje extends Fragment implements AdapterView.OnItemClickListener{
    private static final String TAG = Tab2BuscarViaje.class.getSimpleName();
    public static final String VIAJE = "viaje";
    private ChildEventListener childEvent;
    private Viaje viaje;
    private ArrayList<Viaje> viajes = new ArrayList<>();
    private ListView listView;
    private DatabaseReference dbRefRoot;
    private FirebaseUser user;
    String conductor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buscarviaje, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            conductor = user.getDisplayName();

            // Name, email address, and profile photo Url
            //String name = profile.getDisplayName();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listViewBuscar);
        dbRefRoot = FirebaseDatabase.getInstance().getReference("Viaje");
        Log.d(TAG,"FUNCIONA 2");
        childEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                viaje = dataSnapshot.getValue(Viaje.class);
                viajes.add(viaje);
                ListAdapterBuscar adapter = new ListAdapterBuscar(getContext(),viajes);
                listView.setAdapter(adapter);
                Log.d(TAG,"FUNCIONA");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                viaje = dataSnapshot.getValue(Viaje.class);
                viajes.add(viaje);
                ListAdapterBuscar adapter = new ListAdapterBuscar(getContext(),viajes);
                listView.setAdapter(adapter);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dbRefRoot.addChildEventListener(childEvent);
        listView.setOnItemClickListener(Tab2BuscarViaje.this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getContext(),ReservaViaje.class);
        intent.putExtra(VIAJE, viajes.get(i));
        Log.d("NOMBRE USUARIO:CLICK ",conductor);
        Log.d("NOMBRE USUARIO:CLICK ",viaje.getConductor());

        Viaje viaje =viajes.get(i);
        if(viaje.getPlazas()<1){
            Toast.makeText(getContext(), getString(R.string.toastViajeCompleto), Toast.LENGTH_LONG).show();
        }else if(viaje.getConductor().trim().equals(conductor.trim())){
            Toast.makeText(getContext(), getString(R.string.toastPropioViaje), Toast.LENGTH_LONG).show();
        }else{
            Log.d(TAG,viaje.getDestino()+" "+viaje.getPlazas()+" "+viaje.getPrecio());
            startActivity(intent);
        }
    }
}
