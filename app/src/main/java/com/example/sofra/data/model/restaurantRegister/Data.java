
package com.example.sofra.data.model.restaurantRegister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("data")
    @Expose
    private RestaurantRegisterData data;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public RestaurantRegisterData getData() {
        return data;
    }

    public void setData(RestaurantRegisterData data) {
        this.data = data;
    }

}
