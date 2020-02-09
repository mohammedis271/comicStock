package com.example.superman_application.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IssueDTO {
   private String title;

   private int seriesNumber;



   private String publisher;

   private String coverImagePath;

   private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSeriesNumber() {
        return seriesNumber;
    }

    public void setSeriesNumber(int seriesNumber) {
        this.seriesNumber = seriesNumber;
    }





    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssueDTO(String title, int seriesNumber, String publisher, String coverImagePath, String description) {
        this.title = title;
        this.seriesNumber = seriesNumber;

        this.publisher = publisher;
        this.coverImagePath = coverImagePath;
        this.description = description;
    }
}
