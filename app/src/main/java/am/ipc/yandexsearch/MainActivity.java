package am.ipc.yandexsearch;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import am.ipc.yandexsearch.api.FeatureMember;
import am.ipc.yandexsearch.api.YandexResult;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView address;
    AddressAdapterListener adapterListener;
    ArrayAdapter<String> adapter;
    List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        address = findViewById(R.id.address);
        adapterListener = new AddressAdapterListener();
        address.setOnItemClickListener(adapterListener);
        address.addTextChangedListener(adapterListener);
        data = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        address.setThreshold(4);
        address.setAdapter(adapter);
    }

    class AddressAdapterListener implements AdapterView.OnItemClickListener, TextWatcher {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String r = charSequence.toString();
            if (r.length() > 3) {
                geo(charSequence.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    public void geo(String key) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://geocode-maps.yandex.ru/1.x/?format=json&geocode=Armenia+" + key + "&lang=am_AM";
        Log.i("__yand", url);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    YandexResult result = new ObjectMapper().readValue(response, YandexResult.class);
                    data.clear();
                    for (FeatureMember x : result.getResponse().getGeoObjectCollection().getFeatureMember()) {
                        if (x.getGeoObject().getDescription() != null) {
                            Log.i("__yand", x.getGeoObject().getDescription());
                            Log.i("__yand", x.getGeoObject().getName());
                            Log.i("__yand", x.getGeoObject().getPoint().getPos());
                            data.add(x.getGeoObject().getName() + "::" + x.getGeoObject().getDescription() + "");
                        }

                    }
                    adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);
                    address.setAdapter(adapter);
                    adapter.getFilter().filter("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
}
