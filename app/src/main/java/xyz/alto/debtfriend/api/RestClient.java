package xyz.alto.debtfriend.api;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DateFormat;

import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import xyz.alto.debtfriend.api.service.AltoService;

<<<<<<< HEAD
import static xyz.alto.debtfriend.utils.Static.ANTONS_TELEFON;
=======
import static xyz.alto.debtfriend.utils.Static.ISAKS_TELEFON;
>>>>>>> 0abb4d9e303df79da69daa2a09f48737021110bb
import static xyz.alto.debtfriend.utils.Static.LOCALHOST;

/**
 * Created by Anton on 2015-10-10.
 */
public class RestClient {

    private AltoService altoService;

    public RestClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(new LoggingInterceptor());
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

                //Buffer buffer = new Buffer();
                //request.body().writeTo(buffer);
                //String body = buffer.readUtf8();
                //Log.d("Send this", body);

                // Customize or return the response
                return response;
            }
        });

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LOCALHOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        altoService = retrofit.create(AltoService.class);
    }

    public AltoService getAltoService() {
        return altoService;
    }


    class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.i("Interceptor", String.format("Sending request %s on %s%n%s", "URL" + request.url(), " Chain connection " + chain.connection(), " Request headers " + request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.i("Interceptor", String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
}
