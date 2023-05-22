package com.example.cloudfinalproject.module;

public class PatientTopicModule {
    String Taddress;
    String Tdetails;
    String Timage;
    String Tvideo;

    public PatientTopicModule(String taddress, String tdetails, String timage, String tvideo) {
        Taddress = taddress;
        Tdetails = tdetails;
        Timage = timage;
        Tvideo = tvideo;
    }

    public PatientTopicModule() {

    }

    public PatientTopicModule(String taddress) {
        Taddress = taddress;
    }

    public String getTaddress() {
        return Taddress;
    }

    public void setTaddress(String taddress) {
        Taddress = taddress;
    }

    public String getTdetails() {
        return Tdetails;
    }

    public void setTdetails(String tdetails) {
        Tdetails = tdetails;
    }

    public String getTimage() {
        return Timage;
    }

    public void setTimage(String timage) {
        Timage = timage;
    }

    public String getTvideo() {
        return Tvideo;
    }

    public void setTvideo(String tvideo) {
        Tvideo = tvideo;
    }
}
