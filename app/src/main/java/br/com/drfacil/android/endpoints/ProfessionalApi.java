package br.com.drfacil.android.endpoints;

import br.com.drfacil.android.model.Professional;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.Date;
import java.util.List;

public interface ProfessionalApi {

    @GET("/professionals/search")
    public List<Professional> search(
            @Query("specialty_ids") String specialtyIds,
            @Query("location") String location,
            @Query("start_date") Date startDate,
            @Query("end_date") Date endDate,
            @Query("insurance_ids") String insuranceIds);
}
