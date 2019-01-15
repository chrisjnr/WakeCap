package android.wakecap.co.wakecap.Retrofit;

import android.wakecap.co.wakecap.Models.WorkersListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiInterface {

    @Headers({"X-Requested-With:XMLHttpRequest", "Content-Type:application/json"})
    @GET("api/sites/{site_id}/workers")
    Call<WorkersListResponse> getWorkers(@Path("site_id") String site_id);
}
