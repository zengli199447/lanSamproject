package com.example.administrator.landapplication.widget;

import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/22.
 */

public class AlbumCacheClass {

    public ArrayList<AlbumFile> AlbumFileselectList = new ArrayList<>(); //相册选择图集

    public ArrayList<String> pictureFiles = new ArrayList<>();//拍照图集

    public ArrayList<AlbumFile> getAlbumFileselectList() {
        return AlbumFileselectList;
    }

    public void setAlbumFileselectList(ArrayList<AlbumFile> albumFileselectList) {
        AlbumFileselectList = albumFileselectList;
    }

    public ArrayList<String> getPictureFiles() {
        return pictureFiles;
    }

    public void setPictureFiles(ArrayList<String> pictureFiles) {
        this.pictureFiles = pictureFiles;
    }
}
