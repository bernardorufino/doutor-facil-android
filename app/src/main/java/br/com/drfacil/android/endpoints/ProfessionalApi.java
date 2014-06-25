package br.com.drfacil.android.endpoints;

import br.com.drfacil.android.model.Professional;
import br.com.drfacil.android.model.Slot;
import org.joda.time.DateTime;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

public interface ProfessionalApi {

    @GET("/professionals/search")
    public List<Professional> search(
            @Query("specialty_ids") String specialtyIds,
            @Query("location") String location,
            @Query("start_date") DateTime startDate,
            @Query("end_date") DateTime endDate,
            @Query("insurance_ids") String insuranceIds);

    @GET("/professionals/{id}/slots")
    public List<Slot> slots(
            @Path("id") long id,
            @Query("start_date") DateTime startDate,
            @Query("end_date") DateTime endDate);

}
