package com.itpvt.forecast;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;

public class Categery extends AppCompatActivity  implements BaseSliderView.OnSliderClickListener {

    SliderLayout sliderLayout;
    HashMap<String, Integer> HashMapForURL;
    int[] images = {R.drawable.ban, R.drawable.bann, R.drawable.bannn};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_categery);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categery.this, MainActivity.class);
                startActivity(intent);
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

        FloatingActionButton face = (FloatingActionButton) findViewById(R.id.face);
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent facebookIntent = openFacebook(Categery.this);
                startActivity(facebookIntent);


            }
        });
        ImageView bag = (ImageView) findViewById(R.id.bag);
        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Categery.this, My_Cart.class);
                startActivity(i);

            }
        });
        ImageView img1= (ImageView) findViewById(R.id.womennnc);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Categery.this,Sub_Categories.class);
                i.putExtra("id", "5");
                i.putExtra("title", "Hello");

                startActivity(i);

            }
        });
        ImageView img2= (ImageView) findViewById(R.id.mennnc);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Categery.this,Sub_Categories.class);
                i.putExtra("id", "4");
                i.putExtra("title", "Hello");

                startActivity(i);

            }
        });

AddImagesUrlOnline();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    private void AddImagesUrlOnline()

    {

        HashMapForURL = new HashMap<String, Integer>();

        HashMapForURL.put(" ", R.drawable.ban);
        HashMapForURL.put("  ", R.drawable.bann);
        HashMapForURL.put("   ", R.drawable.bannn);

        callSlider();

    }

    private void callSlider() {

        for (String name : HashMapForURL.keySet()) {

            TextSliderView textSliderView = new TextSliderView(Categery.this.getApplicationContext());

            textSliderView
                    .description(name)
                    .image(HashMapForURL.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("extra", name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(8000);
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
