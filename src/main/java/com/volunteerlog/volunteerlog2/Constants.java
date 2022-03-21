package com.volunteerlog.volunteerlog2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Constants {
    public static ImageView logImgView = createImage("addlist.png");
    public static ImageView milestonesImgView = createImage("spreadsheet.png");
    public static ImageView calendarImgView = createImage("calendar.png");
    public static ImageView addNewImgView = createImage("add-new.png");
    public static ImageView saveImgView = createImage("save.png");
    public static ImageView searchImgView1 = createImage("search.png");
    public static ImageView searchImgView2 = createImage("search.png");


    //Image Helper
    private static ImageView createImage(String path){
        String dir = System.getProperty("user.dir");
        String absolutePath = dir + "\\res\\images\\" + path;
        Image img = new Image(absolutePath);
        ImageView imgview = new ImageView();
        imgview.setFitHeight(24);
        imgview.setFitWidth(24);
        imgview.setImage(img);

        return imgview;
    }
}
