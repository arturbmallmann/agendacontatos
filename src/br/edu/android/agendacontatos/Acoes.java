package br.edu.android.agendacontatos;

import java.io.File;

import br.edu.android.agendacontatos.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.EditText;
import android.widget.Toast;

public class Acoes {
	public static final void sair(final Activity activity){
		AlertDialog.Builder dialogo = new AlertDialog.Builder(activity);
		dialogo.setTitle(activity.getString(R.string.sair));
		dialogo.setMessage(activity.getString(R.string.deseja_sair));
		dialogo.setCancelable(false);
		
		DialogInterface.OnClickListener onClick = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(DialogInterface.BUTTON_POSITIVE == which){
					activity.finish();
					System.exit(0);
				}
			}
		};
		dialogo.setPositiveButton(R.string.sim,onClick );
		dialogo.setNegativeButton(R.string.nao,onClick);
		dialogo.show();
	}
	public static final boolean dialogo(final Activity activity,String opc1,String opc2,String opc3){
		return false;//TODO
	}
	public static final void falhaSair(final Activity activity){
		Toast.makeText(activity,activity.getText(R.string.falhaSair),Toast.LENGTH_LONG).show();
		activity.finish();
		System.exit(0);
	}
	public static final void imprimir (final Context contexto,String texto,Object...objects){
		Toast.makeText(contexto, String.format(texto, objects), Toast.LENGTH_LONG).show();;
	}
	public static final void imprimir (final Context contexto,String texto){
		imprimir(contexto, texto, "");
	}
	public static final Uri getFotoUri (String nomeArquivo){
		File imagesDir = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+
				File.separator + "agendacontatos");
		if (!imagesDir.exists()){
			imagesDir.mkdir();
		}
		
		return Uri.fromFile(new File(imagesDir.getPath()+File.separator+nomeArquivo));
	}
	public static final String dialogoComTexto(final Activity activity,String title,
			String question,EditText edtMensagem,DialogInterface.OnClickListener listener){
		AlertDialog.Builder mensagem = new AlertDialog.Builder(activity);
		mensagem.setTitle(title);
		mensagem.setMessage(question);
		mensagem.setView(edtMensagem);
		mensagem.setCancelable(true);
		mensagem.setPositiveButton(R.string.btnOk, listener);
		mensagem.show();
		return edtMensagem.getText().toString();
	}
	public static final void enviarMensagem(final Activity activity,String mensagem,String numero){
		Intent itSms = new Intent(Intent.ACTION_VIEW);
		itSms.setData(Uri.parse("smsto:"));
		itSms.setType("vnd.android-dir/mms-sms");
		itSms.putExtra("address", numero);

		itSms.putExtra("sms_body", mensagem);
		activity.startActivity(itSms);
	}
}