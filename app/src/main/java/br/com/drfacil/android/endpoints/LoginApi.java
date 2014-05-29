package br.com.drfacil.android.endpoints;

import br.com.drfacil.android.model.Patient;
import retrofit.http.GET;
import retrofit.http.Query;

public interface LoginApi {

    @GET("/patients")
    public Patient search(
            @Query("username") String username,
            @Query("email") String email,
            @Query("first_name") String firstName,
            @Query("last_name") String lastName,
            @Query("birthday") String birthday,
            @Query("gender") String gender);
}
