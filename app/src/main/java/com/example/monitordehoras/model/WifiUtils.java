package com.example.monitordehoras.model;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.joda.time.DateTime;

public class WifiUtils {


    private String ultimoWifiAcessado;
    private WifiManager manager;
    private WifiInfo info;
    private DateTime ultimaMudancaDeWifi;

    public WifiUtils() {
        ultimoWifiAcessado = "";
        ultimaMudancaDeWifi = new DateTime();
    }

    public void setContext(Context context) {
        manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        info = manager.getConnectionInfo();
    }

    public void setTempoDaUltimaConexaoWifi(DateTime tempo) {
        this.ultimaMudancaDeWifi = tempo;
    }

    public String getWifiName(){
        return info.getSSID();
    }

    public boolean mudeiDeWifiDesdeOsUltimosCincoMinutos() {
        DateTime tempoAtual = new DateTime();
        if(ultimaMudancaDeWifi.plusMinutes(5).isBefore(tempoAtual)) {
            return !ultimoWifiAcessado.equals(getWifiName());
        }
        return false;

    }
}