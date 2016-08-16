package com.annanya.notes.classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqlOpenHelper extends SQLiteOpenHelper {

	public MySqlOpenHelper(Context context, String name, CursorFactory factory,
						   int version) {
		super(context, name, factory, version);


	}

	@Override
	public void onCreate(SQLiteDatabase sd) {


		sd.execSQL(Config.CREATEQUERY);

		sd.execSQL(Config.SELECTQUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

		
	}

}
