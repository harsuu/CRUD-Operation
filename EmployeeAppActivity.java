package com.vrs.employeeapp;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class EmployeeAppActivity extends Activity implements OnClickListener {
	String selected_ID = "";
	EditText txtEname, txtDesig, txtSalary;
	Button btnAddEmployee, btnUpdate, btnDelete;
	ListView lvEmployees;
	DBHelper helper;
	SQLiteDatabase db;
	SimpleCursorAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		helper = new DBHelper(this);
		txtEname = (EditText) findViewById(R.id.txtEname);
		txtDesig = (EditText) findViewById(R.id.txtDesig);
		txtSalary = (EditText) findViewById(R.id.txtSalary);
		lvEmployees = (ListView) findViewById(R.id.lvEmployees);

		btnAddEmployee = (Button) findViewById(R.id.btnAdd);
		btnUpdate = (Button) findViewById(R.id.btnUpdate);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnAddEmployee.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		lvEmployees.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {
				String name, desig, salary;
				Cursor row = (Cursor) adapter.getItemAtPosition(position);
				selected_ID = row.getString(0);
				name = row.getString(1);
				desig = row.getString(2);
				salary = row.getString(3);

				txtEname.setText(name);
				txtDesig.setText(desig);
				txtSalary.setText(salary);
			}
		});
		fetchData();
	}

	@Override
	public void onClick(View v) {
		if (v == btnAddEmployee) {
			ContentValues values = new ContentValues();
			values.put(DBHelper.C_ENAME, txtEname.getText().toString());
			values.put(DBHelper.C_DESIGNATION, txtDesig.getText().toString());
			values.put(DBHelper.C_SALARY, txtSalary.getText().toString());
			db = helper.getWritableDatabase();
			db.insert(DBHelper.TABLE, null, values);
			db.close();
			clearFields();
			Toast.makeText(this, "Employee Added Successfully",
					Toast.LENGTH_LONG).show();
			fetchData();
		}
		if (v == btnUpdate) {
			ContentValues values = new ContentValues();
			values.put(DBHelper.C_ENAME, txtEname.getText().toString());
			values.put(DBHelper.C_DESIGNATION, txtDesig.getText().toString());
			values.put(DBHelper.C_SALARY, txtSalary.getText().toString());
			db = helper.getWritableDatabase();
			db.update(DBHelper.TABLE, values, DBHelper.C_ID + "=?",
					new String[] { selected_ID });
			db.close();
			fetchData();
			Toast.makeText(this, "Record Updated Successfully",
					Toast.LENGTH_LONG).show();
			clearFields();

		}
		if (v == btnDelete) {
			db = helper.getWritableDatabase();
			db.delete(DBHelper.TABLE, DBHelper.C_ID + "=?",
					new String[] { selected_ID });
			db.close();
			fetchData();
			Toast.makeText(this, "Record Deleted Successfully",
					Toast.LENGTH_LONG).show();
			clearFields();
		}
	}
	private void clearFields() {
		txtEname.setText("");
		txtDesig.setText("");
		txtSalary.setText("");
	}
 	private void fetchData() {
		db = helper.getReadableDatabase();
		Cursor c = db.query(DBHelper.TABLE, null, null, null, null, null, null);
		adapter = new SimpleCursorAdapter(
				this,
				R.layout.row,
				c,
				new String[] { DBHelper.C_ENAME, DBHelper.C_SALARY,
						DBHelper.C_DESIGNATION },
				new int[] { R.id.lblEname, R.id.lblSalary, R.id.lblDesignation });
		lvEmployees.setAdapter(adapter);
	}
}