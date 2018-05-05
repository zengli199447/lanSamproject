package com.example.administrator.landapplication.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.global.DataClass;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.ui.activity.tools.CameraActivity;
import com.example.administrator.landapplication.widget.AlbumCacheClass;
import com.example.administrator.landapplication.widget.DockingSymbol;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.durban.Controller;
import com.yanzhenjie.durban.Durban;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/3/22.
 */

public class AlbumUtils {

    private Activity activity;
    private AlbumCacheClass albumCacheClass;
    private String TAG = "AlbumUtils";
    public static boolean AlbumModel = false; //TODO 启动模式
    private final ArrayList<String> files = new ArrayList<>();

    private static AlbumUtils instance = new AlbumUtils();

    private AlbumUtils() {

    }

    public AlbumUtils(Activity activity, AlbumCacheClass albumCacheClass) {
        this.activity = activity;
        this.albumCacheClass = albumCacheClass;
    }

    public static AlbumUtils newInstance() {
        return instance;
    }

    public void TakePhoto(Activity activity) {
        activity.startActivityForResult(new Intent(activity, CameraActivity.class), DockingSymbol.TAKEPHOTO);
    }

    public void PictureSelector(Activity activity, AlbumCacheClass albumCacheClass) {
        final ArrayList<AlbumFile> albumFileselectList = albumCacheClass.getAlbumFileselectList();
        final ArrayList<String> pictureFiles = albumCacheClass.getPictureFiles();
        Album.image(activity) // 选择图片。
                .multipleChoice()
                .requestCode(DockingSymbol.ALBUM_CODE_SELECT)
                .camera(true)
                .columnCount(3)
                .selectCount(12)
                .checkedList(albumFileselectList)
                .widget(initWidget(activity))
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        pictureFiles.clear();
                        albumFileselectList.clear();
                        albumFileselectList.addAll(result);
                        for (int i = 0; i < result.size(); i++) {
                            String path = result.get(i).getPath();
                            pictureFiles.add(path);
                            LogUtil.e(TAG, "绝对路径 path : " + "file://" + path);
                        }
                        RxBus.getDefault().post(new CommonEvent(EventCode.REFRESF_PICTURE));
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                    }
                })
                .start();

    }

    //相册单选裁剪
    public void singlePictureSelect(final Activity activity, final boolean status) {
        Album.image(activity)
                .singleChoice()
                .camera(false)
                .requestCode(DockingSymbol.ALBUM_CODE_SEINGLE)
                .columnCount(3)
                .widget(initWidget(activity))
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        files.clear();
                        for (int i = 0; i < result.size(); i++) {
                            String path = result.get(i).getPath();
                            Log.e(TAG, "绝对路径 path : " + "file://" + path);
                            files.add(path);
                        }
                        if (status) {
                            tailoring(files, activity);
                        } else {
                            RxBus.getDefault().post(new CommonEvent(EventCode.REFRESF_PICTURE, files.get(0)));
                        }

                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                    }
                })
                .start();
    }

    //批量裁剪
    public void tailoring(ArrayList<String> files, Activity activity) {
        Durban.with(activity)
                .title("裁剪")// 裁剪界面的标题。
                .statusBarColor(ContextCompat.getColor(activity, R.color.white))
                .toolBarColor(ContextCompat.getColor(activity, R.color.white))
                .navigationBarColor(ContextCompat.getColor(activity, R.color.white))
                .inputImagePaths(files)
                .outputDirectory(DataClass.CacheFile)
                .maxWidthHeight(360, 360)
                .aspectRatio(1, 1)
                .compressFormat(Durban.COMPRESS_JPEG)
                // 图片压缩质量，请参考：Bitmap#compress(Bitmap.CompressFormat, int, OutputStream)
                .compressQuality(90)
                // 裁剪时的手势支持：ROTATE, SCALE, ALL, NONE.
                .gesture(Durban.GESTURE_ALL)
                .controller(Controller.newBuilder()
                        .enable(false) // 是否开启控制面板。
                        .rotation(true) // 是否有旋转按钮。
                        .rotationTitle(true) // 旋转控制按钮上面的标题。
                        .scale(true) // 是否有缩放按钮。
                        .scaleTitle(true) // 缩放控制按钮上面的标题。
                        .build()) // 创建控制面板配置。
                .requestCode(DockingSymbol.ALBUM_CODE_TAILORING)
                .start();
    }

    //画廊
    public void startOrNetGallery(Activity activity, final ArrayList<String> list, final boolean check, int positon, final AlbumCacheClass albumCacheClass) {
        Album.gallery(activity)
                .requestCode(DockingSymbol.ALBUM_CODE_GALLERY) // 请求码，会在listener中返回。
                .checkedList(list) // 要浏览的图片列表：ArrayList<String>。
                .checkable(check) // 是否有浏览时的选择功能。
                .currentPosition(positon)
                .widget(initWidget(activity))
                .onResult(new Action<ArrayList<String>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<String> result) {
                        ArrayList<AlbumFile> albumFileselectList = albumCacheClass.getAlbumFileselectList();
                        ArrayList<String> pictureFiles = albumCacheClass.getPictureFiles();
                        if (check) {
                            pictureFiles.clear();
                            albumFileselectList.clear();
                            for (int i = 0; i < result.size(); i++) {
                                pictureFiles.add(result.get(i));
                                AlbumFile albumFile = new AlbumFile();
                                albumFile.setPath(result.get(i));
                                albumFileselectList.add(albumFile);
                            }
                            RxBus.getDefault().post(new CommonEvent(EventCode.REFRESF_PICTURE));
                        }
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                    }
                })
                .start();
    }

    //样式
    private Widget initWidget(Context context) {
        Widget widget = Widget.newDarkBuilder(context)
                .title("媒体浏览")
                .statusBarColor(Color.WHITE)
                .toolBarColor(Color.WHITE)
                .navigationBarColor(Color.WHITE)
                .mediaItemCheckSelector(Color.TRANSPARENT, Color.TRANSPARENT)
                .bucketItemCheckSelector(Color.TRANSPARENT, Color.TRANSPARENT)
                .buttonStyle(Widget.ButtonStyle.newDarkBuilder(context).setButtonSelector(Color.WHITE, Color.WHITE).build()).build();

        return widget;
    }


}
