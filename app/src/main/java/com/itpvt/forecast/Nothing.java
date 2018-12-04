package com.itpvt.forecast;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;

public class Nothing extends AppCompatActivity {
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nothing);
        FloatingActionButton face= (FloatingActionButton) findViewById(R.id.face);
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent facebookIntent = openFacebook(Nothing.this);
                startActivity(facebookIntent);
                //    fb://page/100004757891858

            }
        });
        FloatingActionButton whats = (FloatingActionButton) findViewById(R.id.whatsapp);
        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri uri  =Uri.parse("smsto:"+"+923161433343");
//                Intent intent =new Intent(Intent.ACTION_SENDTO,uri);
//                intent.setPackage("com.whatsapp");
//                startActivity(intent);

                String smsNumber = "923174295010";

                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix
                startActivity(sendIntent);
            }
        });


        btn=(Button)findViewById(R.id.not);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(Nothing.this, MainActivity.class);
                startActivity(i);


            }
        });
    }
    public static Intent openFacebook(Context c) {
        try {
            c.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/100004757891858"));
        } catch (Exception e){

            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/rdtex2016/"));
        }


    }
}
