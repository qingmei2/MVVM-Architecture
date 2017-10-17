package com.qingmei2.library.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.qingmei2.library.R;

/**
 * Created by QingMei on 2017/10/17.
 * desc:
 */

@com.bumptech.glide.annotation.GlideModule
public final class GlideModule extends AppGlideModule {

    /**
     * Using the @GlideModule annotation requires a dependency on Glide’s annotations:
     */
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {

    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context,
                diskCacheFolderName(context),
                diskCacheSizeBytes()))
                .setMemoryCache(new LruResourceCache(memoryCacheSizeBytes()));
    }

    /**
     * Implementations should return {@code false} after they and their dependencies have migrated
     * to Glide's annotation processor.
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    /**
     * set the memory cache size, unit is the {@link Byte}.
     */
    private int memoryCacheSizeBytes() {
        return 1024 * 1024 * 20; // 20 MB
    }

    /**
     * set the disk cache size, unit is the {@link Byte}.
     */
    private int diskCacheSizeBytes() {
        return 1024 * 1024 * 512; // 512 MB
    }

    /**
     * set the disk cache folder's name.
     */
    private String diskCacheFolderName(Context context) {
        return context.getString(R.string.app_name);
    }
}