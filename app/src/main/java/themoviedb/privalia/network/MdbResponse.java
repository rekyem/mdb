package themoviedb.privalia.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arturo on 08/04/2018.
 */

public class MdbResponse {
    @SerializedName("status")
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public MdbResponse() {
    }

    public MdbResponse(String status) {
        this.status = status;
    }
}
