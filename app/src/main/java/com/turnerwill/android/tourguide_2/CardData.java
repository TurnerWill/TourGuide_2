package com.turnerwill.android.tourguide_2;

public class CardData {

    private String cardName;
    private String cardDescription;
    private String latitude;
    private String longitude;
    private String phoneNumber;
    private int cardImage;
    private String videoPath; //represents URL path of video


    // constructor for cards that will use DetailActivity.class,
    // a null value as phoneNumber will hide fab,
    // a fab click triggers implicit Intent.ACTION_DIAL
    public CardData(String cardName, String cardDescription, String phoneNumber, int cardImage) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.phoneNumber = phoneNumber;
        this.cardImage = cardImage;
    }

    //constructor for cards holding a mapview, used within the  DetailActivityMap.class
    public CardData(String cardName, String cardDescription, String phoneNumber,String latitude,
                    String longitude, int cardImage) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cardImage = cardImage;
    }

    // constructor for cards holding video, used within the DetailActivityVideo.class
    public CardData(String cardName, int cardImage, String videoPath) {
        //String cardVideo is URL path of video
        this.cardName = cardName;
        this.cardImage = cardImage;
        this.videoPath = videoPath;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public int getCardImage() {
        return cardImage;
    }

    public String getLatitude() { return latitude; }

    public String getLongitude() { return longitude; }

    public String getPhoneNumber() { return phoneNumber; }

    public String getVideoPath() { return videoPath; }
}
