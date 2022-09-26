package com.cielicki.dominik.allergyapp.data;

/**
 * Enum zawierający adresy dostępnych endpointów.
 */
public enum Endpoints {
    ALLERGEN("allergen"),
    CHAT("chat"),
    MEDICINE_COMMENT("medicineComment"),
    MEDICINE("medicine"),
    MESSAGES("messages"),
    QUESTION("question"),
    SETTING("setting"),
    SETTINGS_CATEGORY("settingsCategory"),
    USER("user"),
    USER_SETTINGS("userSettings"),
    VOIVODESHIP("voivodeship"),
    VOIVODESHIP_ALLERGEN("voivodeshipAllergen");


    private String url = "http://3.73.73.228:8080/allergy-app/";
    private String endpoint;

    private Endpoints(String endpoint) {
        this.endpoint = url + endpoint;
        // http://192.168.0.16:8080/user
    }

    public String getEndpointUrl() {
        return endpoint;
    }
}
