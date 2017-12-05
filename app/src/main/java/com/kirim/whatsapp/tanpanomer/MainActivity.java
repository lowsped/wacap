package com.kirim.whatsapp.tanpanomer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    Button button1;
    private Button kirim;
    Button button3;
    String smsNumber;
    String pesanmu;
    private EditText pesan;
    private EditText nomer;
    InterstitialAd mInterstitialAd;
    Context context;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);

        if(!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
        else {
            Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
        }

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1142409875326374/7964699322");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdOpened() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdLeftApplication() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdClosed() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

        this.nomer = (EditText) findViewById(R.id.editText2);
        this.pesan = (EditText) findViewById(R.id.editText1);
        this.kirim = (Button) findViewById(R.id.button2);
        b1=(Button)findViewById(R.id.button3);


        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);


        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                ClipData abc = myClipboard.getPrimaryClip();
                ClipData.Item item = abc.getItemAt(0);

                String text = item.getText().toString();
                nomer.setText(text);

                Toast.makeText(getApplicationContext(), "Text Pasted",Toast.LENGTH_SHORT).show();

            }
        });



        button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                String myUri = null;
                try {
                    myUri = "whatsapp://send/?text=" + URLEncoder.encode(MainActivity.this.pesan.getText().toString(), "UTF-8") + "&phone=" + MainActivity.this.nomer.getText().toString();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();


                }
                Intent i = new Intent("android.intent.action.VIEW", Uri.parse(myUri));
                i.setFlags(268435456);
                MainActivity.this.startActivity(i);

            }
        });





        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myUri = null;
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }

                String x = MainActivity.this.nomer.getText().toString();

                if (!TextUtils.isEmpty(x)) {
                    String tlp;
                    if (x.substring(0, 1).equalsIgnoreCase("0")) {
                        tlp = "62" + x.substring(1, x.length());
                    } else {
                        tlp = x;

                    }


                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(tlp) + "@s.whatsapp.net");
                    MainActivity.this.startActivity(sendIntent);



                     }

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


        if (id == R.id.share) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_SUBJECT, "Soal pretest UKG, SIM PKB, SERTIFIKASI");
            share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.soal.ukg.plpg.pretest.sertifikasi");
            startActivity(Intent.createChooser(share, "Share Aps!"));
            Toast.makeText(this, "share aps", Toast.LENGTH_LONG).show();
            return true;

        }

        if (id == R.id.more) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=pemikir%20versi%20baru"));
            startActivity(intent);
            Toast.makeText(this, "more aps", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.abot) {
            Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse("fb://profile/100003203346146"));
            startActivity(intent);
            Toast.makeText(this, "my facebook", Toast.LENGTH_LONG).show();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;

    }
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Pastikan terhubung dengan INTERNET, Saat membuka aplikasi. Terima Kasih.. ");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;

    }
}