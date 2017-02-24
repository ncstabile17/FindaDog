package nickinc.findadog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InfoActivity extends AppCompatActivity {
    EditText dogBreed;
    EditText nameText;
    EditText timeText;
    Dog newDog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        dogBreed = (EditText) findViewById(R.id.dogBreed);
        nameText = (EditText) findViewById(R.id.nameText);
        timeText = (EditText) findViewById(R.id.timeText);

    }

    public void backToMap(View v) {
        if (!(nameText.getText().toString().equals(""))) {
            newDog = new Dog(dogBreed.getText().toString(), timeText.getText().toString(), nameText.getText().toString());
        }
        else {
            newDog = new Dog(dogBreed.getText().toString(), timeText.getText().toString());
        }
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("Dog", newDog);
        startActivity(intent);
    }
}
