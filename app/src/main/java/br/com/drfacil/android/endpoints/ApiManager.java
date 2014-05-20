package br.com.drfacil.android.endpoints;

import android.content.Context;
import br.com.drfacil.android.helpers.CacheHelper;
import br.com.drfacil.android.helpers.CustomHelper;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.okhttp.HttpResponseCache;
import com.squareup.okhttp.OkHttpClient;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

public class ApiManager {

    private static final boolean LOCAL_DEBUG = true; // For Genymotion
    private static final String ENDPOINT = (LOCAL_DEBUG) ? "http://10.0.3.2:5000" : "http://drfacil.herokuapp.com";
    private static final long CACHE_SIZE = 64 * 1024; // In bytes
    private static ApiManager sInstance;

    public synchronized static ApiManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ApiManager(context.getApplicationContext());
        }
        return sInstance;
    }


    private final RestAdapter mRestAdapter;
    private final ClassToInstanceMap<Object> mInstanceMap;

    // Prevents outside instantiation
    private ApiManager(Context context) {
        mInstanceMap = MutableClassToInstanceMap.create();

        // Make sure server allow caches
        OkHttpClient client = new OkHttpClient();
        File cacheDir = CacheHelper.createCacheDir(context, ApiManager.class.toString());
        HttpResponseCache cache = null;
        try {
            cache = new HttpResponseCache(cacheDir, CACHE_SIZE);
        } catch (IOException e) {
            CustomHelper.logException(e);
        }
        if (cache != null) {
            client.setResponseCache(cache);
        }

        Gson gson = new GsonBuilder()
                .setFieldNamingStrategy(CustomFieldNamingStrategy.INSTANCE)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setClient(new OkClient(client))
                .setConverter(new GsonConverter(gson))
                .build();
    }

    public <T> T getApi(Class<T> klass) {
        T api = mInstanceMap.getInstance(klass);
        if (api == null) {
            api = mRestAdapter.create(klass);
            mInstanceMap.put(klass, api);
        }
        return api;
    }

    private static enum CustomFieldNamingStrategy implements FieldNamingStrategy {
        INSTANCE;

        private FieldNamingStrategy mTranslator = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

        @Override
        public String translateName(Field f) {
            return mTranslator.translateName(f).replace("m_", "");
        }
    }
}
