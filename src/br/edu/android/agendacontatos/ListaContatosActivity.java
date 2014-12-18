package br.edu.android.agendacontatos;


import java.io.File;

import br.edu.android.agendacontatos.adapter.ContatoAdapter;
import br.edu.android.agendacontatos.db.ContatoHelper;
import br.edu.android.agendacontatos.model.Contato;

import com.example.agendacontatos.R;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class ListaContatosActivity extends Activity {
	private ListView lstContatos;
	private ArrayAdapter<Contato>  adaptador;
	ContatoHelper contatoHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			if (getIntent()==null){
				Acoes.falhaSair(this);
			}
			
			String login = getIntent().getStringExtra("login");
			this.setTitle(getString(R.string.title_activity_lista_contatos,login));
			//verifacando se a foi acionada via parametro
			Toast.makeText(this, getString(R.string.msgBemVindo,login), Toast.LENGTH_LONG).show();
			setContentView(R.layout.activity_lista_contatos);
			//carregaLstContatos();
			
			contatoHelper = new ContatoHelper(this);
//				adaptador = new ArrayAdapter<Contato>(this,
//						android.R.layout.simple_list_item_1,
//						new ArrayList<Contato>());
//				
//				lstContatos = (ListView)findViewById(R.id.lstContatos);
//				lstContatos.setAdapter(adaptador);
			lstContatos = (ListView)findViewById(R.id.lstContatos);
			registerForContextMenu(lstContatos);			
		}
	private void carregaLstContatos(){
		try {
		/*	adaptador = new ArrayAdapter<Contato>(this,
					android.R.layout.simple_list_item_1,
					contatoHelper.getAll());*/
			adaptador = new ContatoAdapter(this, contatoHelper.getAll());
			lstContatos.setAdapter(adaptador);
			//lstContatos
			adaptador.notifyDataSetChanged();
		} catch (Exception e) {
			Log.e("Erro", e.toString());
			Acoes.imprimir(this, getString(R.string.erro),e.toString());
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.menu_listacontatos, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.menu_lstcontatos, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mnNovoContato:
			Intent itAdicionaContato = new Intent(this,AdicionaContatoActivity.class);
			startActivity(itAdicionaContato);
			break;
		case R.id.mnSair:
			Acoes.sair(this);
			break;
		case R.id.mnSobre:
			
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info =
					(AdapterContextMenuInfo) item.getMenuInfo();
		Contato contato = (Contato)lstContatos.getAdapter().getItem(info.position);
		switch (item.getItemId()) {
		case R.id.mnEditar:
			editar(contato);
			break;
		case R.id.mnRemover:
			remover(contato);
			break;
		case R.id.mnLigar:
			ligar(contato);
			break;
		case R.id.mnSMS:
			sms(contato);
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	private void editar(Contato contato){
		Intent itAdicionaContato = new Intent(this,AdicionaContatoActivity.class);
		itAdicionaContato.putExtra("contato", contato);
		startActivity(itAdicionaContato);
	}
	private void remover(Contato contato){
		// TODO Acoes.
		Acoes.imprimir(this, getString(R.string.strRemover));
		try{
		new File(contato.getFoto()).delete();
		}catch(Exception e){Log.d("Foto", contato.getFoto()+"nem existia");}
		contatoHelper.delete(contato);
		carregaLstContatos();
	}
	private void ligar(Contato contato){
		Acoes.imprimir(this, getString(R.string.imprimirCall), contato);
		Uri uri = Uri.parse("tel:"+contato.getTelefone());
		Intent itLigar = new Intent(Intent.ACTION_CALL,uri);
		startActivity(itLigar);		
	}
	protected void sms(final Contato contato){
		/*
		Acoes.imprimir(this, getString(R.string.imprimirSMS),contato);
		Uri uri = Uri.parse("sms:"+contato.getTelefone());
		Intent itSms = new Intent(Intent.ACTION_SENDTO,uri); //*/
		final Activity contexto=this;
		final EditText editText = new EditText(contexto);
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Acoes.enviarMensagem(contexto, editText.getText().toString(),contato.getTelefone());
			}
		};
		Acoes.dialogoComTexto(	contexto,
								getText(R.string.strSMS).toString(),getText(R.string.strSMS).toString(),
								editText,listener);
	}
	@Override
	protected void onResume() {
		super.onResume();
		carregaLstContatos();
	}
}