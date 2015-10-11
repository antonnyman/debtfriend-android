package xyz.alto.debtfriend.api;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Date;

import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import xyz.alto.debtfriend.api.service.AltoService;
import static xyz.alto.debtfriend.utils.Static.LOCALHOST;

/**
 * Created by Anton on 2015-10-10.
 */
public class RestClient {

    private AltoService altoService;

    public RestClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Customize the request
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Content-type", "application/json")
                        .method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(request);

                Buffer buffer = new Buffer();
                request.body().writeTo(buffer);
                String body = buffer.readUtf8();
                Log.d("Send this mathafacka", body);

                // Customize or return the response
                return response;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LOCALHOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        altoService = retrofit.create(AltoService.class);
    }

    public AltoService getAltoService() {
        return altoService;
    }
}
