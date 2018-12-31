package com.example.dahiya.safesms;

public class Structure {
    private String mobile;
    private String n_string;
    private String e_string;

    public Structure(String mobile, String n_string, String e_string) {
        this.mobile = mobile;
        this.n_string = n_string;
        this.e_string = e_string;
    }

    public String getMobile() {

        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getN_string() {
        return n_string;
    }

    public void setN_string(String n_string) {
        this.n_string = n_string;
    }

    public String getE_string() {
        return e_string;
    }

    public void setE_string(String e_string) {
        this.e_string = e_string;
    }
}
