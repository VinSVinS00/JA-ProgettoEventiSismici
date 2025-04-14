/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package progettoeventisismici;

import java.time.LocalDateTime;
//#EventID|Time|Latitude|Longitude|Depth/Km|Author|Catalog|Contributor|ContributorID|MagType|Magnitude|MagAuthor|EventLocationName


public class Evento {
    private String eventID;
    private LocalDateTime time;
    private double latitude;
    private double longitude;
    private double depthKm;
    private String author;
    private String catalog;
    private String contributor;
    private String contributorID;
    private String magType;
    private double magnitude;
    private String magAuthor;
    private String EventLocationName;
    private String EventType;

    public Evento(String eventID, LocalDateTime time, double latitude, double longitude, double depthKm, String author, String catalog, String contributor, String contributorID, String magType, double magnitude, String magAuthor, String EventLocationName, String EventType) {
        this.eventID = eventID;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.depthKm = depthKm;
        this.author = author;
        this.catalog = catalog;
        this.contributor = contributor;
        this.contributorID = contributorID;
        this.magType = magType;
        this.magnitude = magnitude;
        this.magAuthor = magAuthor;
        this.EventLocationName = EventLocationName;
        this.EventType = EventType;
    }
    
    


    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDepthKm() {
        return depthKm;
    }

    public void setDepthKm(double depthKm) {
        this.depthKm = depthKm;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getMagType() {
        return magType;
    }

    public void setMagType(String magType) {
        this.magType = magType;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getMagAuthor() {
        return magAuthor;
    }

    public void setMagAuthor(String magAuthor) {
        this.magAuthor = magAuthor;
    }

    public String getEventLocationName() {
        return EventLocationName;
    }

    public void setEventLocationName(String EventLocationName) {
        this.EventLocationName = EventLocationName;
    }

    public String getContributorID() {
        return contributorID;
    }

    public void setContributorID(String contributorID) {
        this.contributorID = contributorID;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String EventType) {
        this.EventType = EventType;
    }
    
    
    
@Override
    public String toString() {
        return "EQEvent{" + "eventID=" + eventID + ", \ntime=" + time + ", \nlatitude=" + latitude + ", \nlongitude=" + longitude + ", \ndepthkm=" + depthKm + ", \nauthor=" + author + ", \ncontributor=" + contributor + ", \ncontributorID=" + contributorID + ", \nmagType=" + magType + ", \nmagnitude=" + magnitude + ", \nmagAuthor=" + magAuthor + ", \neventLocationName=" + EventLocationName + "}\n";
    }
}