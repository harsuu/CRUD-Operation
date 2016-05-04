package com.vrs.employeeapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper {
	static final String DATABASE = "empapp.db";
	static final int VERSION = 1;
	harsh
	static final String TABLE = "emp";
	static final String C_ID = "_id";
	static final String C_ENAME = "ename";
	static final String C_DESIGNATION = "designation";
	static final String C_SALARY = "salary";
	public DBHelper(Context context) {
		super(context, DATABASE, null, VERSION);

	}
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL("CREATE TABLE " + TABLE + " ( " + C_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + C_ENAME + " text, "
				+ C_DESIGNATION + " text, " + C_SALARY + " text )");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("Drop table " + TABLE);
		onCreate(db);
	}
}
