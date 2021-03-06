package com.example.monitordehoras.receiver;

import java.util.Arrays;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.monitordehoras.model.WifiUtils;
import com.example.monitordehoras.model.states.PrefsState;
import com.example.monitordehoras.model.states.ChegueiNaCaelum;
import com.example.monitordehoras.model.states.VolteiDoAlmoco;
import com.example.monitordehoras.model.states.FuiEmboraOuFuiAlmocar;
import com.example.monitordehoras.model.states.EstouTrabalhando;

import org.joda.time.DateTime;

public class ConnectionReceiver extends BroadcastReceiver {

    WifiUtils wifiUtils;

    public ConnectionReceiver() {
        this.wifiUtils = new WifiUtils();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.wifiUtils.setContext(context);

        PrefsState[] s = {new ChegueiNaCaelum(context, wifiUtils),
                new VolteiDoAlmoco(context, wifiUtils),
                new FuiEmboraOuFuiAlmocar(context, wifiUtils),
                new EstouTrabalhando(context, wifiUtils)};
        List<PrefsState> states = Arrays.asList(s);

        for (PrefsState state : states) {
            if (state.isOnThisState()) state.doIt();
        }

        this.wifiUtils.setTempoDaUltimaConexaoWifi(new DateTime());
    }
}
