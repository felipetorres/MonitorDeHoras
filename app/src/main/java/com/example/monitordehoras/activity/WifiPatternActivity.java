package com.example.monitordehoras.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.monitordehoras.R;

import static com.example.monitordehoras.model.Constants.FILENAME;
import static com.example.monitordehoras.model.Constants.NOME_DO_WIFI;

/**
 * Created by felipe on 6/1/15.
 */
public class WifiPatternActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wifi_pattern);

        TextView patternAtual = (TextView) findViewById(R.id.wifi_pattern_atual);
        Button botaoSalvar = (Button) findViewById(R.id.wifi_pattern_salvar);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.wifi_pattern_radio_group);
        final EditText campoPattern = (EditText) findViewById(R.id.wifi_pattern);

        SharedPreferences prefs= getSharedPreferences(FILENAME, MODE_PRIVATE);

        patternAtual.setText(prefs.getString(NOME_DO_WIFI, "Nenhum padrão salvo"));

        final SharedPreferences.Editor editor = prefs.edit();

        botaoSalvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String pattern = campoPattern.getText().toString();
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                if(radioButtonId == R.id.wifi_pattern_igual) {
                    editor.putString("nome", pattern);
                    editor.commit();
                } else {
                    editor.putString("nome", ".*" + pattern + ".*");
                    editor.commit();
                }
                finish();
            }
        });
    }
}
