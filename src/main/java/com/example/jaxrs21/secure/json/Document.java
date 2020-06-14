package com.example.jaxrs21.secure.json;

import javax.annotation.security.RolesAllowed;

public class Document {

    private String publicData;
    private String secretData;

    public Document(String publicData, String secretData) {
        this.publicData = publicData;
        this.secretData = secretData;
    }

    public String getPublicData() {
        return publicData;
    }

    public void setPublicData(String publicData) {
        this.publicData = publicData;
    }

    @RolesAllowed("SecretRole")
    public String getSecretData() {
        return secretData;
    }

    public void setSecretData(String secretData) {
        this.secretData = secretData;
    }


}