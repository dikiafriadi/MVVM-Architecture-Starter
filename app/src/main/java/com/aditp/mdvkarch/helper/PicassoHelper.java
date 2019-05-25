package com.aditp.mdvkarch.helper;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class PicassoHelper {

    /**
     * @AUTHOR : A D I T Y A   P R A T A M A
     * @DATE : 04/06/2017
     * <p>
     * Picasso Loader for load large images in Android and avoiding the out of memory error
     */

    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 768;
    private static final int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

    public static void load(int ResourceId, ImageView imgView) {
        Picasso.get()
                .load(ResourceId)
                .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .priority(Picasso.Priority.NORMAL)
                .resize(size, size)
                .centerInside()
                .into(imgView);
    }

    public static void load(String url, ImageView imgView) {
        Picasso.get()
                .load(url)
                .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .priority(Picasso.Priority.NORMAL)
                .resize(size, size)
                .centerInside()
                .into(imgView);
    }


    public static class BitmapTransform implements Transformation {

        private final int maxWidth;
        private final int maxHeight;

        public BitmapTransform(int maxWidth, int maxHeight) {
            this.maxWidth = maxWidth;
            this.maxHeight = maxHeight;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            int targetWidth, targetHeight;
            double aspectRatio;

            if (source.getWidth() > source.getHeight()) {
                targetWidth = maxWidth;
                aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                targetHeight = (int) (targetWidth * aspectRatio);
            } else {
                targetHeight = maxHeight;
                aspectRatio = (double) source.getWidth() / (double) source.getHeight();
                targetWidth = (int) (targetHeight * aspectRatio);
            }

            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return maxWidth + "x" + maxHeight;
        }

    }
}
