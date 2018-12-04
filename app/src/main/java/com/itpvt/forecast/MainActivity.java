package com.itpvt.forecast;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener,BaseSliderView.OnSliderClickListener {

    SliderLayout sliderLayout;
    HashMap<String, Integer> HashMapForURL ;
    int[] images={  R.drawable.ban, R.drawable.bann, R.drawable.bannn};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar



        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.main_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        sliderLayout= (SliderLayout) findViewById(R.id.slider);

        FloatingActionButton whats = (FloatingActionButton) findViewById(R.id.whatsapp);
        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri uri  =Uri.parse("smsto:"+"+923161433343");
//                Intent intent =new Intent(Intent.ACTION_SENDTO,uri);
//                intent.setPackage("com.whatsapp");
//                startActivity(intent);
whatsapp();

            }
        });
       // ImageView instaa = (ImageView) findViewById(R.id.insta);


        FloatingActionButton face= (FloatingActionButton) findViewById(R.id.face);
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent facebookIntent = openFacebook(MainActivity.this);
                startActivity(facebookIntent);
            //    fb://page/100004757891858

            }
        });
        ImageView bag= (ImageView) findViewById(R.id.bag);
        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(MainActivity.this, My_Cart.class);
                startActivity(i);

            }
        });








//        ImageView img1 = (ImageView) findViewById(R.id.mennn);
//        img1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Sub_Categories.class);
//                intent.putExtra("id", "4");
//                intent.putExtra("title", "Hello");
//
//                startActivity(intent);
//
//            }
//        });
//
//
//        ImageView img2 = (ImageView) findViewById(R.id.womennn);
//        img2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Sub_Categories.class);
//                intent.putExtra("id", "5");
//                intent.putExtra("title", "Hello");
//
//                startActivity(intent);
//
//            }
//        });
//
//        ImageView img3 = (ImageView) findViewById(R.id.newinnn);
//        img3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, All_Products_Design.class);
//                intent.putExtra("id", "3");
//                intent.putExtra("title", "Hello");
//
//                startActivity(intent);
//
//            }
//        });
//        ImageView img4 = (ImageView) findViewById(R.id.saleee);
//        img4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, All_Products_Design.class);
//                intent.putExtra("id", "7");
//                intent.putExtra("title", "Hello");
//
//                startActivity(intent);
//
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ImageView img1 = (ImageView) findViewById(R.id.mennn);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Sub_Categories.class);
                intent.putExtra("id", "4");
                intent.putExtra("title", "Hello");

                startActivity(intent);

            }
        });


        ImageView img2 = (ImageView) findViewById(R.id.womennn);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Sub_Categories.class);
                intent.putExtra("id", "5");
                intent.putExtra("title", "Hello");

                startActivity(intent);

            }
        });

        ImageView img3 = (ImageView) findViewById(R.id.newinnn);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, All_Products_Design.class);
                intent.putExtra("id", "3");
                intent.putExtra("title", "Hello");

                startActivity(intent);

            }
        });
        ImageView img4 = (ImageView) findViewById(R.id.saleee);
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, All_Products_Design.class);
                intent.putExtra("id", "7");
                intent.putExtra("title", "Hello");

                startActivity(intent);

            }
        });

      //  GetAllProducts();
        AddImagesUrlOnline();
    }

    private void whatsapp() {

        String smsNumber = "923174295010";

        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix
        startActivity(sendIntent);
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

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Home)
        {
            Intent intent=new Intent(MainActivity.this,MainActivity.class);
startActivity(intent);
finish();

        } else if (id == R.id.Categories){

            Intent intent = new Intent(MainActivity.this, Categery.class);


            startActivity(intent);
            finish();

        } else if (id == R.id.Cart) {

           Intent i= new Intent(MainActivity.this, My_Cart.class);
            startActivity(i);


        } else if (id == R.id.Whatsapp)
        {


            whatsapp();

        } else if (id == R.id.Innovators)
        {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://itpvt.net/"));
            startActivity(myIntent);
finish();
        }else if (id == R.id.web) {

            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://forecast.com.pk/"));
            startActivity(myIntent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

        for(String name : HashMapForURL.keySet()){

            TextSliderView textSliderView = new TextSliderView(MainActivity.this.getApplicationContext());

            textSliderView
                    .description(name)
                    .image(HashMapForURL.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(8000);
    }

}


