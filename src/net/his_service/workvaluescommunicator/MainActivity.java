package net.his_service.workvaluescommunicator;

import org.apache.http.protocol.HTTP;

import net.his_service.workvaluescommunicator.DisplayMessageActivity;
import net.his_service.workvaluescommunicator.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

//	public final static String RECIPIENT_EMAIL = ;
	public final static String THANKS_MESSAGE = "Thanks!";
	public final static String STATE_DONE = "net.his_service.done";
	protected boolean mDone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			mDone = savedInstanceState.getBoolean(STATE_DONE);
		} else {
			mDone = false;
		}

		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mDone) {
			nextPage();
		}
	}

//	private void clearForm() {
//		String answerLookup = "";
//		Spinner questionSpinner = null;
//		int id = -1;
//
//		for (int i = 1; i <= 17; i++) {
//			answerLookup = "Question" + Integer.toString(i) + "Spinner";
//			id = getResources().getIdentifier(answerLookup, "id",
//					this.getPackageName());
//			questionSpinner = (Spinner) findViewById(id);
//
//			// clear out spinners - totally not Single-Responsibility ;-)
//			questionSpinner.setAdapter(null);
//			questionSpinner.setOnItemSelectedListener(null);
//		}
//	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putBoolean(STATE_DONE, mDone);
		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	private void nextPage() {
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private String questionsAndAnswers() {
		String response = "";
		String questionLookup = "";
		String answerLookup = "";
		Spinner questionSpinner = null;

		int id = -1;

		for (int i = 1; i <= 17; i++) {
			questionLookup = "question" + Integer.toString(i);
			id = getResources().getIdentifier(questionLookup, "string",
					this.getPackageName());
			response += getString(id) + ":\n";
			answerLookup = "Question" + Integer.toString(i) + "Spinner";
			id = getResources().getIdentifier(answerLookup, "id",
					this.getPackageName());
			questionSpinner = (Spinner) findViewById(id);
			response += "\t" + questionSpinner.getSelectedItem().toString()
					+ "\n\n";

		}

		return response;
	}

	private String emailMessage() {
		return "From:\n\t" + employeeEmail() + "\n\n" + questionsAndAnswers();
	}

	private String employeeEmail() {
		final EditText editText = (EditText) findViewById(R.id.EditTextEmail);
		return editText.getText().toString();
	}

	/** Called when the user clicks the Send button */
	public void sendMessage(View view) {
		Intent intent = new Intent(Intent.ACTION_SEND);

		intent.setType(HTTP.PLAIN_TEXT_TYPE);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.recipient_email) });
		intent.putExtra(Intent.EXTRA_SUBJECT, "Work Values Preferences from: "
				+ employeeEmail());
		intent.putExtra(Intent.EXTRA_TEXT, emailMessage());

		Toast.makeText(getBaseContext(), THANKS_MESSAGE, Toast.LENGTH_LONG)
				.show();
		mDone = true;
		startActivity(intent);
	}

}
