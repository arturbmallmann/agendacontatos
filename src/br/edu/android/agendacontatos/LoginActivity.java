package br.edu.android.agendacontatos;

import br.edu.android.agendacontatos.db.UsuarioHelper;

import com.example.agendacontatos.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{//cuidado implementar da classe view
	// declarando atributos locais (vinculo com
	private EditText edtLogin;
	private EditText edtSenha;
	private Button btnOk;
	private Button btnSair;
	@Override                                                   // poderia muito bem fazer assim                   
	protected void onCreate(Bundle savedInstanceState) {        //                                                     
		super.onCreate(savedInstanceState);                     //	btnOk.setOnClickListener(new OnClickListener() {   
		setContentView(R.layout.activity_login);                //		@Override                                      
		// pegando referencia dos componentes visuais           //		public void onClick(View v) {                  
		edtLogin=(EditText)findViewById(R.id.edtLogin);         //			// TODO Auto-generated method stub         
		edtSenha=(EditText)findViewById(R.id.edtSenha);         //			                                           
		btnOk=(Button)findViewById(R.id.btnOk);                 //		}                                              
		btnSair=(Button)findViewById(R.id.btnSair);             //	});                                                
		btnOk.setOnClickListener(this);                         //	                                                 
		btnSair.setOnClickListener(this);                       //
	
	}
	private void validar() {
		String login = edtLogin.getText().toString();
		String senha = edtSenha.getText().toString();
		UsuarioHelper usuariohelper = new UsuarioHelper(this);
		if (usuariohelper.isLoginValido(login, senha)){
			Intent itListaContatos = new Intent(this,ListaContatosActivity.class);
			itListaContatos.putExtra("login", login);
			Log.d("Activity", "criou");
			startActivity(itListaContatos);
			finish();
		}else{
			/* com getString, é bom se acustumar com ele, pois sem ele pegamos o valor id e nao o texto
			componentes android suportam ele, porém, se formos fazer uma concatenação teremos na mais
			nada menos que um número inteiro*/
			Toast.makeText(this, getString(R.string.usuario_senha_incorretos), Toast.LENGTH_LONG).show();
			edtLogin.setText("");
			edtSenha.setText("");
			edtLogin.requestFocus();
		}
			
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnOk:
			validar();
			break;
		case R.id.btnSair:
			Acoes.sair(this);
			break;
		default:
			break;
		}
	}
}
