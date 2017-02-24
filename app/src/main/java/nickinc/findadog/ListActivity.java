package nickinc.findadog;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ListView dogList;
    static LatLng newLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        dogList = (ListView) findViewById(R.id.dogList);

        ArrayList<String> dogBreeds = new ArrayList<>();
        if (MapsActivity.dogs.size() > 0) {
            for (Dog dog:MapsActivity.dogs) {
                dogBreeds.add(dog.dogBreed);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dogBreeds);
            dogList.setAdapter(arrayAdapter);
        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("No dogs saved yet. Go find some!");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //
                        }
                    });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


       // dogList.setOnItemClickListener(itemClickListener);
    }
}
