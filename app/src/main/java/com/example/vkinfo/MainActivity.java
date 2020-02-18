package com.example.vkinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import static com.example.vkinfo.utils.NetworkUtils.generateURL;
import static com.example.vkinfo.utils.NetworkUtils.getResponseFromURL;

public class MainActivity extends AppCompatActivity {
    private EditText searchField;
    private Button searchButton;
    private TextView result;

    class VKQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {//троеточие обозначает, что метод ожидает на вход массив urls в качестве параметров
            String response = null;//этот метод выполняется в отдельном потоке
            try {
                response = getResponseFromURL(urls[0]);//мы не сможем делать запрос к серверу в основном потоке(этот процесс занимает слишком много времени для стандартов основного потока), поэтому создали отдельный
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String response){//метод выполняется после того, как выполнится наш таск в бэкграунде
            result.setText(response + "TEXT");//этот результат мы вставляем в наш TextView
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchField = findViewById(R.id.et_search_field);//инициализируем это и 3 ниже
        searchButton = findViewById(R.id.b_search_VK);
        result = findViewById(R.id.tv_result);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL generatedURL = generateURL(searchField.getText().toString());//объект, по которому надо будет совершить запрос
                new VKQueryTask().execute(generatedURL);//мы этот объект передаём нашему AsyncTask, он попадает в массив в doInBackGround на 0 позицию
            }
        };

        searchButton.setOnClickListener(onClickListener);
    }
}
