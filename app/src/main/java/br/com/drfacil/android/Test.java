package br.com.drfacil.android;

import br.com.drfacil.android.model.Insurance;
import br.com.drfacil.android.model.Professional;
import com.fatboyindustrial.gsonjodatime.DateTimeConverter;
import com.google.gson.*;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import retrofit.http.GET;
import retrofit.http.Query;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class Test {

    private static final String ENDPOINT = "http://localhost:5000";

    public static void main(String[] args) throws IOException {
        Gson gson = new GsonBuilder()
                .setFieldNamingStrategy(CustomFieldNamingStrategy.INSTANCE)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .registerTypeAdapter(DateTime.class, new DateTimeConverter())
                .create();

//        DateTime dateTime = gson.getAdapter(DateTime.class).fromJson("\"2014-06-24T07:00:00-03:00\"");
//        System.out.println(dateTime);
        System.out.println(ISODateTimeFormat.dateTime().print(DateTime.now()));
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
