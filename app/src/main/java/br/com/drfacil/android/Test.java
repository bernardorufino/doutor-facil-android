package br.com.drfacil.android;

import br.com.drfacil.android.model.Insurance;
import br.com.drfacil.android.model.Professional;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import retrofit.http.GET;
import retrofit.http.Query;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class Test {

    private static final String ENDPOINT = "http://localhost:5000";

    public static void main(String[] args) {
        String a = (String) null;
        System.out.println(a == null);
//        try {
//            Gson gson = new GsonBuilder()
//                     .setFieldNamingStrategy(CustomFieldNamingStrategy.INSTANCE)
//                     .registerTypeAdapter(Date.class, new DateTypeAdapter())
//                     .create();
//            RestAdapter restAdapter = new RestAdapter.Builder()
//                     .setEndpoint(ENDPOINT)
//                     .setConverter(new GsonConverter(gson))
//                     .build();
//             Api api = restAdapter.create(Api.class);
//             List<Professional> ps = api.search("1,2,3", "na", new Date(), new Date(), "1,2,3");
//             Professional p = ps.get(0);
//             System.out.println(p);
//        } catch (RetrofitError e) {
//            System.out.println(e.getResponse().getStatus());
//        }
    }


    public static interface Api {

        @GET("/professionals/search")
        public List<Professional> search(
                @Query("specialty_ids") String specialtyIds,
                @Query("location") String location,
                @Query("start_date") Date startDate,
                @Query("end_date") Date endDate,
                @Query("insurance_ids") String insuranceIds);
    }


    public static class InsuranceAdapter extends TypeAdapter<Insurance> {

        @Override
        public void write(JsonWriter out, Insurance value) throws IOException {

        }

        @Override
        public Insurance read(JsonReader in) throws IOException {
            return null;
        }
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
