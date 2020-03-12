package hr.infinum.fer.ip47055;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/**
 * Adapter za rad s listama.
 * 
 * @author Ivan Pavić
 * 
 */
public class ContactArrayAdapter extends ArrayAdapter<Contact> {
	/**
	 * Kontekst liste.
	 */
	private final Context context;
	/**
	 * Konstruktor prima sljedece parametre.
	 * 
	 * @param context
	 *            {@link Context} liste
	 * @param textViewResourceId
	 *            identifikator
	 * @param contacts
	 *            kontakti
	 */
	public ContactArrayAdapter(Context context, int textViewResourceId, List<Contact> contacts) {
		super(context, textViewResourceId, contacts);

		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);

			convertView = inflater.inflate(R.layout.list_item, parent, false);
		}

		TextView nameText = (TextView) convertView.findViewById(R.id.tvName);

		TextView number = (TextView) convertView.findViewById(R.id.tvNumber);

		TextView email = (TextView) convertView.findViewById(R.id.tvEmail);

		nameText.setText(getItem(position).getName());
		number.setText(getItem(position).getNumber());
		email.setText(getItem(position).getEmail());

		return convertView;
	}

}
