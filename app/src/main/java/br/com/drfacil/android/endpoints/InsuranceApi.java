package br.com.drfacil.android.endpoints;

import br.com.drfacil.android.model.Insurance;
import retrofit.http.GET;

import java.util.List;

public interface InsuranceApi {

    /* TODO: Needs cache! Use OkHttp */
    @GET("/insurances")
    public List<Insurance> all();
}
