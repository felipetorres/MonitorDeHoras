package com.example.monitordehoras.receiver;

import java.util.Arrays;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.monitordehoras.model.WifiUtils;
import com.example.monitordehoras.model.states.PrefsState;
import com.example.monitordehoras.model.states.ChegueiNaCaelum;
import com.example.monitordehoras.model.states.VolteiDoAlmoco;
import com.example.monitordehoras.model.states.FuiEmboraOuFuiAlmocar;
import com.example.monitordehoras.model.states.EstouTrabalhando;

public class ConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        WifiUtils wifiUtils = new WifiUtils(context);
        PrefsState[] s = {new ChegueiNaCaelum(context, wifiUtils),
                new VolteiDoAlmoco(context, wifiUtils),
                new FuiEmboraOuFuiAlmocar(context, wifiUtils),
                new EstouTrabalhando(context, wifiUtils)};
        List<PrefsState> states = Arrays.asList(s);

        for (PrefsState state : states) {
            if (state.isOnThisState()) state.doIt();
        }
    }
}
