package br.com.drfacil.android.fragments.search;

import android.content.Context;
import android.content.res.Resources;
import br.com.drfacil.android.R;
import br.com.drfacil.android.model.Professional;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.net.ConnectException;
import java.util.List;

public class SearchResultsResponse {

    private int mStatus;
    private List<Professional> mProfessionals;
    private String mMessage;

    public SearchResultsResponse(List<Professional> professionals) {
        mStatus = 200;
        mProfessionals = professionals;
    }

    public SearchResultsResponse(Context context, RetrofitError error) {
        mStatus = -1;
        Resources resources = context.getResources();
        Response response = error.getResponse();
        if (error.getCause() instanceof ConnectException) {
            mMessage = resources.getString(R.string.error_no_connectivity);
        } else if (response == null) {
            mMessage = resources.getString(R.string.error_unknown_network_error);
        } else {
            mStatus = response.getStatus();
            if (400 <= mStatus && mStatus < 500) mMessage = resources.getString(R.string.error_client_error);
            else if (500 <= mStatus && mStatus < 600) mMessage = resources.getString(R.string.error_server_error);
        }
    }

    public boolean isSuccess() {
        return 200 <= mStatus && mStatus < 300 && mProfessionals != null;
    }

    public int getStatus() {
        return mStatus;
    }

    public List<Professional> getProfessionals() {
        return mProfessionals;
    }

    public String getMessage() {
        return mMessage;
    }
}
