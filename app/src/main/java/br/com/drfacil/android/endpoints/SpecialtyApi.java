package br.com.drfacil.android.endpoints;

import br.com.drfacil.android.model.Specialty;
import retrofit.http.GET;

import java.util.List;

public interface SpecialtyApi {

    /* TODO: Needs cache! Use OkHttp */
    @GET("/specialties")
    public List<Specialty> all();
}
