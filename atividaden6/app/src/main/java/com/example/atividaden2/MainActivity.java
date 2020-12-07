package com.example.atividaden2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.meuBotao);

        ListView listView = findViewById(R.id.mylistview);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://restcountries.eu/rest/v2/lang/pt";
                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                List<String> names = new ArrayList<String>();
                                //System.out.println(response);
                                try {
                                    JSONArray jsonObject = new JSONArray(response);
                                    for (int i = 0; i < jsonObject.length() ; i++) {
                                        names.add(jsonObject.getJSONObject(i).get("name").toString());

                                    }

                                    listView.setAdapter(new ArrayAdapter<String>(
                                            getApplicationContext(),
                                            android.R.layout.simple_list_item_1,
                                            android.R.id.text1,
                                            names
                                    ));
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String item = (String) listView.getItemAtPosition(position);
                                            String latlng ="";
                                            Toast.makeText(MainActivity.this,"Voce escolheu : " + item,Toast.LENGTH_SHORT).show();
                                          try {
                                              JSONArray jsonObject = new JSONArray(response);

                                              for (int i = 0; i < jsonObject.length() ; i++) {
                                                 String itemcompar=jsonObject.getJSONObject(i).get("name").toString();

                                                  if(item.contains(itemcompar)){
                                                      latlng =jsonObject.getJSONObject(i).get("latlng").toString();
                                                  }

                                              }
                                              Intent pais =new Intent(MainActivity.this, MapsActivity.class);

                                              pais.putExtra("pais",item);
                                              pais.putExtra("latlng",latlng);
                                              startActivity(pais);
                                          }catch (JSONException e){  e.printStackTrace();}

                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                System.err.println(error.toString());
                            }
                        }
                );

                MySingleton.getInstance(MainActivity.this).addToRequestQueue(request);
            }
        });
    }
}