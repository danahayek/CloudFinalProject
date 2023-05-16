package com.example.cloudfinalproject.module;

public class Topics {
    String topic_name;

    public Topics(String topic_name, String topic_content, String topic_image, String topic_video) {
        this.topic_name = topic_name;
        this.topic_content = topic_content;
        this.topic_image = topic_image;
        this.topic_video = topic_video;
    }

    String topic_content;

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getTopic_content() {
        return topic_content;
    }

    public void setTopic_content(String topic_content) {
        this.topic_content = topic_content;
    }

    public String getTopic_image() {
        return topic_image;
    }

    public void setTopic_image(String topic_image) {
        this.topic_image = topic_image;
    }

    public String getTopic_video() {
        return topic_video;
    }

    public void setTopic_video(String topic_video) {
        this.topic_video = topic_video;
    }

    String topic_image;
    String topic_video;

}