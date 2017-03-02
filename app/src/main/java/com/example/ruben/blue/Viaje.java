package com.example.ruben.blue;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruben on 10/02/2017.
 */

public class Viaje implements Parcelable{
    private String origen;
    private String destino;
    private String fecha;
    private String hora;
    private String precio;
    private int plazas;
    private String key;
    private String uid;
    private String conductor;

    public Viaje(String origen, String destino, String fecha, String hora, String precio, int plazas, String key, String uid, String conductor) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.plazas = plazas;
        this.key = key;
        this.uid = uid;
        this.conductor = conductor;
    }

    public Viaje() {
    }

    protected Viaje(Parcel in) {
        origen = in.readString();
        destino = in.readString();
        fecha = in.readString();
        hora = in.readString();
        precio = in.readString();
        plazas = in.readInt();
        key = in.readString();
        uid = in.readString();
        conductor = in.readString();
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("origen",origen);
        result.put("destino",destino);
        result.put("fecha", fecha);
        result.put("hora", hora);
        result.put("precio", precio);
        result.put("numPlazas", plazas);
        result.put("key",key);
        result.put("uid",uid);
        result.put("conductor",conductor);


        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(origen);
        parcel.writeString(destino);
        parcel.writeString(fecha);
        parcel.writeString(hora);
        parcel.writeString(precio);
        parcel.writeInt(plazas);
        parcel.writeString(key);
        parcel.writeString(uid);
        parcel.writeString(conductor);

    }
    public static final Creator<Viaje> CREATOR = new Creator<Viaje>() {
        @Override
        public Viaje createFromParcel(Parcel in) {
            return new Viaje(in);
        }

        @Override
        public Viaje[] newArray(int size) {
            return new Viaje[size];
        }
    };
}
