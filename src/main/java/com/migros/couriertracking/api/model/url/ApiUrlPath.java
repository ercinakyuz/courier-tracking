package com.migros.couriertracking.api.model.url;

public final class ApiUrlPath {
    private ApiUrlPath() {
    }

    public static final String COURIER_BASE_URL = "/courier";
    public static final String RELATIVE_COURIER_REGISTRATION_URL = "registration";

    public static final String COURIER_REGISTRATION_URL = String.format("%s/%s", COURIER_BASE_URL, RELATIVE_COURIER_REGISTRATION_URL);
    public static final String RELATIVE_COURIER_BY_ID_URL = "{id}";

    public static final String COURIER_GET_BY_ID_URL = String.format("%s/%s", COURIER_BASE_URL, RELATIVE_COURIER_BY_ID_URL);
    public static final String RELATIVE_COURIER_CHANGE_LOCATION_URL = RELATIVE_COURIER_BY_ID_URL + "/location";

    public static final String COURIER_CHANGE_LOCATION_URL = String.format("%s/%s", COURIER_BASE_URL, RELATIVE_COURIER_CHANGE_LOCATION_URL);


}
