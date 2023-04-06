package com.android.covidqa;

import androidx.annotation.NonNull;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends BaseActivity{

    private final String TAG = "CovidQA";
    private static final String URL = "http://35.225.219.221:1145/question/";

    EditText queryInputEditText;
    Button getQueryBtn;
    RequestQueue requestQueue;

    TextView stateTv;
    TextView levelTv;
    TextView countyTv;
    TextView startDateTv;
    TextView endDataTv;
    TextView newCaseTv;
    TextView newCaseRateTv;

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        mHandler = new Handler();
        queryInputEditText = findViewById(R.id.edit_query);

        stateTv = findViewById(R.id.tv_state);
        levelTv = findViewById(R.id.tv_level);
        countyTv = findViewById(R.id.tv_county);
        startDateTv = findViewById(R.id.tv_startdate);
        endDataTv = findViewById(R.id.tv_enddate);
        newCaseTv = findViewById(R.id.tv_newcase);
        newCaseRateTv = findViewById(R.id.tv_newcase_rate);

        requestQueue = Volley.newRequestQueue(this);
        getQueryBtn = findViewById(R.id.btn_query);
        getQueryBtn.setOnClickListener(view -> {
            String queryString = queryInputEditText.getText().toString();
            stateTv.setText("");
            levelTv.setText("");
            countyTv.setText("");
            startDateTv.setText("");
            endDataTv.setText("");
            newCaseTv.setText("");
            newCaseRateTv.setText("");

            getQueryBtn.setEnabled(false);
            mHandler.postDelayed(()->{
                getQueryBtn.setEnabled(true);
            },3000);
            if(queryString.equals("")){
                Toast.makeText(MainActivity.this,"Please input!",Toast.LENGTH_SHORT).show();
            }else{

                String query_url = URL + queryString;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, query_url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG,response.toString());
                            JSONObject resultObject = response.optJSONObject("result");

                            if(resultObject != null){
                                Log.e(TAG,resultObject.toString());
                                String state = resultObject.optString("State");
                                stateTv.setText(state);
                                String level = resultObject.optString("Level");
                                levelTv.setText(level);
                                String county = resultObject.optString("County");
                                countyTv.setText(county);
                                String startDate = resultObject.optString("Start Date");
                                startDateTv.setText(startDate);
                                String endDate = resultObject.optString("End Date");
                                endDataTv.setText(endDate);
                                String newCase = resultObject.optString("New Case Past 7 days");
                                newCaseTv.setText(newCase);
                                String newCaseRate = resultObject.optString("New Case Rate Past 7 days (100K)");
                                newCaseRateTv.setText(newCaseRate);
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle errors
                            Log.e(TAG,"error: "+error.toString());
                    }});
                requestQueue.add(jsonObjectRequest);

            }
        });

    }




}