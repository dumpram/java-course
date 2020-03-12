package hr.infinum.fer.ip47055;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * Klasa za pregledavanje korisnika.
 * 
 * @author Ivan Pavić
 * 
 */
public class ProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile2);

		final String name = getIntent().getExtras().getString("name");
		final String number = getIntent().getExtras().getString("number");
		final String email = getIntent().getExtras().getString("email");
		final String note = getIntent().getExtras().getString("note");
		final String facebook = getIntent().getExtras().getString("facebook");

		TextView nameLabel = (TextView) findViewById(R.id.tvName);
		TextView numberLabel = (TextView) findViewById(R.id.tvNumber);
		TextView emailLabel = (TextView) findViewById(R.id.tvEmail);
		TextView noteLabel = (TextView) findViewById(R.id.tvNote);
		TextView facebookLabel = (TextView) findViewById(R.id.tvFacebook);

		nameLabel.setText("Ime: " + name);
		numberLabel.setText("Broj: " + number);
		emailLabel.setText("E-mail: " + email);
		noteLabel.setText("Note: " + note);
		facebookLabel.setText("Facebook: " + facebook);

		Button callButton = (Button) findViewById(R.id.btnCall);

		Button faceButton = (Button) findViewById(R.id.btnFace);

		callButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
				startActivity(intent);
			}
		});

		faceButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebook));
				startActivity(intent);
			}
		});

	}
}
