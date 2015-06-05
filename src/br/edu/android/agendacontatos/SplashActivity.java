package br.edu.android.agendacontatos;

import br.edu.android.agendacontatos.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Acoes.imprimir(this, "dasfdsaf %s", "Artur");
        //criando handler
        Handler h = new Handler();
        //agenda a abertura da tela de login
        h.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// Criando uma intent(mensagem) para o android	
				Intent itLogin=new Intent(SplashActivity.this, LoginActivity.class);
				//chama a tela de login
				startActivity(itLogin);
				//finaliza a tela de splash
				finish();
			}
		}, 4000);
    }
}