package com.raccoonapps.worksimple.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class PictureSaver {

    private static final String SLASH = "/";

    private PictureSaver() {
    }

    public static String savePicture(Bitmap bitmap, String destinationFolder) {
        FileOutputStream stream;
        String fullPath = destinationFolder + SLASH + UUID.randomUUID().toString().split("-")[0] + ".jpg";
        File destFile = new File(destinationFolder);
        if (!destFile.exists())
            destFile.mkdirs();
        try {
            stream = new FileOutputStream(fullPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            stream.close();
            return fullPath;
        } catch (Exception e) {
            return null;
        }
    }

    public static String savePicture(Bitmap bitmap, String destinationFolder, Context context) {
        String photo = savePicture(bitmap, destinationFolder);
        addIntent(photo, context);
        return photo;
    }

    private static void addIntent(String file, Context context) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(new File(file));
        intent.setData(contentUri);
        context.sendBroadcast(intent);
    }

    public static boolean removeDirectory(String directory) {
        File directoryFile = new File(directory);
        return directoryFile.exists() && directoryFile.delete();
    }
}
