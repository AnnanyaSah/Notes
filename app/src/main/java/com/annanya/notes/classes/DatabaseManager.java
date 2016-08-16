package com.annanya.notes.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.annanya.notes.fragments.Note;

public class DatabaseManager {
	MySqlOpenHelper mh=null;
	SQLiteDatabase sdb=null;
	public DatabaseManager(Context context)
	{
	mh=new MySqlOpenHelper(context, Config.DBNAME, null,1);
	}
	public void OpenDb()
	{
		sdb=mh.getWritableDatabase();
	}
	public void CloseDb()
	{
		mh.close();
	}
	
	public Cursor viewData()
	{
		Cursor c=null;
		c=sdb.query(Config.TBLNAME,null, null,null,null, null, null);
		return c;
	}

	public int updateData(String id, String cost,String title)
	{
		String[]args={id};
		ContentValues cv=new ContentValues();
		cv.put(Config.PTEXT,cost);
		cv.put(Config.PTITLE,title);
		int rw=sdb.update(Config.TBLNAME,cv,Config.PID+"=?",args);
		return rw;
		
	}
	public long insertData(String name, String cost) {

		ContentValues cv=new ContentValues();
		cv.put(Config.PTITLE,name);
		cv.put(Config.PTEXT, cost);

		long rw=sdb.insert(Config.TBLNAME,null,cv);
		return rw;
		
	}

	public void deleteData(int id){
		sdb.delete(Config.TBLNAME,Config.PID+ " = ?",new String[]{String.valueOf(id)});
	}

	public NoteData getNote(String id){
		Cursor c=null;
		c=sdb.query(Config.TBLNAME,new String[]{Config.PTITLE,Config.PTEXT},Config.PID+"=?",new String[]{id},null,null,null,null);
		if (c != null)
			c.moveToFirst();

		NoteData note = new NoteData(c.getString(0),
				c.getString(1),Integer.parseInt(id));

		return note;
	}
}
