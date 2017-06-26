package org.example.gayathri.fwstorageactivityex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    static final int PICK_CONTACT_REQUEST = 0;
    EditText edtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: Having network connection "+haveNetworkConnection());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(haveNetworkConnection()){

            Button btnActivity = (Button) findViewById(R.id.btnActivity);
            Button btnFragOne = (Button) findViewById(R.id.btnFragOne);
            Button btnFragTwo = (Button) findViewById(R.id.btnFragTwo);
            Button btnSharedPrefSave = (Button) findViewById(R.id.btnSharedPref);
            Button btnSharedPrefRetrieve = (Button) findViewById(R.id.btnSharedPrefRetrieve);
            Button btnStoreFileSave = (Button) findViewById(R.id.btnStoreFileSave);
            Button btnRetrieveInFile = (Button) findViewById(R.id.btnRetrieveInFile);
            Button btnDBStore = (Button) findViewById(R.id.btnDBStore);
            edtName = (EditText) findViewById(R.id.etNameMain);


            btnActivity.setOnClickListener(this);
            btnFragOne.setOnClickListener(this);
            btnFragTwo.setOnClickListener(this);
            btnSharedPrefRetrieve.setOnClickListener(this);
            btnSharedPrefSave.setOnClickListener(this);
            btnStoreFileSave.setOnClickListener(this);
            btnRetrieveInFile.setOnClickListener(this);
            btnDBStore.setOnClickListener(this);
        } else {
            Log.d(TAG, "onCreate: Closing");
            Toast.makeText(this, "No Internet available cannot open the application", Toast.LENGTH_SHORT).show();
            finish();
        }


    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
//        for (NetworkInfo ni : netInfo) {
//            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
//                if (ni.isConnected())
//                    haveConnectedWifi = true;
//            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
//                if (ni.isConnected())
//                    haveConnectedMobile = true;
//        }
//        return haveConnectedWifi || haveConnectedMobile;

        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnActivity :
                Intent i = new Intent(this,Main2Activity.class);
                startActivityForResult(i,5);
                Log.d(TAG, "onClick: "+getChangingConfigurations()+" nn "+getApplicationContext()+ " n "+getApplication()+" m "+getComponentName());
                break;
            case R.id.btnFragOne :
                FragOne f1=new FragOne();
                FragmentTransaction f = getSupportFragmentManager().beginTransaction();
                f.addToBackStack(f1.getTag());
                Log.d(TAG, "onClick: f1.getTag()"+f1.getTag());
                f.replace(R.id.frmMain,f1);
                f.commit();
                break;
            case R.id.btnFragTwo :
                FragTwo f2=new FragTwo();
                FragmentTransaction ff = getSupportFragmentManager().beginTransaction();
                ff.addToBackStack(null);
                ff.replace(R.id.frmMain,f2);
                ff.commit();
                break;
            case R.id.btnSharedPref :
                String dataStored = edtName.getText().toString();
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor e=sp.edit();
                e.putString("name",dataStored);
                e.commit();
                break;
            case R.id.btnSharedPrefRetrieve :
                SharedPreferences spe = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String nameVal = spe.getString("name",null);
                Toast.makeText(this, nameVal, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnStoreFileSave :
                try{
                    OutputStreamWriter out = new OutputStreamWriter(getApplicationContext().openFileOutput("Test.txt",MODE_APPEND));
                    out.write(edtName.getText().toString());
                    out.close();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                break;
            case R.id.btnRetrieveInFile :
                String r = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    InputStreamReader isr = new InputStreamReader(getApplicationContext().openFileInput("Test.txt"));
                    BufferedReader b = new BufferedReader(isr);
                    while((r=b.readLine())!=null){
                        stringBuilder.append(r);
                    }
                    Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                break;
            case R.id.btnDBStore :
//                Intent in = new Intent(this,DBActivity.class);
//                startActivity(in);
                startService(new Intent(this,MyIntentService.class));


                break;
            default:
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 5){
            Toast.makeText(this, requestCode + " and "+resultCode+ " and "+data.getExtras().getString("name"), Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.

                data.setAction(Intent.ACTION_VIEW);

                startActivity(data);

                        //Intent.ACTION_VIEW, data));
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // When the user center presses, let them pick a contact.
            Intent intent = new Intent ( Intent.ACTION_GET_CONTENT );
            intent.setType ( ContactsContract.Contacts.CONTENT_ITEM_TYPE );
            startActivityForResult ( intent, PICK_CONTACT_REQUEST );

            //startActivityForResult(new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts")),
                    //PICK_CONTACT_REQUEST);
            return true;
        }
        return false;
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }
}
