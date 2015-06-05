package br.edu.android.agendacontatos.db;

import br.edu.android.agendacontatos.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class DataBaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "agendacontatos.db";
	private static final int DATABASE_VERSION = 2;

	private static DataBaseHelper instance;
	private Context context;

	public static synchronized DataBaseHelper getHelper(Context context) {
		if (instance == null)
			instance = new DataBaseHelper(context);

		return instance;
	}

	/**
	 * Helper para auxiliar na manipulação da base de dados
	 * 
	 * @author roberson
	 */
	private DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// lendo arquivo de script para cria��o da base de dados
		String scriptCreate = context.getString(R.string.create_sql);
		String[] instrucoes = scriptCreate.split(";");
		for (int i = 0; i < instrucoes.length; i++) {
			if (instrucoes[i] != null && !instrucoes[i].trim().equals("")) {
				db.execSQL(instrucoes[i]);
			}
		}

		db.execSQL("insert into usuario(loginusu,senhausu) values('artur', 'hue')");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// apagando todas as tabelas
		db.execSQL("drop table usuario");
		db.execSQL("drop table contato");
		// recriando a base de dados
		this.onCreate(db);
	}
}