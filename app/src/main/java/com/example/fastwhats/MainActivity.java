package com.example.fastwhats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton buStart;
    EditText etNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buStart = findViewById(R.id.bu_start);
        etNum = findViewById(R.id.ed_num);

        init();

    }

    private void init() {
        buStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNum != null)
                    orderFun(MainActivity.this, etNum.getText().toString().trim().replaceAll(" ",""));
            }
        });
    }


    public void orderFun(Context context, String whatsNum) {

        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp", this);
        if (isWhatsappInstalled) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            // if(id == Constanes.FAKE_ID)
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "i'm using fastWhats");

            //Directly send to specific mobile number...
            whatsappIntent.putExtra("jid", "2" + whatsNum + "@s.whatsapp.net");
            startActivity(whatsappIntent);
        } else {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp&hl=en"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }


    public boolean whatsappInstalledOrNot(String uri, Context context) {
        PackageManager pm = this.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
