package com.anshi.healthdeyi.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * Created by yulu on 2017/9/7.
 */

public class  PhotoUtils {
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getPath(Intent data,Context context){
        Uri  photoUri;
        String picPath;
        if(data == null)
        {
            Toast.makeText(context, "选择图片文件出错", Toast.LENGTH_LONG).show();
        }
        photoUri = data.getData();
        if(photoUri == null )
        {
            Toast.makeText(context, "选择图片文件出错", Toast.LENGTH_LONG).show();
        }
        picPath = PhotoUtils.getPath(context, photoUri);
        return picPath;
    }
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            return isSuccess;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String saveImageToGalleryString(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Bitmap getHttpBitmap(String url) {
        Bitmap bitmap = null;
        try
        {
            URL pictureUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) pictureUrl.openConnection();
            InputStream in = con.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static String getCameraImage(ImageView imageView, Intent data, PhotoStorageList mList, Context context){
        Uri uri = data.getData();
        String pathOne;
        InputStream in = null;
        ArrayList<StorageBean> storageData = StorageUtils.getStorageData(context);
        String[] volumePaths = mList.getVolumePaths();
        if (volumePaths.length>1&&storageData.size()>1){
            //是否已挂载
            if (storageData.get(1).getMounted().equalsIgnoreCase("mounted")){
                pathOne = volumePaths[1]+ File.separator+ "DCIM" + File.separator + "Camera" + File.separator + System.currentTimeMillis() + ".jpg";
            }else {
                pathOne = volumePaths[0]+File.separator+ "DCIM" + File.separator + "Camera" + File.separator + System.currentTimeMillis() + ".jpg";
            }

        }else {
            pathOne = volumePaths[0]+File.separator+ "DCIM" + File.separator + "Camera" + File.separator + System.currentTimeMillis() + ".jpg";
        }
        if(uri == null){
            //use bundle to get data
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                saveImage(photo, pathOne,context);
            } else {
                Toast.makeText(context, "获取失败", Toast.LENGTH_LONG).show();
            }
        }else{
            try{
                in =context.getContentResolver().openInputStream(uri);
               // Bitmap map= BitmapFactory.decodeStream(in);
                //imageView.setImageBitmap(map);
            }catch (Exception e) {
                Toast.makeText(context, "获取失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }finally {
                if (null!=in){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return pathOne;
    }
    public static void saveImage(Bitmap photo, String spath,Context context) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(spath))));
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static  String saveImageString(Bitmap bitmap,String sPath,Context context){
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(sPath, false));
            File file = new File(sPath);
            if (!file.exists()){
                file.mkdir();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            bos.flush();
            bos.close();
            return  file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getImageFromAlbum(final int code,Context context){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        try{
            ((Activity)context).startActivityForResult(intent,code);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    public static void getImageFromCamera(final int code,Context context) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            try{
                ((Activity)context).startActivityForResult(getImageByCamera,code);
            }catch (ClassCastException e){
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(context, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    public static String getCameraImage(Intent data, PhotoStorageList mList, Context context){
        String pathOne = "";
        try {
            Uri uri = data.getData();
            InputStream in = null;
            ArrayList<StorageBean> storageData = StorageUtils.getStorageData(context);
            String[] volumePaths = mList.getVolumePaths();
            if (volumePaths.length>1&&storageData.size()>1){
                //是否已挂载
                if (storageData.get(1).getMounted().equalsIgnoreCase("mounted")){
                    pathOne = volumePaths[1]+ File.separator+ "DCIM" + File.separator + "Camera" + File.separator + System.currentTimeMillis() + ".jpg";
                }else {
                    pathOne = volumePaths[0]+File.separator+ "DCIM" + File.separator + "Camera" + File.separator + System.currentTimeMillis() + ".jpg";
                }

            }else {
                pathOne = volumePaths[0]+File.separator+ "DCIM" + File.separator + "Camera" + File.separator + System.currentTimeMillis() + ".jpg";
            }
            if(uri == null){
                //use bundle to get data
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                    saveImage(photo, pathOne,context);
                } else {
                    Toast.makeText(context, "获取失败", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return pathOne;
    }
    //计算 inSampleSize 值，压缩图片
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    public static Bitmap drawBg4Bitmap(int color, Bitmap orginBitmap) {
        Paint paint = new Paint();
        paint.setColor(color);
        Bitmap bitmap = Bitmap.createBitmap(orginBitmap.getWidth(),
                orginBitmap.getHeight(), orginBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(0, 0, orginBitmap.getWidth(), orginBitmap.getHeight(), paint);
        canvas.drawBitmap(orginBitmap, 0, 0, paint);
        return bitmap;
    }

    /**
     * 灰度化 bitmap
     * @param imgTheWidth
     * @param imgTheHeight
     * @param imgThePixels
     * @return
     */
    private static Bitmap getGrayImg(int imgTheWidth, int imgTheHeight, int[] imgThePixels) {
        int alpha = 0xFF << 24;  //设置透明度
        for (int i = 0; i < imgTheHeight; i++) {
            for (int j = 0; j < imgTheWidth; j++) {
                int grey = imgThePixels[imgTheWidth * i + j];
                int red = ((grey & 0x00FF0000) >> 16);  //获取红色灰度值
                int green = ((grey & 0x0000FF00) >> 8); //获取绿色灰度值
                int blue = (grey & 0x000000FF);         //获取蓝色灰度值
                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey; //添加透明度
                imgThePixels[imgTheWidth * i + j] = grey;   //更改像素色值
            }
        }
        Bitmap result =
                Bitmap.createBitmap(imgTheWidth, imgTheHeight, Bitmap.Config.RGB_565);
        result.setPixels(imgThePixels, 0, imgTheWidth, 0, 0, imgTheWidth, imgTheHeight);
        return result;
    }

    /**
     * 去除多余白框
     * @param originBitmap
     * @return
     */
    public static Bitmap deleteNoUseWhiteSpace(Bitmap originBitmap) {
        int[] imgThePixels = new int[originBitmap.getWidth() * originBitmap.getHeight()];
        originBitmap.getPixels(
                imgThePixels,
                0,
                originBitmap.getWidth(),
                0,
                0,
                originBitmap.getWidth(),
                originBitmap.getHeight());

        // 灰度化 bitmap
        Bitmap bitmap = getGrayImg(
                originBitmap.getWidth(),
                originBitmap.getHeight(),
                imgThePixels);

        int top = 0;  // 上边框白色高度
        int left = 0; // 左边框白色高度
        int right = 0; // 右边框白色高度
        int bottom = 0; // 底边框白色高度

        for (int h = 0; h < bitmap.getHeight(); h++) {
            boolean holdBlackPix = false;
            for (int w = 0; w < bitmap.getWidth(); w++) {
                if (bitmap.getPixel(w, h) != -1) { // -1 是白色
                    holdBlackPix = true; // 如果不是-1 则是其他颜色
                    break;
                }
            }

            if (holdBlackPix) {
                break;
            }
            top++;
        }

        for (int w = 0; w < bitmap.getWidth(); w++) {
            boolean holdBlackPix = false;
            for (int h = 0; h < bitmap.getHeight(); h++) {
                if (bitmap.getPixel(w, h) != -1) {
                    holdBlackPix = true;
                    break;
                }
            }
            if (holdBlackPix) {
                break;
            }
            left++;
        }

        for (int w = bitmap.getWidth() - 1; w >= 0; w--) {
            boolean holdBlackPix = false;
            for (int h = 0; h < bitmap.getHeight(); h++) {
                if (bitmap.getPixel(w, h) != -1) {
                    holdBlackPix = true;
                    break;
                }
            }
            if (holdBlackPix) {
                break;
            }
            right++;
        }

        for (int h = bitmap.getHeight() - 1; h >= 0; h--) {
            boolean holdBlackPix = false;
            for (int w = 0; w < bitmap.getWidth(); w++) {
                if (bitmap.getPixel(w, h) != -1) {
                    holdBlackPix = true;
                    break;
                }
            }
            if (holdBlackPix) {
                break;
            }
            bottom++;
        }

        // 获取内容区域的宽高
        int cropHeight = bitmap.getHeight() - bottom - top;
        int cropWidth = bitmap.getWidth() - left - right;

        // 获取内容区域的像素点
        int[] newPix = new int[cropWidth * cropHeight];

        int i = 0;
        for (int h = top; h < top + cropHeight; h++) {
            for (int w = left; w < left + cropWidth; w++) {
                newPix[i++] = bitmap.getPixel(w, h);
            }
        }

        // 创建切割后的 bitmap， 针对彩色图，把 newPix 替换为 originBitmap 的 pixs
        return Bitmap.createBitmap(newPix, cropWidth, cropHeight, Bitmap.Config.ARGB_8888);

    }


    public static String loadImgSaveToLocal(String url,String filename){
        try {
            String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
            File appDir = new File(storePath);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            File file = new File(appDir, filename);
//得到图片地址
            byte[] data = readImage(url);
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(data);
//关闭流的这个地方需要完善一下
            outStream.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //声明称为静态变量有助于调用
    public static byte[] readImage(String path) throws Exception{
        URL url = new URL(path);
// 记住使用的是HttpURLConnection类
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
//如果运行超过5秒会自动失效 这是android规定
        conn.setConnectTimeout(5*1000);
        InputStream inStream = conn.getInputStream();
//调用readStream方法
        return readStream(inStream);
    }

    public static byte[] readStream(InputStream inStream) throws Exception{
//把数据读取存放到内存中去
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while( (len=inStream.read(buffer)) != -1){
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

    public static String compressImage(String filePath, String targetPath, int quality)  {
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = readPictureDegree(filePath);//获取相片拍摄角度
        if(degree!=0){//旋转照片角度，防止头像横着显示
            bm=rotateBitmap(bm,degree);
        }
        File outputFile=new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                //outputFile.createNewFile();
            }else{
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
        }catch (Exception e){
            e.printStackTrace();
        }
        return outputFile.getPath();
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSizes(options, 800, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     * 获取照片角度
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转照片
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap,int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }
    public static int calculateInSampleSizes(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static void getCropFromAlbum(int code,Context context){
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        ((Activity)context).startActivityForResult(intent,code);
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        try{
            ((Activity)context).startActivityForResult(intent,code);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    /**
     * 打开相册
     */
    public static void cameraPic(int code,Activity activity) {
        //创建一个file，用来存储拍照后的照片
        File outputfile = new File(activity.getExternalCacheDir(),"output.png");
        try {
            if (outputfile.exists()){
                outputfile.delete();//删除
            }
            outputfile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri imageuri ;
        if (Build.VERSION.SDK_INT >= 24){
            imageuri = FileProvider.getUriForFile(activity,
                    "com.anshi.healthdeyi.fileprovider", //可以是任意字符串
                    outputfile);
        }else{
            imageuri = Uri.fromFile(outputfile);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
        activity.startActivityForResult(intent,code);
    }

}
