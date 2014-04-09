package com.AndroidExamApp.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "partyDB.db";
	private static final String TABLE_PARTY = "parties";
	private static final String TABLE_PARTY_PROMISES = "promises";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_FILENAME = "filename";
	public static final String COLUMN_PLUS = "plus";
	public static final String COLUMN_EQUALS = "equals";
	public static final String COLUMN_MINUS = "minus";
	
	public static final String COLUMN_ID2 = "_id";
	public static final String COLUMN_ID2_FOREIGN = "_id_foreign";
	public static final String COLUMN_HASHMAP1 = "promise";
	public static final String COLUMN_HASHMAP2 = "promise_description";
	
	public MyDBHandler(Context context, String name, CursorFactory factory, int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PARTY_TABLE = "CREATE TABLE " +
				TABLE_PARTY + "("
	             + COLUMN_ID + " INTEGER PRIMARY KEY," 
	             + COLUMN_NAME + " TEXT," 
	             + COLUMN_DESCRIPTION + " TEXT,"
	             + COLUMN_FILENAME + " TEXT,"
	             + COLUMN_PLUS + " INT,"
	             + COLUMN_EQUALS + " INT,"
	             + COLUMN_MINUS + " INT"
	             + ")";
		db.execSQL(CREATE_PARTY_TABLE);
		
		String CREATE_PARTY_PROMISES_TABLE = "CREATE TABLE " +
				TABLE_PARTY_PROMISES + "("
	             + COLUMN_ID2 + " INTEGER PRIMARY KEY," 
	             + COLUMN_ID2_FOREIGN + " INTEGER,"
	             + COLUMN_HASHMAP1 + " TEXT," 
	             + COLUMN_HASHMAP2 + " TEXT," 
	             + "FOREIGN KEY(" + COLUMN_ID2_FOREIGN + ") REFERENCES " + TABLE_PARTY + "(" + COLUMN_ID + ")"
	             + ")";
		db.execSQL(CREATE_PARTY_PROMISES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTY);
	    onCreate(db);
	}
	
	public void dropTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_PARTY);
		db.execSQL("delete from "+ TABLE_PARTY_PROMISES);
	}
	
	public void addParty(Party party) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, party.get_name());
        values.put(COLUMN_DESCRIPTION, party.get_description());
        values.put(COLUMN_FILENAME, party.get_filename());        
        
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PARTY, null, values);
        
        ContentValues values2 = new ContentValues();
        String query = "Select " + COLUMN_ID + " FROM " + TABLE_PARTY + " WHERE " + COLUMN_NAME + " =  \"" + party.get_name() + "\"";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        Iterator iterator = party.get_promises().keySet().iterator();
        while(iterator.hasNext()) {
        	String key=(String)iterator.next();
            String value=(String)party.get_promises().get(key);
        	values2.put(COLUMN_ID2_FOREIGN, cursor.getString(0));
        	values2.put(COLUMN_HASHMAP1, key);
        	values2.put(COLUMN_HASHMAP2, value);
        	db.insert(TABLE_PARTY_PROMISES, null, values2);
        }
        cursor.close();
        db.close();
	}
	
	public Party findParty(String partyname) {
		String query = "Select * FROM " + TABLE_PARTY + " WHERE " + COLUMN_NAME + " =  \"" + partyname + "\"";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		Party party = new Party();
		
		if (cursor.moveToFirst()) {
			cursor.moveToFirst();
			party.set_name(cursor.getString(1));
			party.set_description(cursor.getString(2));
			party.set_filename(cursor.getString(3));
//			party.set_plus_sign(Integer.parseInt((cursor.getString(4))));
//			party.set_equals_sign(Integer.parseInt(cursor.getString(5)));
//			party.set_minus_sign(Integer.parseInt(cursor.getString(6)));
			String query2 = "Select * FROM " + TABLE_PARTY_PROMISES + " WHERE " + COLUMN_ID2_FOREIGN + " =  \"" + cursor.getString(0) + "\"";
			Cursor cursor2 = db.rawQuery(query2, null);
			cursor2.moveToFirst();
			HashMap<String,String> temp = new HashMap<String,String>();
	        while(cursor2.moveToNext()!=false) {
	        	temp.put(cursor2.getString(2), cursor.getString(3));
	        }
	        party.set_promises(temp);
			cursor.close();
		} else {
			party = null;
		}
	    
		db.close();
		return party;
	}
	
	public boolean deleteParty(String partyname) {
		boolean result = false;
		String query = "Select * FROM " + TABLE_PARTY + " WHERE " + COLUMN_NAME + " =  \"" + partyname + "\"";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		Party party = new Party();
		
		if (cursor.moveToFirst()) {
			party.set_id(Integer.parseInt(cursor.getString(0)));
			db.delete(TABLE_PARTY, COLUMN_ID + " = ?",
		            new String[] { String.valueOf(party.get_id()) });
			db.delete(TABLE_PARTY_PROMISES, COLUMN_ID2_FOREIGN + " = ?",
		            new String[] { String.valueOf(party.get_id()) });
			cursor.close();
			result = true;
		}
	    
		db.close();
		return result;
	}
	
	public ArrayList<Party> getAll(){
		String query = "Select * FROM " + TABLE_PARTY;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		ArrayList<Party> result = new ArrayList<Party>();
		
		for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
			Party party = findParty(cursor.getString(1));			
			result.add(party);
		}
		
		return result;
	}

} 