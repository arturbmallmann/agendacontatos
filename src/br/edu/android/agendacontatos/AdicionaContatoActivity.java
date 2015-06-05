package br.edu.android.agendacontatos;

import br.edu.android.agendacontatos.R;
import br.edu.android.agendacontatos.db.ContatoHelper;
import br.edu.android.agendacontatos.model.Contato;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AdicionaContatoActivity extends Activity implements OnClickListener{

	private EditText edtNome;
	private EditText edtTelefone;
	private EditText edtLatitude;
	private EditText edtLongitude;
	private ImageView imgFoto;
	private Button mapa;
	private Button salvar;
	private Button voltar;
	private Uri uriFoto;
	
	private Contato contato;
	
	private ContatoHelper contatoHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adiciona_contato);
		
		edtNome = (EditText)findViewById(R.id.campoNome);
		edtTelefone = (EditText)findViewById(R.id.campoNumero);
		edtLatitude = (EditText)findViewById(R.id.campoLatitude);
		edtLongitude = (EditText)findViewById(R.id.campoLongitude);
		imgFoto = (ImageView)findViewById(R.id.campoFoto);
		mapa = (Button)findViewById(R.id.addMapa);
		salvar = (Button)findViewById(R.id.addOk);
		voltar = (Button)findViewById(R.id.addCancelar);
		imgFoto.setOnClickListener(this);
		mapa.setOnClickListener(this);
		salvar.setOnClickListener(this);
		voltar.setOnClickListener(this);
		
		edtLatitude.setEnabled(false);
		edtLongitude.setEnabled(false);
		
		contatoHelper = new ContatoHelper(this);
		
		if(getIntent() != null){
			if(getIntent().getSerializableExtra("contato")!= null){
			//	Acoes.imprimir(this,"carregando Contato: "+getIntent().getSerializableExtra("contato").toString());
				contato= (Contato)getIntent().getSerializableExtra("contato");
					carregaFoto(contato);
				edtNome.setText(contato.getNome());
				edtTelefone.setText(contato.getTelefone());
				edtLatitude.setText(contato.getLatitude().toString());
				edtLongitude.setText(contato.getLongitude().toString());
			}else{
				contato=new Contato();
			}
			
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addMapa:
			
			break;
		case R.id.addOk:
			String nome=edtNome.getText().toString();
			// TODO: lat long
			if(nome == null && edtTelefone.getText().toString() == null &&
					edtNome.getText().toString().equals("") && edtTelefone.getText().toString().equals("")){
						Acoes.imprimir(this,getString(R.string.campos_vazios));
						break;
			}
			contato.setNome(nome);
			contato.setTelefone(edtTelefone.getText().toString());
			if (contato.getCodigo()!=null){
				contatoHelper.update(contato);
			}else{
				contatoHelper.insert(contato);
			}
			Acoes.imprimir(this,getString(R.string.contato_adicionado),nome);
			finish();
			break;
		case R.id.addCancelar:
			finish();
			break;
		case R.id.campoFoto:
			capturarFoto();
			break;
		default:
			break;
		}
		
	}
	
	private void capturarFoto(){
		Log.v("Foto", "Capturando");
		uriFoto = Acoes.getFotoUri(geraNomeFoto());

		Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		it.putExtra(MediaStore.EXTRA_OUTPUT,uriFoto);
		Log.v("Foto", "capturando foto para:"+uriFoto.getPath());
		startActivityForResult(it, 1000);	//codigo 1000 identifica o acionamento da camera via imageview
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1000 && resultCode == RESULT_OK){
			contato.setFoto(uriFoto.getPath());
			carregaFoto(contato);
		}
	}
	
	public void carregaFoto(Contato contato){
		try{
		Bitmap bitFoto = BitmapFactory.decodeFile(contato.getFoto());
		Bitmap bitScaledFoto = Bitmap.createScaledBitmap(bitFoto, imgFoto.getLayoutParams().width, imgFoto.getLayoutParams().height,false);
		imgFoto.setImageBitmap(bitScaledFoto);
		Log.v("Foto","abrindo foto em"+contato.getFoto());
		}catch (Exception e){
			Log.d("Foto", getString(R.string.erro)+contato.getFoto());
		}
	}
	public String geraNomeFoto(){
		long i = (long)(Math.random()*100000)+1;
		return i + ".jpg";
	}
	
}