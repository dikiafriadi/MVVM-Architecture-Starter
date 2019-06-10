package com.aditp.mdvkarch.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.helper.utils.OKDIT;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.signature.ObjectKey;

import java.io.InputStream;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * ------------------------------------------------------------------------------------
 *
 * @author <Aditya Pratama>
 * @implSpec <Glide Helper for lazy command and load large images in Android and avoiding the out of memory error>
 * @since <Mei 2019>
 * <p>
 * Source : https://github.com/nuhkoca/GlideAppModuleSample/blob/master/app/src/main/java/com/example/nuhkoca/appglidemodulesample/SecondActivity.java
 */

@GlideModule
public class GlideHelper extends AppGlideModule {

    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 768;
    private static final int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
    private static DrawableCrossFadeFactory factory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
    private static RequestOptions requestOptions = new RequestOptions().override(size, size).apply(RequestOptions.centerInsideTransform());
    private static RequestOptions requestOptionsRound = new RequestOptions().override(size, size).apply(RequestOptions.circleCropTransform());


    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, memoryCacheSizeBytes));
        builder.setDefaultRequestOptions(requestOptions());
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(OKDIT.CLIENT());
        glide.getRegistry().replace(GlideUrl.class, InputStream.class, factory);
    }

    private static RequestOptions requestOptions() {
        return new RequestOptions()
                .signature(new ObjectKey(System.currentTimeMillis() / (24 * 60 * 60 * 1000)))
                .override(size, size)
                .centerCrop()
                .encodeFormat(Bitmap.CompressFormat.PNG)
                .encodeQuality(100)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .format(PREFER_ARGB_8888)
                .skipMemoryCache(false);
    }


    public static void loadOriginal(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transition(withCrossFade(factory))
                .placeholder(R.color.grey_200)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadOriginal(Context context, @DrawableRes int drawable, ImageView imageView) {
        Glide.with(context)
                .load(drawable)
                .transition(withCrossFade(factory))
                .placeholder(R.color.grey_200)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);
    }


    public static void loadRound(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transition(withCrossFade(factory))
                .placeholder(R.color.grey_200)
                .apply(requestOptionsRound)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadRound(Context context, @DrawableRes int drawable, ImageView imageView) {
        Glide.with(context)
                .load(drawable)
                .transition(withCrossFade(factory))
                .apply(requestOptionsRound)
                .placeholder(R.color.grey_200)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);
    }

}
