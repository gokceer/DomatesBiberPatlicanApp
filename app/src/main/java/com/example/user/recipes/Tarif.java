package com.example.user.recipes;

public class Tarif {
    int id;
    String tarif_adi;
    String malzeme_listesi;
    String yapilisi;
    String resim;

    // constructors
    public Tarif() {
    }

    public Tarif(String tarif_adi, String malzeme_listesi, String yapilisi, String resim) {
        this.tarif_adi = tarif_adi;
        this.malzeme_listesi = malzeme_listesi;
        this.yapilisi = yapilisi;
        this.resim = resim;
    }

    public Tarif(int id, String tarif_adi, String malzeme_listesi, String yapilisi, String resim) {
        this.id = id;
        this.tarif_adi = tarif_adi;
        this.malzeme_listesi = malzeme_listesi;
        this.yapilisi = yapilisi;
        this.resim = resim;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }

    public void setTarifAdi(String tarif_adi) {
        this.tarif_adi = tarif_adi;
    }

    public void setMalzemeListesi(String malzeme_listesi) {
        this.malzeme_listesi = malzeme_listesi;
    }

    public void setYapilisi(String yapilisi){
        this.yapilisi = yapilisi;
    }

    public int getId() {
        return this.id;
    }

    public String getTarifAdi() {
        return this.tarif_adi;
    }

    public String getMalzemeListesi() {
        return this.malzeme_listesi;
    }

    public String getYapilisi() {
        return this.yapilisi;
    }

    public String getResim() {
        return this.resim;
    }

}
