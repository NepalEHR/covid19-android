package biz.melamart.www.cov19.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import biz.melamart.www.cov19.R;

public class DialogUtils {
    private static final String TAG = DialogUtils.class.getSimpleName();
    private static final String MSG_EXIT = "Are you sure you want to exit?";
    private static final String OPTION_YES = "Yes";
    private static final String OPTION_NO = "No";
    private static final int REQUEST_CHECK_SETTINGS = 999;

    private static ProgressDialog progressDialog;

    /**
     * Simple exit dialog.
     *
     * @param activity reference of initiating activity
     */
    public static void exitDialog(final Activity activity) {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(activity);
        alertDlg.setMessage(MSG_EXIT);
        alertDlg.setCancelable(false); // We avoid that the dialog can be cancelled, forcing the user to choose one of the options
        alertDlg.setPositiveButton(OPTION_YES, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                System.exit(0);
            }
        });

        alertDlg.setNegativeButton(OPTION_NO, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDlg.create().show();
    }

    /**
     * Returns {@link AlertDialog}
     *
     * @param context               {@link Context} of initiating activity
     * @param icon                  Icon to be shown in alertList
     * @param title                 {@link String} title of the dialog
     * @param msg                   {@link String} message to show on the dialog
     * @param view                  {@link View} Custom layout
     * @param positiveBtnText       {@link String} positive button text
     * @param positiveClickListener {@link DialogInterface.OnClickListener} positive button action
     * @param negativeBtnText       {@link String} positive button text
     * @param negativeClickListener {@link DialogInterface.OnClickListener} negative button action
     * @return
     */
    public static AlertDialog.Builder alertDialog(Context context, int icon, String title, String msg, View view, String positiveBtnText,
                                                  DialogInterface.OnClickListener positiveClickListener, String negativeBtnText,
                                                  DialogInterface.OnClickListener negativeClickListener) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);

        alertBuilder.setIcon(icon)
                .setTitle(title)
                .setMessage(msg)
                .setView(view)
                .setPositiveButton(positiveBtnText, positiveClickListener)
                .setNegativeButton(negativeBtnText, negativeClickListener)
                .setCancelable(false);

        AlertDialog dialog = alertBuilder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        if (!((Activity) context).isFinishing())
            alertBuilder.show();

        return alertBuilder;
    }

    /**
     * Returns simple progress dialog with message.
     *
     * @param context {@link Context} of the initiating activity
     * @param title   {@link String} title of the progress dialog
     * @param msg     {@link String} message to show in progress
     * @return {@link ProgressDialog}
     */
    public static ProgressDialog showProgressDialog(final Context context, String title, String msg, boolean indeterminate, boolean cancelable) {
        progressDialog = ProgressDialog.show(context,
                title,
                msg, indeterminate, cancelable,
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Activity activity = (Activity) context;
                        activity.finish();
                    }
                }
        );

        return progressDialog;
    }

    public static void showRectangleGlideProgressDialog(final Context context, String uri, ImageView imageView,
                                                        int overrideWidth, int overrideHeight, Priority priority) {

        if (GeneralUtils.isValidContextForGlide(context)) {
            Glide.with(context)
                    .load("" + uri)
                    .apply(new RequestOptions()
//                            .placeholder(R.drawable.)
//                            .error(R.drawable.error_no_preview)
                            .override(overrideWidth, overrideHeight)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // cache only the final image
                            .priority(priority))
                    .into(imageView);
        }
    }

    public static void showGlideProgressDialog(final Context context, ProgressBar progressBar, String uri, ImageView imageView,
                                               int overrideWidth, int overrideHeight, Priority priority) {

        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
        final ProgressBar progressView = progressBar;
        Glide.with(context)
                .load("" + uri)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_001_coronavirus)
                        .error(R.drawable.ic_001_coronavirus)
                        .override(overrideWidth, overrideHeight)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // cache only the final image
                        .priority(priority))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (progressView != null)
                            progressView.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (progressView != null)
                            progressView.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

    public static void showCircleGlideProgressDialog(final Context context, final ProgressBar progressBar, String uri, final ImageView imageView,

                                                     int overrideWidth, int overrideHeight, Priority priority) {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
        if (GeneralUtils.isValidContextForGlide(context)) {
            // Load image via Glide lib using context
            final ProgressBar progressView = progressBar;
            Glide.with(context)
                    .load("" + uri)
                    .apply(new RequestOptions().circleCropTransform()
                            .placeholder(R.drawable.ic_001_coronavirus)
                            .error(R.drawable.ic_001_coronavirus)
                            .override(overrideWidth, overrideHeight)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // cache only the final image
                            .priority(priority))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if (progressBar != null)
                                progressView.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (progressBar != null)
                                progressView.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView);
        }
    }

    public static void showCornerGlideProgressDialog(final Context context, String uri, final ImageView imageView,
                                                     int overrideWidth, int overrideHeight, Priority priority) {
        if (GeneralUtils.isValidContextForGlide(context)) {
            // Load image via Glide lib using context
            Glide.with(context)
                    .load("" + uri)
                    .apply(new RequestOptions()
//                    .placeholder(R.drawable.nearby_circle_placeholder)
//                    .error(R.drawable.error_nearby_circle_preview)
//                            .override(overrideWidth, overrideHeight)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // cache only the final image
                            .priority(priority))
                    .into(imageView);
        }
    }

    public static void dismissProgressDialog(ProgressDialog pDialog) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
