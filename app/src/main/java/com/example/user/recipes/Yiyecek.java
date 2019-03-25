package com.example.user.recipes;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.content.Context;
import android.view.View;
//import android.widget.Button;

//Kodlamayla bir suru button olusturmak
//icin bu class ı olusturduk
public class Yiyecek extends AppCompatButton {

    String item_name = "New button";
    int item_indexID;
    int width;
    int height;
    int padding;

    /*Bu Yiyecek class'ından üretilen objelerin buton özelligi tasımasını istiyoruz.
      Buton kullandigimiz her yerde bu yiyecek objlerini kullanacagiz.*/

    public Yiyecek(Context context, String yiyecek_name, int yiyecek_indexID) {
        super(context);
        setId(yiyecek_indexID);
        item_name = yiyecek_name;
        item_indexID=yiyecek_indexID;
        setText(item_name);
    }

    public Yiyecek(Context context, String yiyecek_name) {
        super(context);
        item_name = yiyecek_name;
        setText(item_name);
    }

    public Yiyecek(Context context, String yiyecek_name, int yiyecek_indexID, int width, int height) {
        super(context);
        setId(yiyecek_indexID);
        item_name = yiyecek_name;
        item_indexID=yiyecek_indexID;
        setText(item_name);
        this.height = height;
        this.width = width;
    }

    public String getItem_name() {
        return item_name;
    }

    public int getItem_indexID() {
        return item_indexID;
    }


    public int getWidthButton() {
        return width;
    }


    public void setWidthButton(int width) {
        this.width = width;
    }

    public int getHeightButton() {
        return height;
    }

    public void setHeightButton(int height) {
        this.height = height;
    }

    public int getPaddingButton() {
        return padding;
    }

    public void setPaddingButton(int padding) {
        this.padding = padding;
    }
}
