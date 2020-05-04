package biz.melamart.www.cov19.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class GeneralUtils {

    private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
    private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
    private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
    private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
    private static final char DIVIDER = '-';

    private static int imageHeight;
    private static int imageWidth;
    private static int hour;
    private static int minute;


    public static boolean isInputCorrect(Editable s) {
        boolean isCorrect = s.length() <= TOTAL_SYMBOLS; // check size of entered string
        for (int i = 0; i < s.length(); i++) { // check that every element is right
            if (i > 0 && (i + 1) % DIVIDER_MODULO == 0) {
                isCorrect &= DIVIDER == s.charAt(i);
            } else {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }
        return isCorrect;
    }

    public static String buildCorrectString(char[] digits) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % DIVIDER_POSITION) == 0)) {
                    formatted.append(DIVIDER);
                }
            }
        }

        return formatted.toString();
    }

    public static char[] getDigitArray(final Editable s) {
        char[] digits = new char[TOTAL_DIGITS];
        int index = 0;
        for (int i = 0; i < s.length() && index < TOTAL_DIGITS; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }
        return digits;
    }

    public static void CardExpirationPattern(Editable editable) {
        if (editable.length() > 0 && (editable.length() % 3) == 0) {
            final char c = editable.charAt(editable.length() - 1);
            if ('/' == c) {
                editable.delete(editable.length() - 1, editable.length());
            }
        }
        if (editable.length() > 0 && (editable.length() % 3) == 0) {
            char c = editable.charAt(editable.length() - 1);
            if (Character.isDigit(c) && TextUtils.split(editable.toString(), String.valueOf("/")).length <= 2) {
                editable.insert(editable.length() - 1, String.valueOf("/"));
            }
        }
    }

    public static void getDate(Activity activity, String date, DatePickerDialog.OnDateSetListener mDateSetListener) {
        int year;
        int month;
        int day;
        if (date == null || date.isEmpty()) {
            Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Date dateObj = null;
            try {
                dateObj = sdf.parse(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateObj);
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
            } catch (ParseException e) {
                e.printStackTrace();
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
            }
        }

        DatePickerDialog dialog = new DatePickerDialog(
                activity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public static void getDateYearMonth(Activity activity, String date, DatePickerDialog.OnDateSetListener mDateSetListener) {
        int year;
        int month;
        int day;
        if (date == null || date.isEmpty()) {
            Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm");
            Date dateObj = null;
            try {
                dateObj = sdf.parse(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateObj);
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
            } catch (ParseException e) {
                e.printStackTrace();
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
            }
        }

        DatePickerDialog dialog = new DatePickerDialog(
                activity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    //only used for trip booking time
    public static void getTimeForBookingTrip(Activity activity, TimePickerDialog.OnTimeSetListener mTimeListener) {
        Calendar cal = Calendar.getInstance();
        // From calander get the hour, minute
//        int hour = cal.get(Calendar.HOUR_OF_DAY);
//        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(
                activity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mTimeListener,
                hour, minute, false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public static String getRoadName(Context context, boolean isFullName, double latitude, double longitude) {
        String knownName = "Unnamed Road";
        List<Address> addresses = new ArrayList<>();
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null) {
            if (addresses.size() != 0) {
                String str = addresses.get(0).getAddressLine(0);
                if (!isFullName) {
                    try {
                        knownName = str.substring(0, str.indexOf(","));
                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        knownName = str;
                    }
                } else {
                    knownName = str;
                }
            }
        }
        return knownName;
    }

    public static void setImageHeight() {
        imageHeight = ResourceUtils.getImageHeight();
    }

    public static void setImageWidth() {
        imageWidth = ResourceUtils.getImageWidth();
    }

    public static int getImageHeight() {
        return imageHeight;
    }

    public static int getImageWidth() {
        return imageWidth;
    }

    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (activity.isDestroyed() || activity.isFinishing()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void shareAppUrl(Context context) {
        String shareUrl = "play.google.com/store/apps/details?id=com.travelbyhour.rider";

        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(shareUrl));
        emailIntent.setType("message/rfc822");

        PackageManager pm = context.getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");

        Intent openInChooser = Intent.createChooser(emailIntent, "Share Rider App");

        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if (packageName.contains("android.email")) {
                emailIntent.setPackage(packageName);
            } else if (packageName.contains("twitter") || packageName.contains("facebook") || packageName.contains("mms")
                    || packageName.contains("android.gm") || packageName.contains("instagram") || packageName.contains("viber") || packageName.contains("whatsapp")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                if (packageName.contains("twitter")) {
                    intent.putExtra(Intent.EXTRA_TEXT, shareUrl);
                } else if (packageName.contains("facebook")) {
                    // Warning: Facebook IGNORES our text. They say "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the user voice."
                    // One workaround is to use the Facebook SDK to post, but that doesn't allow the user to choose how they want to share. We can also make a custom landing page, and the link
                    // will show the <meta content ="..."> text from that page with our link in Facebook.
                    intent.putExtra(Intent.EXTRA_TEXT, shareUrl);
                } else if (packageName.contains("mms")) {
                    intent.putExtra(Intent.EXTRA_TEXT, shareUrl);
                } else if (packageName.contains("instagram")) {
                    intent.putExtra(Intent.EXTRA_TEXT, shareUrl);
                } else if (packageName.contains("viber")) {
                    intent.putExtra(Intent.EXTRA_TEXT, shareUrl);
                } else if (packageName.contains("whatsapp")) {
                    intent.putExtra(Intent.EXTRA_TEXT, shareUrl);
                } else if (packageName.contains("android.gm")) { // If Gmail shows up twice, try removing this else-if clause and the reference to "android.gm" above
                    intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(shareUrl));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Rider App");
                    intent.setType("message/rfc822");
                }
                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }

        // convert intentList to array
        LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);
        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        context.startActivity(openInChooser);
    }

    public static boolean isLocationUpdateServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        String date = year + "-" + month + "-" + day;
        return date;
    }

    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        String time = hour + ":" + minute + ":" + second;
        return time;
    }

    public static String getCurrentLocalDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        String date = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
        return date;
    }

    //only used for trip booking time
    public static String getCurrentLocalTimeWithExtendFiveMinute() {
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE) + 5;
        int second = cal.get(Calendar.SECOND);
        if (minute > 60) {
            hour = hour + (minute / 60);
            minute = (minute % 60);
        }
        String time = String.format("%02d", hour) + ":" + String.format("%02d", minute) + ":" + String.format("%02d", second);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            Date dateObj = sdf.parse(time);
            time = new SimpleDateFormat("K:mm aa").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
            time = "n/a";
        }

        return time;
    }


    public static String convertToNewFormat(String dateStr, String destFormatString) throws ParseException {
        SimpleDateFormat sourceFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sourceFormatter.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
        Date date = sourceFormatter.parse(dateStr);

        SimpleDateFormat desFormatter = new SimpleDateFormat(destFormatString);
        desFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return desFormatter.format(date);
    }

    public static String convertGMTtoLocal(String dateStr, String destFormatString) throws ParseException {
        SimpleDateFormat sourceFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sourceFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = sourceFormatter.parse(dateStr);

        SimpleDateFormat desFormatter = new SimpleDateFormat(destFormatString);
        desFormatter.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
        return desFormatter.format(date);
    }
    public static String getConvertedDate(String date)
    {
        String conDate = "2020-03-31 08:28:31";

            String[]  date1 = date.split(" ");
        String[] newDate = date1[0].trim().split("-");
        String month ="1";

        switch (newDate[1].trim())
        {
            case "01":
                month="JAN";
                        break;
            case "02":
                month="FEB";
                break;
            case "03":
                month="MAR";
                break;
            case "04":
                month="APR";
                break;
            case "05":
                month="MAY";
                break;
            case "06":
                month="JUN";
                break;
            case "07":
                month="JUL";
                break;
            case "08":
                month="AUG";
                break;
            case "09":
                month="SEP";
                break;
            case "10":
                month="OCT";
                break;
            case "11":
                month="NOV";
                break;
            case "12":
                month="DEC";
                break;
        }


        return  month + " "+ newDate[2] ;
    }
    public static long convertDateToTimestamp(String str_date) {
        long timestamp = 0;

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date) formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long output = date.getTime() / 1000L;
        String str = Long.toString(output);
        timestamp = Long.parseLong(str) * 1000;

        return timestamp;
    }


    public static boolean isInternetAvailable(Context context) {
//        ConnectivityManager cm = (ConnectivityManager)context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if (activeNetwork != null && activeNetwork.isConnected()) {
//            try {
//                URL url = new URL("http://www.google.com/");
//                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
//                urlc.setRequestProperty("User-Agent", "test");
//                urlc.setRequestProperty("Connection", "close");
//                urlc.setConnectTimeout(1000); // mTimeout is in seconds
//                urlc.connect();
//                if (urlc.getResponseCode() == 200) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } catch (IOException e) {
//                Log.i("warning", "Error checking internet connection", e);
//                return false;
//            }
//        }
//
//        return false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }



    public static List<String> getImageUrlInList(String url) {
        if (url == null || url.isEmpty())
            return new ArrayList<>();
        String[] elements = url.split(",");
        return Arrays.asList(elements);
    }
}
