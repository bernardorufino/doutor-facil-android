package br.com.drfacil.android.endpoints;

import br.com.drfacil.android.model.Appointment;
import org.joda.time.DateTime;
import retrofit.http.*;

import java.util.List;

public interface AppointmentApi {

    @GET("/patient/{id}/appointments")
    public List<Appointment> all(
            @Path("id") String patientId);

    @POST("/appointments")
    @FormUrlEncoded
    public Appointment create(
        @Field("patient_id") String patientId,
        @Field("professional_id") String professionalId,
        @Field("start_date") DateTime startDate,
        @Field("end_date") DateTime endDate);

}
