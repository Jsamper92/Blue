package com.example.ruben.blue;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by ruben on 10/02/2017.
 */

public class Tab3MisViajes extends Fragment {
    private FirebaseUser user;
    private static final String TAG = Tab3MisViajes.class.getSimpleName();
    private ListView listViewMisViajes;
    private DatabaseReference dbRef;
    private ChildEventListener childEvent;
    private Viaje viaje;
    String uid;
    private ArrayList<Viaje> viajes = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.misviajes, container, false);
        viajes=new ArrayList<>();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        listViewMisViajes = (ListView) view.findViewById(R.id.listViewMisViajes);
        //dbRef = FirebaseDatabase.getInstance();
        Log.d(TAG,"UID USUARIO  EL BUENO "+uid);
        Query q = FirebaseDatabase.getInstance().getReference("Viaje").orderByChild("uid").equalTo(uid);
        Query q1 = FirebaseDatabase.getInstance().getReference("Reservas").orderByChild("uid").equalTo(uid);
        Log.d("QUERY",q.toString());
        ChildEventListener cv = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                viaje = dataSnapshot.getValue(Viaje.class);
                viajes.add(viaje);
                ListAdapterMisViajes adapter2 = new ListAdapterMisViajes(getContext(),viajes);
                listViewMisViajes.setAdapter(adapter2);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                viaje = dataSnapshot.getValue(Viaje.class);
                viajes.add(viaje);
                ListAdapterMisViajes adapter2 = new ListAdapterMisViajes(getContext(),viajes);
                listViewMisViajes.setAdapter(adapter2);
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
        q.addChildEventListener(cv);
        q1.addChildEventListener(cv);
    }
}
