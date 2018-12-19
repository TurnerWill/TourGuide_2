package com.turnerwill.android.tourguide_2;

public class CardData {

    private String cardName;
    private String cardDescription;
    private String latitude;
    private String longitude;
    private String phoneNumber;
    private int cardImage;

    //constructor for cards without location data
    public CardData(String cardName, String cardDescription, int cardImage) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.cardImage = cardImage;
    }

    // constructor for cards which will use floating action button,
    // a null value as phoneNumber will hide fab, (handled in DetailActivity.class)
    // a fab click triggers implicit Intent.ACTION_DIAL (handled in DetailActivity.class),
    public CardData(String cardName, String cardDescription, String phoneNumber, int cardImage) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.phoneNumber = phoneNumber;
        this.cardImage = cardImage;
    }

    //constructor for cards with location data(for possible map api)
    public CardData(String cardName, String cardDescription, String phoneNumber,String latitude,
                    String longitude, int cardImage) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cardImage = cardImage;
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
}
