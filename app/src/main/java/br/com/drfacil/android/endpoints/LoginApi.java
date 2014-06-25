package br.com.drfacil.android.endpoints;

import br.com.drfacil.android.model.Patient;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Query;

public interface LoginApi {

    @POST("/patients")
    public Patient create(@Body Patient patient);
}
