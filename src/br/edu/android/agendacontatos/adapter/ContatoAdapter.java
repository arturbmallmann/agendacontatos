package br.edu.android.agendacontatos.adapter;

import java.util.List;
import com.example.agendacontatos.R;
import br.edu.android.agendacontatos.model.Contato;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


@SuppressLint("InflateParams")
public class ContatoAdapter extends ArrayAdapter<Contato> {

	public ContatoAdapter(Context context, List<Contato> contatos) {
		super(context, 0,contatos);
	}
	
	public View getView(int position, View convertView,ViewGroup parent){
		Contato contato = getItem(position);
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contato, null);
		}
		
		TextView txtNome = (TextView)convertView.findViewById(R.id.item_nome);
		TextView txtTelefone = (TextView)convertView.findViewById(R.id.item_telefone);
		ImageView imgFoto = (ImageView)convertView.findViewById(R.id.Item_foto);
		txtNome.setText(contato.getNome());
		txtTelefone.setText(contato.getTelefone());
		try{
			if(contato.getFoto()!=null && !contato.getFoto().trim().equals("")){
				Bitmap bitFoto = BitmapFactory.decodeFile(contato.getFoto());
				Bitmap bitScaledFoto = Bitmap.createScaledBitmap(bitFoto, imgFoto.getLayoutParams().width, imgFoto.getLayoutParams().height,false);
				imgFoto.setImageBitmap(bitScaledFoto);
			}
		}catch(Exception e){
			Log.v("Foto", getContext().getString(R.string.erro),e);
		}
		return convertView;
	}

}
