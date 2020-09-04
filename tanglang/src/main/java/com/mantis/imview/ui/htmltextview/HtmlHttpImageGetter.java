/*
 * Copyright (C) 2014-2016 Dominik Schürmann <dominik@schuermann.eu>
 * Copyright (C) 2013 Antarix Tandon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mantis.imview.ui.htmltextview;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.Html.ImageGetter;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URL;
import java.sql.SQLOutput;

public class HtmlHttpImageGetter implements ImageGetter {
    private TextView container;
    private Activity activity;
    private URI baseUri;
    private boolean matchParentWidth;

    private boolean compressImage = false;
    private int qualityImage = 50;

    public HtmlHttpImageGetter(TextView textView, Activity activity) {
        this.container = textView;
        this.activity = activity;
        this.matchParentWidth = false;
    }

    public HtmlHttpImageGetter(TextView textView, String baseUrl) {
        this.container = textView;
        if (baseUrl != null) {
            this.baseUri = URI.create(baseUrl);
        }
    }

    public HtmlHttpImageGetter(TextView textView, String baseUrl, boolean matchParentWidth) {
        this.container = textView;
        this.matchParentWidth = matchParentWidth;
        if (baseUrl != null) {
            this.baseUri = URI.create(baseUrl);
        }
    }

    public void enableCompressImage(boolean enable) {
        enableCompressImage(enable, 50);
    }

    public void enableCompressImage(boolean enable, int quality) {
        compressImage = enable;
        qualityImage = quality;
    }

    public Drawable getDrawable(String source) {
        UrlDrawable urlDrawable = new UrlDrawable();

        // get the actual source
        ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(urlDrawable, this, container,
                matchParentWidth, compressImage, qualityImage, activity);

        asyncTask.execute(source);

        // return reference to URLDrawable which will asynchronously load the image specified in the src tag
        return urlDrawable;
    }

    /**
     * Static inner {@link AsyncTask} that keeps a {@link WeakReference} to the {@link UrlDrawable}
     * and {@link HtmlHttpImageGetter}.
     * <p/>
     * This way, if the AsyncTask has a longer life span than the UrlDrawable,
     * we won't leak the UrlDrawable or the HtmlRemoteImageGetter.
     */
    private static class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        private final WeakReference<UrlDrawable> drawableReference;
        private final WeakReference<HtmlHttpImageGetter> imageGetterReference;
        private final WeakReference<View> containerReference;
        private final WeakReference<Resources> resources;
        private final WeakReference<Activity> activity;
        private String source;
        private boolean matchParentWidth;
        private float scale;

        private boolean compressImage = false;
        private int qualityImage = 50;

        public ImageGetterAsyncTask(UrlDrawable d, HtmlHttpImageGetter imageGetter, View container,
                                    boolean matchParentWidth, boolean compressImage, int qualityImage, Activity activity) {
            this.drawableReference = new WeakReference<>(d);
            this.imageGetterReference = new WeakReference<>(imageGetter);
            this.containerReference = new WeakReference<>(container);
            this.resources = new WeakReference<>(container.getResources());
            this.activity = new WeakReference<>(activity);
            this.matchParentWidth = matchParentWidth;
            this.compressImage = compressImage;
            this.qualityImage = qualityImage;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            source = params[0];

            if (resources.get() != null) {
                if (compressImage) {
                    return fetchCompressedDrawable(resources.get(), source);
                } else {
                    return fetchDrawable(resources.get(), source);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            if (result == null) {
                Log.w(HtmlTextView.TAG, "Drawable result is null! (source: " + source + ")");
                return;
            }
            final UrlDrawable urlDrawable = drawableReference.get();
            if (urlDrawable == null) {
                return;
            }
            // set the correct bound according to the result from HTTP call
            urlDrawable.setBounds(0, 0, (int) (result.getIntrinsicWidth() * scale), (int) (result.getIntrinsicHeight() * scale));

            // change the reference of the current drawable to the result from the HTTP call
            urlDrawable.drawable = result;

            final HtmlHttpImageGetter imageGetter = imageGetterReference.get();
            if (imageGetter == null) {
                return;
            }

            // redraw the image by invalidating the container
            imageGetter.container.invalidate();
            // re-set text to fix images overlapping text

            imageGetter.container.setText(imageGetter.container.getText());
        }

        /**
         * Get the Drawable from URL
         */
        public Drawable fetchDrawable(Resources res, String urlString) {
            try {
                InputStream is = fetch(urlString);
                Drawable drawable = new BitmapDrawable(res, is);
                scale = getScale(drawable);
                drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * scale), (int) (drawable.getIntrinsicHeight() * scale));
                return drawable;
            } catch (Exception e) {
                return null;
            }
        }

        /**
         * Get the compressed image with specific quality from URL
         */
        public Drawable fetchCompressedDrawable(Resources res, String urlString) {
            try {
                InputStream is = fetch(urlString);
                Bitmap original = new BitmapDrawable(res, is).getBitmap();

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                original.compress(Bitmap.CompressFormat.WEBP, qualityImage, out);
                original.recycle();
                is.close();

                Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                out.close();

                scale = getScale(decoded);
                BitmapDrawable b = new BitmapDrawable(res, decoded);

                b.setBounds(0, 0, (int) (b.getIntrinsicWidth() * scale), (int) (b.getIntrinsicHeight() * scale));
                return b;
            } catch (Exception e) {
                return null;
            }
        }

        private float getScale(Bitmap bitmap) {
            View container = containerReference.get();
            if (container == null) {
                return 1f;
            }
            float currentWidth = container.getMeasuredWidth();
            float originalDrawableWidth = bitmap.getWidth();
            //如果原图的大小小于100 直接按原比例展示
            if (originalDrawableWidth < 100) {
                return 1f;
            }

            int width = activity.get().getWindowManager().getDefaultDisplay().getWidth();
            DisplayMetrics displayMetrics = activity.get().getResources().getDisplayMetrics();
            float densityDpi = displayMetrics.density;

//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
//            System.out.println("currentWidth = " + currentWidth);
//            System.out.println("width = " + width);
//            System.out.println("originalDrawableWidth = " + originalDrawableWidth);
            float maxWidth = width - 130 * densityDpi;
//            System.out.println("maxWidth = " + maxWidth);
//            System.out.println("densityDpi = " + densityDpi);
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");

            //如果当前控件的大小 小于最大宽度的 2/1  那么就计算出控件剩余可展示控件计算
            if (currentWidth < (maxWidth / 2)) {
                return (maxWidth - currentWidth) / originalDrawableWidth;
            }
            //这里是容错  如果当前控件的大小 大于 最大宽度的5/4  那么就在当前控件大小的基础上-100再计算
            if (currentWidth > (maxWidth / 5 * 4)) {
                return (currentWidth - 100) / originalDrawableWidth;
            }
            return currentWidth / originalDrawableWidth;
        }

        private float getScale(Drawable drawable) {
            View container = containerReference.get();
            if (!matchParentWidth || container == null) {
                return 1f;
            }

            float maxWidth = container.getWidth();
            float originalDrawableWidth = drawable.getIntrinsicWidth();

            if (originalDrawableWidth < maxWidth) {
                return 1f;
            }
            return maxWidth / originalDrawableWidth;
        }

        private InputStream fetch(String urlString) throws IOException {
            URL url;
            final HtmlHttpImageGetter imageGetter = imageGetterReference.get();
            if (imageGetter == null) {
                return null;
            }
            if (imageGetter.baseUri != null) {
                url = imageGetter.baseUri.resolve(urlString).toURL();
            } else {
                url = URI.create(urlString).toURL();
            }

            return (InputStream) url.getContent();
        }
    }

    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }
} 
