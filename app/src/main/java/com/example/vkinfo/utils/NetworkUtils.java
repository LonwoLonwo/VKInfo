package com.example.vkinfo.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String VK_API_BASE_URL = "https://api.vk.com";
    private static final String VK_USERS_GET = "/method/users.get";
    private static final String PARAM_USER_ID = "user_ids";
    private static final String PARAM_VERSION = "v";
    private static final String ACCESS_TOKEN = "access_token";

    public static URL generateURL(String userId){
        Uri builtUri = Uri.parse(VK_API_BASE_URL + VK_USERS_GET)
                .buildUpon()
                .appendQueryParameter(PARAM_USER_ID, userId)
                .appendQueryParameter(PARAM_VERSION, "5.8")
                .appendQueryParameter(ACCESS_TOKEN, "21ae02d521ae02d521ae02d56821c1bd3c221ae21ae02d57fe7912388079d93b8d86b60")
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);//Можно БуффередРид ещё аналогично как-то использовать
            scanner.useDelimiter("\\A");//Хитрый паттерн-разделитель. Это сочетание выведет целиком строку, а по умолчанию у Сканнера в разделителе - пробел.
            //а этот паттерн позволяет получить данные, при этом не разделяя их на подстроки

            boolean hasInput = scanner.hasNext();//проверить, что входные данные вообще есть

            if(hasInput){
                return scanner.next();
            }
            else{//если от сервера вообще не придут данные
                return null;
            }
        } finally {
            urlConnection.disconnect();//делать дисконнект надо в самом конце, но после return выдаёт уже ошибку в методе. поэтому обернули в finally
        }
    }
}
