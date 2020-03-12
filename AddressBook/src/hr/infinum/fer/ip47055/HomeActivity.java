package hr.infinum.fer.ip47055;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
/**
 * Glavna, pocetna aktivnost koja se pali na pocetku rada.
 * 
 * @author Ivan Pavić
 * 
 */
public class HomeActivity extends ActionBarActivity {
	/**
	 * Statička lista kontakata.
	 */
	static List<Contact> contacts;
	/**
	 * Instanca {@link ListView}.
	 */
	private ListView listView;
	/**
	 * {@link ContactArrayAdapter} adapter.
	 */
	private ContactArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		InputStream in = null;
		try {
			in = getAssets().open("people.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (in != null) {
			try {
				contacts = readContacts(in);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Button button = (Button) findViewById(R.id.addBtn);

		listView = (ListView) findViewById(R.id.listView);

		adapter = new ContactArrayAdapter(this, 0, contacts);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
				intent.putExtra("name", contacts.get(position).getName());
				intent.putExtra("number", contacts.get(position).getNumber());
				intent.putExtra("email", contacts.get(position).getEmail());
				intent.putExtra("note", contacts.get(position).getNote());
				intent.putExtra("facebook", contacts.get(position).getFacebook());
				startActivity(intent);
			}

		});

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, AddContactActivity.class);
				startActivityForResult(intent, 0);
			}
		});

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);

		if (arg1 == RESULT_OK) {
			contacts.add(new Contact(arg2.getExtras().getString("name"), arg2.getExtras().getString("number"), arg2
					.getExtras().getString("email"), arg2.getExtras().getString("note"), arg2.getExtras().getString(
					"facebook")));
			Toast.makeText(this, "Novi kontakt dodan!", Toast.LENGTH_SHORT).show();
			adapter.notifyDataSetChanged();
		}
	}
	private static List<Contact> readContacts(InputStream in) throws JSONException {
		List<Contact> forExport = new ArrayList<Contact>();

		StringBuffer sb = new StringBuffer();

		byte[] buffer = new byte[1024];

		try {
			while (in.read(buffer) != -1) {
				sb.append(new String(buffer, "UTF-8"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject obj = null;
		try {
			obj = new JSONObject(sb.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONArray array = obj.getJSONArray("people");

		for (int i = 0; i < array.length(); i++) {
			JSONObject object = ((JSONObject) array.get(i)).getJSONObject("person");
			String name = object.getString("name");
			String number = (String) object.get("phone");
			String email = (String) object.get("email");
			String note = (String) object.get("note");
			String facebook = (String) object.get("facebook_profile");
			forExport.add(new Contact(name, number, email, note, facebook));
		}
		return forExport;

	}
}
