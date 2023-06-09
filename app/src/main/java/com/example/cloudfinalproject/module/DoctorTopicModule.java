package com.example.cloudfinalproject.module;

public class DoctorTopicModule {

    public String id;
    String topic_img;
    String topic_video;
    String topic_address;
    String topic_details;

    public DoctorTopicModule( String topic_address,String id) {
        this.topic_address=topic_address;
        this.id=id;
    }

    public DoctorTopicModule(String id,String topic_img, String topic_video, String topic_address, String topic_details) {

        this.id=id;
        this.topic_img = topic_img;
        this.topic_video = topic_video;
        this.topic_address = topic_address;
        this.topic_details = topic_details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DoctorTopicModule(String topic_address) {
        this.topic_address = topic_address;
    }

    public String getTopic_img() {
        return topic_img;
    }

    public String getTopic_address() {
        return topic_address;
    }

    public String getTopic_details() {
        return topic_details;
    }

    public String getTopic_video() {
        return topic_video;
    }

    public void setTopic_img(String topic_img) {
        this.topic_img = topic_img;
    }

    public void setTopic_address(String topic_address) {
        this.topic_address = topic_address;
    }

    public void setTopic_details(String topic_details) {
        this.topic_details = topic_details;
    }

    public void setTopic_video(String topic_video) {
        this.topic_video = topic_video;
    }
}
