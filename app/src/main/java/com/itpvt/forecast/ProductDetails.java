package com.itpvt.forecast;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductDetails extends AppCompatActivity {

    private ProgressDialog loading;
    String P_id, sku;
    TextView name, tv_aval, tv_price, tv_disprice, p_description, pname;
    String p_type,p_size="",p_color="";
    String quantity1, orig, disco;
    String Image_Url = null;
    EditText ed_qty;
    Spinner s_color, s_size;
    String value_indexc = "", value_indexs = "";
    Button sizechart;
    Button Buy, add;
    ImageView imageView;
    LinearLayout spinners;
    String cart_no = null;
    float given;
    float enter;
    String Build_Sku;
    String color_name = "", size_name = "";
    String atr_id2, atr_id;
    ArrayList<Spinner_attribute_Pojo> arrayListcolor = new ArrayList<>();
    ArrayList<Spinner_attribute_Pojo> arrayListsize = new ArrayList<>();
    ImageView whatsapp;
    boolean checked,firsttime;
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.product_detail);





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView bag=(ImageView)findViewById(R.id.bag);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        checked=false;
        firsttime=false;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductDetails.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProductDetails.this,My_Cart.class);
                startActivity(intent);
                finish();
            }
        });


        final Intent intent = getIntent();
        P_id = intent.getStringExtra("product_id");
        sku = intent.getStringExtra("SKU");

           name= (TextView) findViewById(R.id.p_name);
        imageView = (ImageView) findViewById(R.id.p_image);
        ed_qty = (EditText) findViewById(R.id.pedit);
        tv_price = (TextView) findViewById(R.id.price);
        s_color = (Spinner) findViewById(R.id.spinner_color);
        s_size = (Spinner) findViewById(R.id.spinner_size);
        tv_aval = (TextView) findViewById(R.id.p_aval);
//        tv_qty=(TextView)findViewById(R.id.tv_qty);
        tv_disprice = (TextView) findViewById(R.id.p_disc);
        p_description=(TextView)findViewById(R.id.dprice);
        Buy = (Button) findViewById(R.id.pbtn);
         txt= (TextView) findViewById(R.id.tedit);

//        sizechart = (Button) findViewById(R.id.sizechart);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Image_Url == null) {
                    Toast.makeText(getApplicationContext(), "Network Connection Error No Image", Toast.LENGTH_SHORT).show();
               } else {
                  Intent intent=new Intent(ProductDetails.this,FullScreenImage.class);
                  intent.putExtra("URL",Image_Url);
                   startActivity(intent);
              }
                }
                 });


        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_CART, Context.MODE_PRIVATE);
                cart_no = sharedPreferences.getString(Config.SHARED_PREF_CART_NO, null);

                given = Float.valueOf(quantity1);
                enter = Float.valueOf(ed_qty.getText().toString());
                if(enter>given){

                    Toast.makeText(getApplicationContext(), "Please Reduce The Quantity", Toast.LENGTH_SHORT).show();



                }

               else {
                    if (cart_no == null) {
                        if (p_type.equals("configurable")) {
                            ADDTOCART();
                        } else {
                            given = Float.valueOf(quantity1);
                            enter = Float.valueOf(ed_qty.getText().toString());
                            if (given >= enter) {
                                ADDTOCART();
                            } else

                            {
                                Toast.makeText(getApplicationContext(), "Please Reduce The Quantity", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        if (p_type.equals("configurable")) {
                            ADDTOCARTWITHCARTNO();
                        } else {
                            given = Float.valueOf(quantity1);
                            enter = Float.valueOf(ed_qty.getText().toString());
                            if (given >= enter) {
                                ADDTOCARTWITHCARTNO();
                            } else

                            {
                                Toast.makeText(getApplicationContext(), "Please Reduce The Quantity", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
//

            }

        });

        s_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner_attribute_Pojo country = (Spinner_attribute_Pojo) parent.getSelectedItem();
                value_indexc = country.getValue_index().toString();
                size_name = country.getLabel().toString();
                if (checked)
                {
                if (!p_size.equals(size_name))
                {
                    BUILDSKU();
                }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //{
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Spinner_attribute_Pojo country = (Spinner_attribute_Pojo) parent.getSelectedItem();
//                value_indexc = country.getValue_index().toString();
//                size_name = country.getLabel().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
        // color wala spinner..
        s_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Spinner_attribute_Pojo country = (Spinner_attribute_Pojo) parent.getSelectedItem();
                value_indexs = country.getValue_index().toString();
                color_name = country.getLabel().toString();
                if (!firsttime)
                {
                    BUILDSKU();
                }
                if (checked)
                {
                if (!p_color.equals(color_name))
                {
                    BUILDSKU();
                }
                }
//                Toast.makeText(context, "Country ID: "+country.getId()+",  Country Name : "+country.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ///////////////////////////PRODUCT_DETAILS//////////////////////////////////////////////
// sb kch get key ga product ka next activity py....

        loading = ProgressDialog.show(ProductDetails.this, "Fetching...", "Please wait...", false, false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_PRODUCT_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();


                try {
                    JSONObject object = new JSONObject(response);
                    String product_id = object.getString("id");
                    String p_price = object.getString("price");
                    String p_sku = object.getString("sku");
                    String p_img_url = object.getString("img");
                    String p_des = object.getString("proName");
                    p_type = object.getString("type_id");
                    String p_quantity = object.getString("product_quantity");
                    String P_dis_price = object.getString("discount_price");
                    JSONArray quantity = object.getJSONArray("Qunatity");
                    JSONObject data = quantity.getJSONObject(0);
                    quantity1 = data.getString("qty");
                    orig = object.getString("price").replace(".0000","Rs");;
                    disco = object.getString("discount_price").replace(".0000","Rs");;
                    tv_disprice.setText(object.getString("des"));
                    Image_Url = object.getString("id");

                    Glide.with(ProductDetails.this).load(p_img_url).into(imageView);
                    name.setText(p_des);
                    tv_aval.setText("In Stock");
                    tv_price.setText(orig);
                    p_description.setText(disco);


// quantity of product//

                    if (p_quantity.equals("0")) {
//                      tv_aval.setText("Out Of Stock");
                        tv_aval.setText("Out Of Stock");
                        tv_aval.setTextColor(R.color.colorAccent);
                        Buy.setEnabled(false);
                        ed_qty.setVisibility(View.GONE);
                        s_color.setVisibility(View.GONE);
                        s_size.setVisibility(View.GONE);
                    } else if (p_type.equals("configurable") && !p_quantity.equals("0")) {
// buy krna sb visible hojay
                        Buy.setEnabled(true);
                        s_color.setVisibility(View.VISIBLE);
                        s_size.setVisibility(View.VISIBLE);
//                       spinners.setVisibility(View.VISIBLE);
                        ed_qty.setVisibility(View.VISIBLE);
                        productifConfigure();
                        // ed_qty.setVisibility(View.GONE);
                    } else if (p_type.equals("simple") && !p_quantity.equals("0")) {
                        ed_qty.setEnabled(true);
                        s_color.setVisibility(View.GONE);
                        s_size.setVisibility(View.GONE);
                        spinners.setVisibility(View.GONE);
//                        tv_qty.setText(quantity1);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // object.get("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Error",error.printStackTrace());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Network Connection Error", Toast.LENGTH_SHORT).show();
                //              Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("product_id", P_id);
                params.put("sku", sku);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }


    ////////////////////////ADD TO CART ON EXISTING ORDER//////////////////////////

    private void BUILDSKU()
    {

        if (cart_no == null) {
            if (p_type.equals("configurable")) {
                if (!color_name.equals("") && !size_name.equals("")) {
                    Build_Sku = sku + "-" + color_name + "-" + size_name;
                    ChekgivenQuantity();

                    //   ADDTOCART();
                } else if (!color_name.equals("") && size_name.equals("")) {
                    Build_Sku = sku + "-" + color_name;
                    ChekgivenQuantity();
                    //ADDTOCART();
                } else if (color_name.equals("") && !size_name.equals("")) {
                    Build_Sku = sku + "-" + size_name;
                    ChekgivenQuantity();
                    //ADDTOCART();
                } else if (color_name.equals("") && size_name.equals("")) {
                    Build_Sku = sku;
                    ChekgivenQuantity();
                }

            }



        } else {
            // agr ha wo to sb kch dekh k add kro product ko
            if (p_type.equals("configurable")) {
                if (!color_name.equals("") && !size_name.equals("")) {
                    Build_Sku = sku + "-" + color_name + "-" + size_name;
                    ChekgivenQuantity_2();
//                            ADDTOCART();
                } else if (!color_name.equals("") && size_name.equals("")) {
                    Build_Sku = sku + "-" + color_name;
                    ChekgivenQuantity_2();
                    //                          ADDTOCART();
                } else if (color_name.equals("") && !size_name.equals("")) {
                    Build_Sku = sku + "-" + size_name;
                    ChekgivenQuantity_2();
                    //                        ADDTOCART();
                } else if (color_name.equals("") && !size_name.equals("")) {
                    Build_Sku = sku;
                    ChekgivenQuantity_2();
                }
            }
        }
    }
    private void ADDTOCARTWITHCARTNO() {
        loading = ProgressDialog.show(this, "Adding...", "Please Wait...", false, false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_ADD_TO_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                try {
                    JSONObject result = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), "Product Added", Toast.LENGTH_SHORT).show();
                  ChoseOption();
//

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ProductDetails.this, response, Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Error",error.printStackTrace());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "This Product is Out of stock", Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("color_option", value_indexs);
                params.put("size_option", value_indexc);
                params.put("quantity", ed_qty.getText().toString());
                params.put("prod_id", P_id);
                params.put("cart_id", cart_no);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
    ////////////////////////////Getting details for configrable products/////////////////////////////////////////

    private void productifConfigure() {
        loading = ProgressDialog.show(this, "Getting...", "Configure Details...", false, false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_PRODUCT_DETAILS_CONFIGURE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loading.dismiss();
                try {
                    JSONArray config = new JSONArray(response);
                    int i = 0;
                    JSONObject attribute1 = config.getJSONObject(i);
                    String label = attribute1.getString("label");
                    atr_id = attribute1.getString("attribute_id");
                    if (atr_id.equals("92")) {
                        JSONArray value = attribute1.getJSONArray("values");
                        for (int j = 0; j < value.length(); j++) {
                            JSONObject data = value.getJSONObject(j);
                            String value_index = data.getString("value_index");
                            String label_index = data.getString("label");
                            arrayListcolor.add(new Spinner_attribute_Pojo(label_index, value_index));

                        }
// color k liye ha ye
                        ArrayAdapter<Spinner_attribute_Pojo> adapter = new
                                ArrayAdapter<Spinner_attribute_Pojo>(ProductDetails.this,
                                android.R.layout.simple_spinner_dropdown_item, arrayListcolor);
                        s_color.setPrompt("Color");
                        s_color.setAdapter(adapter);
//                        BUILDSKU();

                        //s_color.setSelection(adapter.getPosition(myItem));//Optional to set the selected item.


                    } else if (atr_id.equals("134")) {
                        JSONArray value = attribute1.getJSONArray("values");
                        for (int j = 0; j < value.length(); j++) {
                            JSONObject data = value.getJSONObject(j);
                            String value_index = data.getString("value_index");
                            String label_index = data.getString("label");
                            arrayListsize.add(new Spinner_attribute_Pojo(label_index, value_index));


                        }

                        ArrayAdapter<Spinner_attribute_Pojo> adapter = new
                                ArrayAdapter<Spinner_attribute_Pojo>(ProductDetails.this,
                                android.R.layout.simple_spinner_dropdown_item, arrayListsize);
                        s_size.setPrompt("Size");
                        s_size.setAdapter(adapter);
//                        BUILDSKU();

                    }

                    int k = 1;
                    JSONObject attribute2 = config.getJSONObject(k);
                    String label2 = attribute2.getString("label");
                    atr_id2 = attribute2.getString("attribute_id");
                    if (atr_id2.equals("92")) {
                        JSONArray value = attribute2.getJSONArray("values");

                        for (int j = 0; j < value.length(); j++) {
                            JSONObject data = value.getJSONObject(j);
                            String value_index = data.getString("value_index");
                            String label_index = data.getString("label");
                            arrayListcolor.add(new Spinner_attribute_Pojo(label_index, value_index));

                        }
                        ArrayAdapter<Spinner_attribute_Pojo> adapter = new
                                ArrayAdapter<Spinner_attribute_Pojo>(ProductDetails.this,
                                android.R.layout.simple_spinner_dropdown_item, arrayListcolor);
                        s_color.setPrompt("Color");
                        s_color.setAdapter(adapter);
//                        BUILDSKU();


                    } else if (atr_id2.equals("134")) {
                        JSONArray value = attribute2.getJSONArray("values");
                        for (int j = 0; j < value.length(); j++) {
                            JSONObject data = value.getJSONObject(j);
                            String value_index = data.getString("value_index");
                            String label_index = data.getString("label");
                            arrayListsize.add(new Spinner_attribute_Pojo(label_index, value_index));
                        }
                        ArrayAdapter<Spinner_attribute_Pojo> adapter = new
                                ArrayAdapter<Spinner_attribute_Pojo>(ProductDetails.this,
                                android.R.layout.simple_spinner_dropdown_item, arrayListsize);
                        s_size.setPrompt("Size");
                        s_size.setAdapter(adapter);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Error",error.printStackTrace());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Network Connection Error", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("product_id", P_id);
//
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }// check the inventory

    private void ChekgivenQuantity_2() {
        loading = ProgressDialog.show(ProductDetails.this, "Checking Availability...", "Please Wait...", false, false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_INVENTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                try {
                    JSONObject main = new JSONObject(response);
                    JSONArray quantyCOnfig = main.getJSONArray("Qunatity");
                    JSONObject data = quantyCOnfig.getJSONObject(0);
                    quantity1 = data.getString("qty");
                    given = Float.valueOf(quantity1);
                    enter = Float.valueOf(ed_qty.getText().toString());
//                    if (given >= enter) {
//
//                    } else

                   // {
                    txt.setText(quantity1.replace(".0000"," Available"));
                    p_size=size_name;
                    p_color=color_name;
                    firsttime=true;
                    if (!checked)
                    {
                        checked=true;
                    }

                        // }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "This Product is Out of stock", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Error",error.printStackTrace());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "This Product is Out of stock", Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sku", Build_Sku);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }


    //////////////////////////Chose_Options/////////////////////////////////////

    private void ChoseOption() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetails.this);
        LayoutInflater inflater = ProductDetails.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.chose_review_checkout, null);
        builder.setView(dialogView);
        Button shop, chek;
        shop = (Button) dialogView.findViewById(R.id.shop);
        chek = (Button) dialogView.findViewById(R.id.chek);
        builder.setTitle("Product Added Chose next");
        shop.setOnClickListener(new View.OnClickListener() {

//                        chek.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent =new Intent(ProductDetails.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });

        chek.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ProductDetails.this, My_Cart.class);
                startActivity(intent);
            }
        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        builder.show();
    }

    //////////////////Configureable Quantity////////////////////
    private void ChekgivenQuantity() {
        loading = ProgressDialog.show(ProductDetails.this, "Checking Availability...", "Please Wait...", false, false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_INVENTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                try {
                    JSONObject main = new JSONObject(response);
                    JSONArray quantyCOnfig = main.getJSONArray("Qunatity");
                    JSONObject data = quantyCOnfig.getJSONObject(0);
                    quantity1 = data.getString("qty");
                    given = Float.valueOf(quantity1);
                    enter = Float.valueOf(ed_qty.getText().toString());
//                    if (given >= enter) {
//
//                    } else
//
//                    {


                    txt.setText(quantity1.replace(".0000"," Available"));
                    p_size=size_name;
                    p_color=color_name;

                    firsttime=true;
                    if (!checked)
                    {
                        checked=true;
                    }

                    // }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Error",error.printStackTrace());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "This Product is Out of stock", Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sku", Build_Sku);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
//////////////////////////////////////////////////////

    ///////////////////////Add to Cart For New Order/////////////////////////////////////////////////////////////////////
    private void ADDTOCART() {
        loading = ProgressDialog.show(ProductDetails.this, "Adding...", "Please Wait...", false, false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_ADD_TO_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                try {
                    JSONObject data = new JSONObject(response);
                    cart_no = data.getString("cart_id");
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = ProductDetails.this.getApplicationContext().
                            getSharedPreferences(Config.SHARED_PREF_CART, Context.MODE_PRIVATE);
                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Config.SHARED_PREF_CART_NO, cart_no);
                    Toast.makeText(getApplicationContext(), "Product Added", Toast.LENGTH_SHORT).show();
                    //Adding values to editor

                    //Saving values to editor
                    editor.commit();
                    ChoseOption();
//
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Try Again.. Something Went Wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Error",error.printStackTrace());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "This Product Is Out of Stock", Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("color_option", value_indexs);
                params.put("size_option", value_indexc);
                params.put("quantity", ed_qty.getText().toString());
                params.put("prod_id", P_id);
//                params.put("cart_id", P_id);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


    }
}
