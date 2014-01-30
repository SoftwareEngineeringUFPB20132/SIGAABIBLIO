package br.nti.SigaaBiblio.persistence;

import java.util.HashMap;
import br.nti.SigaaBiblio.model.Usuario.Usuarios;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class UsuarioProvider extends ContentProvider {

	public static final int USUARIOS = 1;
	public static final String TABELA = "usuario";
	static final UriMatcher uriUsuarios;
	private static final HashMap<String, String> colunas;
	static SQLiteDatabase sqLite;

	static {
		uriUsuarios = new UriMatcher(UriMatcher.NO_MATCH);
		uriUsuarios.addURI(Usuarios.AUTHORITY, "usuarios", USUARIOS);

		colunas = new HashMap<String, String>();
		colunas.put(Usuarios._ID, Usuarios._ID);
		colunas.put(Usuarios.IDUSUARIOBIBLIOTECA, Usuarios.IDUSUARIOBIBLIOTECA);
		colunas.put(Usuarios.NOME, Usuarios.NOME);
		colunas.put(Usuarios.MATRICULA, Usuarios.MATRICULA);
		colunas.put(Usuarios.ISALUNO, Usuarios.ISALUNO);
		colunas.put(Usuarios.CURSO, Usuarios.CURSO);
		colunas.put(Usuarios.URLFOTO, Usuarios.URLFOTO);
		colunas.put(Usuarios.UNIDADE, Usuarios.UNIDADE);
		colunas.put(Usuarios.LOGIN, Usuarios.LOGIN);
		colunas.put(Usuarios.SENHA, Usuarios.SENHA);

	}

	public static boolean isTypeOf(Uri uri, int type) {
		if (uriUsuarios.match(uri) == type)
			return true;
		else
			return false;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return sqLite.delete(TABELA, arg1, arg2);

	}

	@Override
	public String getType(Uri uri) {
		return "br.nti.SigaaBiblio.provider/usuarios";
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		sqLite.insert(TABELA, "", arg1);
		return null;
	}

	@Override
	public boolean onCreate() {
		sqLite = new RepositorioLocalSigaa(getContext()).getReadableDatabase();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder query = new SQLiteQueryBuilder();

		query.setTables(TABELA);
		query.setProjectionMap(colunas);

		String orderBy;
		if (TextUtils.isEmpty(sortOrder))
			orderBy = Usuarios.DEFAULT_SORT_ORDER;
		else
			orderBy = sortOrder;

		// Query
		Cursor c = query.query(UsuarioProvider.sqLite, projection, selection,
				selectionArgs, null, null, orderBy);

		// Notifica o content provider
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {

		int count = 0;
		int changedRows = sqLite.update(TABELA, values, where, whereArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
