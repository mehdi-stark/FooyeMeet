package com.fooyemeet2.Activities.Fragments;

import android.support.v4.app.Fragment;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by kegan on 29/04/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profil extends Fragment {

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("age")
    private String age;

    public Profil() {
        this.username = "";
        this.email = "";
        this.age = "";
    }

    public Profil(String _usernameProfil, String _emailProfil, String _ageProfil){
        this.username = _usernameProfil;
        this.email = _ageProfil;
        this.age = _ageProfil;
    }

    public  String getUsername(){
        return username;
    }

    public void  setUsername(String _usernameProfil){
        this.username = _usernameProfil;
    }

    public  String getAge(){
        return age;
    }

    public void setAge(String _ageProfil){
        this.age = _ageProfil;
    }

    public  String getEmail(){
        return email;
    }

    public void setEmail(String _emailProfil){
        this.email = _emailProfil;

    }
}