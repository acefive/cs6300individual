package edu.gatech.seclass.sdpencryptor;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Character.isLetter;
import static java.lang.Character.isUpperCase;

public class SDPEncryptorActivity extends AppCompatActivity {

    private EditText messageText;
    private EditText shiftNumber;
    private EditText rotateNumber;
    private Button encryptButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdpencryptor);

        messageText = (EditText) findViewById(R.id.messageText);
        shiftNumber = (EditText) findViewById(R.id.shiftNumber);
        rotateNumber = (EditText) findViewById(R.id.rotateNumber);
        encryptButton = (Button) findViewById(R.id.encryptButton);
        resultText = (TextView) findViewById(R.id.resultText);
    }

    public String getResultText(String messageText, int shiftNumber, int rotateNumber) {
        char[] convertedMessage = messageText.toCharArray();
        for(int i = 0; i < convertedMessage.length; i++) {
            if(isLetter(convertedMessage[i])) {
                if (isUpperCase(convertedMessage[i])) {
                    convertedMessage[i] = (char) ((convertedMessage[i] + shiftNumber - 'A') % 26 + 'A');
                } else {
                    convertedMessage[i] = (char) ((convertedMessage[i] + shiftNumber - 'a') % 26 + 'a');
                }
            }
        }
        char[] copy = convertedMessage.clone();
        int temp = convertedMessage.length - rotateNumber;
        while(temp < 0 ) { temp += convertedMessage.length; }
        for(int i = 0; i < convertedMessage.length; i++) { copy[i] = convertedMessage[(i+temp) % convertedMessage.length]; }
        return new String(copy);
    }

    public void handleClick(View view) {
        messageText.setError(null);
        shiftNumber.setError(null);
        rotateNumber.setError(null);
        if(view.getId() ==  R.id.encryptButton) {
            String messageString = messageText.getText().toString();
            if(messageString == null || messageString.isEmpty() || !messageString.matches(".*[a-zA-Z].*")) {
                messageText.setError("Alphabetic Message Required");
                resultText.setText("");
            }
            String shiftString = shiftNumber.getText().toString();
            String rotateString = rotateNumber.getText().toString();
            if(shiftString == null || shiftString.isEmpty()) {
                shiftNumber.setError("Must Be Between 0 And 25");
                if(rotateString == null || rotateString.isEmpty())
                    rotateNumber.setError("Positive number required!");
                resultText.setText("");
                return;
            }
            if(rotateString == null || rotateString.isEmpty()) {
                rotateNumber.setError("Positive number required!");
                resultText.setText("");
                return;
            }
            int shiftInt = Integer.parseInt(shiftNumber.getText().toString());
            int rotateInt = Integer.parseInt(rotateNumber.getText().toString());
            if(shiftInt < 0 || shiftInt > 25) {
                shiftNumber.setError("Must Be Between 0 And 25");
                resultText.setText("");
                return;
            }
            if(shiftInt == 0 && rotateInt == 0) {
                shiftNumber.setError("No Encryption Applied");
                rotateNumber.setError("No Encryption Applied");
                resultText.setText("");
                return;
            }
            if(rotateInt < 0) {
                rotateNumber.setError("Positive number required!");
                resultText.setText("");

                Context context = getApplicationContext();
                CharSequence text = "Positive number required!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }
            resultText.setText(getResultText(messageString, shiftInt, rotateInt));
            return;
        }
    }
}
