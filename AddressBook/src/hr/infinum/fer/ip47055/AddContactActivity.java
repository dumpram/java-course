package hr.infinum.fer.ip47055;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/**
 * {@link Activity} za dodavanje kontakata.
 * 
 * @author Ivan Pavić
 * 
 */
public class AddContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_activity);

		// TextView textLabel = (TextView) findViewById(R.id.tvLabel);

		final EditText editName = (EditText) findViewById(R.id.etName);
		final EditText editNumber = (EditText) findViewById(R.id.etNumber);
		final EditText editEmail = (EditText) findViewById(R.id.etEmail);
		final EditText editNote = (EditText) findViewById(R.id.etNote);
		final EditText editFacebook = (EditText) findViewById(R.id.etFacebook);

		Button submitButton = (Button) findViewById(R.id.btnSave);
		Button cancelButton = (Button) findViewById(R.id.btnCancel);

		// String label = getIntent().getExtras().getString("label");

		// textLabel.setText(label);

		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = editName.getText().toString();
				String number = editNumber.getText().toString();
				String email = editEmail.getText().toString();
				String note = editNote.getText().toString();
				String facebook = editFacebook.getText().toString();
				Intent data = new Intent();
				data.putExtra("name", name);
				data.putExtra("number", number);
				data.putExtra("email", email);
				data.putExtra("note", note);
				data.putExtra("facebook", facebook);
				setResult(RESULT_OK, data);
				finish();
			}
		});

		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
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

}
