package com.example.monitordehoras.receiver;

import java.util.Arrays;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.monitordehoras.model.states.PrefsState;
import com.example.monitordehoras.model.states.State1;
import com.example.monitordehoras.model.states.State2;
import com.example.monitordehoras.model.states.State3;
import com.example.monitordehoras.model.states.State4;

public class ConnectionReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		PrefsState[] s = {new State1(context), new State2(context), new State3(context), new State4(context)};
		List<PrefsState> states = Arrays.asList(s);
		
		for (PrefsState state : states) {
			if(state.isOnThisState()) state.doIt();
		}
	}
}
