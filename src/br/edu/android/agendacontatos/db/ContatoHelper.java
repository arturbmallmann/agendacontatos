package br.edu.android.agendacontatos.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import br.edu.android.agendacontatos.model.Contato;

/**
 * Classe auxiliar para manipular dados de contato
 * 
 * @author roberson
 */
public class ContatoHelper extends GenericTableHelper {

	public enum colunas{
		CODIGO ("codcon"),
		NOME ("nomecon"),
		TELEFONE ("telcon"),
		LATITUDE ("latcon"),
		LONGITUDE ("loncon"),
		FOTO ("fotocon");
		private String text;
		colunas(String col){
			this.text=col;
		}
		@Override
		public String toString() {
			return this.text;
		}
	};
	
	public ContatoHelper(Context context) {
		this.db = DataBaseHelper.getHelper(context).getWritableDatabase();
		this.tableName = "contato";
	}

	/**
	 * M�todo gen�rico para inserts
	 * 
	 * @param values
	 */
	public void insert(Contato contato) {
		ContentValues values = new ContentValues();
//		values.put("codcon", contato.getCodigo());
		values.put(colunas.NOME.toString(),contato.getNome());
		values.put(colunas.TELEFONE.toString(),contato.getTelefone());
		values.put(colunas.LATITUDE.toString(),contato.getLatitude());
		values.put(colunas.LONGITUDE.toString(), contato.getLongitude());
		values.put(colunas.FOTO.toString(),contato.getFoto());
		Log.d("addContato","adicionando contato"+values.toString());
		this.insert(values);
	}

	/**
	 * M�todo gen�rico para inserts
	 * 
	 * @param values
	 */
	public void update(Contato contato) {
		ContentValues values = new ContentValues();
		values.put(colunas.NOME.toString(),contato.getNome());
		values.put(colunas.TELEFONE.toString(),contato.getTelefone());
		values.put(colunas.LATITUDE.toString(),contato.getLatitude());
		values.put(colunas.LONGITUDE.toString(), contato.getLongitude());
		values.put(colunas.FOTO.toString(),contato.getFoto());
		Log.d("addContato","editando contato"+values.toString());
		this.update(values, "codcon = ?", new String[] { contato.getCodigo()
				.toString() });
	}

	public void delete(Contato contato){
		this.db.delete(this.getTableName(), "codcon = ?", new String[]{contato.getCodigo().toString()});
	}
	
	@Override
	public Contato getById(String idName, int idValue) {
		Contato contato = null;
		Cursor c = this.db.query(this.getTableName(), null, idName + " = ?",
				new String[] { "" + idValue }, null, null, null);
		if (c != null) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				contato = this.fillContato(c);
				break;
			}
			c.close();
		}

		return contato;
	}

	@Override
	public List<Contato> getAll() {
		List<Contato> contatos = new ArrayList<Contato>();
		Cursor c = this.db.query(this.getTableName(), null, null, null, null,
				null,"nomecon COLLATE LOCALIZED ASC");	//ultimo parametro trás a lista ordenada
		if (c != null) {										//inclusive com caracteres especiais etc..
			c.moveToFirst();
			while (!c.isAfterLast()) {
				contatos.add(this.fillContato(c));
				c.moveToNext();
			}                                                       //         codcon integer primary key autoincrement,
			c.close();                                              //         							nomecon varchar (50) not null,
		}                                                           //         							telcon varchar (20),
        Log.d("getAll",contatos.toString());                                                            //         	    					latcon real,
		return contatos;                                            //         	    					loncon real,
	}                                                               //         	    					fotocon varchar (255) );
                                                                    //
	private Contato fillContato(Cursor c) {                         //
		Contato contato = new Contato();                            //
		contato.setCodigo(c.getInt(c.getColumnIndex("codcon")));
		contato.setNome(c.getString(c.getColumnIndex("nomecon")));
		contato.setTelefone(c.getString(c.getColumnIndex("telcon")));
		contato.setLatitude(c.getDouble(c.getColumnIndex("latcon")));
		contato.setLongitude(c.getDouble(c.getColumnIndex("loncon")));
		contato.setFoto(c.getString(c.getColumnIndex("fotocon")));
		return contato;
	}
}
