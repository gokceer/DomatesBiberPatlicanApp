package com.example.user.recipes;

public class Malzeme {
    int id;
    String malzeme_adi;

    // constructors
    public Malzeme() {
    }

    public Malzeme(String malzeme_adi) {
        this.malzeme_adi = malzeme_adi;
    }

    public Malzeme(int id, String malzeme_adi) {
        this.id = id;
        this.malzeme_adi = malzeme_adi;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMalzemeAdi(String malzeme_adi) {
        this.malzeme_adi = malzeme_adi;
    }

    public int getId() {
        return id;
    }

    public String getMalzemeAdi() {
        return malzeme_adi;
    }

}
