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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        dogBreed = (EditText) findViewById(R.id.dogBreed);

    }

    public void backToMap(View v) {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("Dog breed", dogBreed.getText().toString());
        startActivity(intent);
    }
}
