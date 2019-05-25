package com.aditp.mdvkarch.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;

import com.aditp.mdvkarch.BuildConfig;
import com.aditp.mdvkarch.R;
import com.aditp.mdvkarch.databinding.DialogAboutBinding;
import com.aditp.mdvkarch.databinding.DialogLoadingBinding;
import com.aditp.mdvkarch.databinding.DialogQuestionBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.TlsVersion;


@SuppressWarnings({"WeakerAccess", "unused", "deprecation"})
public class MDVK {
    private static final String TAG = "MDVK";
    private static final Pattern IP_ADDRESS = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))");
    private static String formatDate = "yyyy-MM-dd";
    private static ProgressDialog dialog;
    private static Pattern pattern;
    private static Matcher matcher;

    // ------------------------------------------------------------------------
    // OK HTTP CLIENT
    // ------------------------------------------------------------------------
    public static OkHttpClient MY_UNSAFE_OKHTTP_CLIENT() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{

                    new X509TrustManager() {

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            //Using TLS 1_2 & 1_1 for HTTP/2 Server requests
            // Note : The following is suitable for my Server. Please change accordingly
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
                    .build();

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(chain -> {
                Request request = chain.request();
                okhttp3.Response response = chain.proceed(request);
                // error case
                if (response.code() != 200){
                    switch (response.code()) {
                        case 400:
                            Log.d("400", "400 Bad Request");
                            break;
                        case 401:
                            Log.d("401", " 401 Unauthorized");
                            break;
                        case 403:
                            Log.d("403", " 401 Forbidden");
                            break;
                        case 404:
                            Log.d("404", "404 Not Found");
                            break;
                        case 405:
                            Log.d("405", "405 Method Not Allowed");
                            break;
                        case 415:
                            Log.d("415", "415 Unsupported Media Type");
                            break;
                        case 500:
                            Log.d("500", "500 Server Broken");
                            break;
                        default:
                            Log.d(String.valueOf(response.code()), response.message());
                            break;
                    }
                }
                return response;
            });
            builder.readTimeout(15, TimeUnit.SECONDS);
            builder.writeTimeout(15, TimeUnit.SECONDS);
            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.sslSocketFactory(sslSocketFactory);
            builder.connectionSpecs(Collections.singletonList(spec));
            builder.hostnameVerifier((hostname, session) -> true);
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class TIME_PICKER {
        public static void showDatePicker(Context context, TextView textView) {
            DatePickerDialog datePickerDialog;
            SimpleDateFormat dateFormatter = new SimpleDateFormat(formatDate, Locale.US);
            Calendar newCalendar = Calendar.getInstance();
            datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                textView.setText(dateFormatter.format(newDate.getTime()));
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }


        @SuppressLint("DefaultLocale")
        public static void showTimePicker(Context context, TextView textView) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog;

            timePickerDialog = new TimePickerDialog(context,
                    (view, hourOfDay, minute) -> textView.setText(String.format("%02d:%02d", hourOfDay, minute)), mHour, mMinute, true);
            timePickerDialog.show();
        }
    }

    public static class DIALOG_TOOLS {
        public static void showAlertDialog(Context context, String title, String msg, String buttonText) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            alertDialog.setCancelable(false);
            alertDialog.setMessage(msg);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, buttonText, (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        }

        public static void showAlertDialog(Context context, String title, String msg, Boolean isCancel, final ActionDialogListener actionDialogListener) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            alertDialog.setCancelable(isCancel);
            alertDialog.setMessage(msg);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                    (dialog, which) -> {
                        actionDialogListener.executeYes();
                        alertDialog.dismiss();
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                    (dialog, which) -> {
                        alertDialog.dismiss();
                        actionDialogListener.executeNo();
                    });
            alertDialog.show();
        }

        public static void showCustomDialog(Context context, String title, String msg, final ActionDialogListener actionDialogListener) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);

            DialogQuestionBinding binding;
            int LAYOUT = R.layout.dialog_question;

            binding = DataBindingUtil.inflate(LayoutInflater.from(context), LAYOUT, null, false);
            dialog.setContentView(binding.getRoot());
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            binding.tvTitle.setText(title);
            binding.tvMessage.setText(msg);

            binding.btnNo.setOnClickListener(v -> {
                actionDialogListener.executeNo();
                dialog.dismiss();
            });

            binding.btnYes.setOnClickListener(v -> {
                actionDialogListener.executeYes();
                dialog.dismiss();
            });

            dialog.show();
            dialog.getWindow().setAttributes(lp);
        }

        // with custome drawable
        @SuppressLint("SetTextI18n")
        public static void showCustomDialog(Context context, String title, String msg, int drawable, final ActionDialogListener actionDialogListener) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);

            DialogQuestionBinding binding;
            int LAYOUT = R.layout.dialog_question;

            binding = DataBindingUtil.inflate(LayoutInflater.from(context), LAYOUT, null, false);
            dialog.setContentView(binding.getRoot());
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


            binding.ivImage.setImageResource(drawable);
            binding.btnNo.setVisibility(View.GONE);
            binding.btnYes.setText("OK");
            binding.tvTitle.setText(title);
            binding.tvMessage.setText(msg);
            binding.btnYes.setOnClickListener(v -> {
                actionDialogListener.executeYes();
                dialog.dismiss();
            });
            dialog.show();
            dialog.getWindow().setAttributes(lp);
        }


        public static Dialog showProgressDialog(Context context) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            DialogLoadingBinding binding;
            int LAYOUT = R.layout.dialog_loading;

            binding = DataBindingUtil.inflate(LayoutInflater.from(context), LAYOUT, null, false);
            dialog.setContentView(binding.getRoot());
            dialog.setCancelable(true);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            binding.tvStatus.setVisibility(View.GONE);
            dialog.show();
            dialog.getWindow().setAttributes(lp);

            return dialog;
        }


        public static Dialog showProgressDialog(Context context, String message) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            DialogLoadingBinding binding;
            int LAYOUT = R.layout.dialog_loading;

            binding = DataBindingUtil.inflate(LayoutInflater.from(context), LAYOUT, null, false);
            dialog.setContentView(binding.getRoot());
            dialog.setCancelable(true);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            binding.tvStatus.setText(message);
            dialog.show();
            dialog.getWindow().setAttributes(lp);

            return dialog;
        }

        // ------------------------------------------------------------------------
        // ABOUT DIALOG
        // ------------------------------------------------------------------------
        public static void showAboutDialog(Context context) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
            dialog.setCancelable(false);


            DialogAboutBinding binding;
            int LAYOUT = R.layout.dialog_about;

            binding = DataBindingUtil.inflate(LayoutInflater.from(context), LAYOUT, null, false);
            dialog.setContentView(binding.getRoot());
            dialog.setCancelable(true);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


            binding.btnRate.setOnClickListener(v -> {
                Toast.makeText(context, "rate terklik, direct playstore ~", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });

            binding.btnContactUs.setOnClickListener(v -> {
                Toast.makeText(context, "contact us terklik, direct ke link website", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
            binding.btClose.setOnClickListener(v -> dialog.dismiss());
            dialog.show();
            dialog.getWindow().setAttributes(lp);
        }
    }

    public static class NETWORK_TOOLS {
        public static boolean isOnline(Context context) {
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnected();
        }

        public static boolean isOnlineShowDialog(Context c) {
            if (isOnline(c))
                return true;
            else
                noConnection(c);
            return false;
        }

        public static void noConnection(final Context context) {
            DIALOG_TOOLS.showAlertDialog(context,
                    "Servers unreachable",
                    "You seem to have no internet connection, this app requires internet to load the latest content.\n\nYou can continue to see your favorite/offline items!",
                    "OK");
        }

        public static boolean isValidIP(String ip) {
            boolean validate = false;
            Matcher matcher = IP_ADDRESS.matcher(ip);
            if (matcher.matches()) {
                return true;
            }
            return validate;
        }

        public static boolean isGPSEnable(Context context) {

            final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }

        public static void showGPSEnableDialog(final Context context) {
            showGPSEnableDialog(context, "Your GPS seems to be disabled, do you want to enable it?", "Yes", "No");
        }

        public static void showGPSEnableDialog(final Context context, String message, String positiveButtonText, String negativeButtonText) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(positiveButtonText, (dialog, id) -> context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .setNegativeButton(negativeButtonText, (dialog, id) -> dialog.cancel());
            final AlertDialog alert = builder.create();
            alert.show();
        }


        public static Boolean isMockLocationEnabled(Context context) {
            return !Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0");
        }

        public static boolean isMockLocationEnabled(Location location) {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && location != null && location.isFromMockProvider();
        }
    }

    public static class STRING_TOOLS {
        public static String getCurrentDateTime(String format) {
            return getTimeByFormat(format);
        }

        public static String getCurrentTime(String format) {
            return getTimeByFormat(format);
        }

        public static String getCurrentDateTime() {
            return getTimeByFormat(formatDate);
        }

        public static String getCurrentTime() {
            return getTimeByFormat("hh:mm");
        }

        private static String getTimeByFormat(String format) {
            Calendar c = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(format);
            df.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
            return df.format(c.getTime());
        }

        public static String getMonthName(String value) {

            if (value.equals("01") || value.equals("1")) {
                return "Jan";
            }
            if (value.equals("02") || value.equals("2")) {
                return "Feb";
            }
            if (value.equals("03") || value.equals("3")) {
                return "Mar";
            }
            if (value.equals("04") || value.equals("4")) {
                return "Apr";
            }
            if (value.equals("05") || value.equals("5")) {
                return "May";
            }
            if (value.equals("06") || value.equals("6")) {
                return "Jun";
            }
            if (value.equals("07") || value.equals("7")) {
                return "Jul";
            }
            if (value.equals("08") || value.equals("8")) {
                return "Aug";
            }
            if (value.equals("09") || value.equals("9")) {
                return "Sep";
            }
            if (value.equals("10")) {
                return "Oct";
            }
            if (value.equals("11")) {
                return "Nov";
            }
            if (value.equals("12")) {
                return "Dec";
            }
            return "Not Found";
        }

        public static String toCamelCase(String input) {
            input = input.toLowerCase();
            StringBuilder titleCase = new StringBuilder();
            boolean nextTitleCase = true;

            for (char c : input.toCharArray()) {
                if (Character.isSpaceChar(c)) {
                    nextTitleCase = true;
                } else if (nextTitleCase) {
                    c = Character.toTitleCase(c);
                    nextTitleCase = false;
                }

                titleCase.append(c);
            }

            return titleCase.toString();
        }

        public static String insertPeriodically(String text, String insert, int period) {
            StringBuilder builder = new StringBuilder(text.length() + insert.length() * (text.length() / period) + 1);
            int index = 0;
            String prefix = "";
            while (index < text.length()) {
                builder.append(prefix);
                prefix = insert;
                builder.append(text.substring(index, Math.min(index + period, text.length())));
                index += period;
            }
            return builder.toString();
        }


        public static String convertTimeFromServer(String utcTime) {
            try {
                // template server : hh mm ss
                String jam = utcTime.substring(0, 2);
                String menit = utcTime.substring(2, 4);
                return jam + ":" + menit;
            } catch (Exception ignore) {
            }
            return "";

        }


        public static String convertDateToServer(String utcTime) {
            try {
                return utcTime.replace("-", "");
            } catch (Exception ignored) {
            }
            return "";
        }


        public static String convertTimeToServer(String utcTime) {
            try {
                Log.d(TAG, "convertTimeToServer: " + utcTime.replace(":", ""));
                return utcTime.replace(":", "");
            } catch (Exception ignored) {
            }
            return "";

        }

        public static String convertDateFromServer(String utcTime) {
            try {
                // template server : yyyy MM dd
                String tahun = utcTime.substring(0, 4);
                String bulan = utcTime.substring(4, 6);
                String hari = utcTime.substring(6, 8);
                return hari + " " + getMonthName(bulan) + " " + tahun;
            } catch (Exception ignored) {
            }
            return "";

        }

        public static String getFormatFromIntegerValue(int value) {
            String amount = String.valueOf(value);
            amount = amount.replaceAll(",", "");
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(Double.valueOf(amount));
        }

        public static String getFormatFromDecimalValue(Double value) {
            String amount = String.valueOf(value);
            amount = amount.replaceAll(",", "");
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            return decimalFormat.format(Double.valueOf(amount));
        }

        public static long getCurrentTimeInMillis() {
            long currentTimeMillis;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            currentTimeMillis = calendar.getTimeInMillis();
            return currentTimeMillis;
        }


        public static long parseDate(String text) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm",
                    Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                return dateFormat.parse(text).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }

        public static long getHoursDifferent(long startTime, long currentTime) {
            long hours;
            long diff = currentTime - startTime;
            hours = diff / (60 * 60 * 1000);
            return hours;
        }

        public static String getDateDiff(String dateStart, String dateStop) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(formatDate);

            Date d1 = null;
            Date d2 = null;
            try {
                d1 = format.parse(dateStart);
                d2 = format.parse(dateStop);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Get msec from each, and subtract.
            long diff = d2.getTime() - d1.getTime();

            long days = TimeUnit.MILLISECONDS.toDays(diff);
            long remainingHoursInMillis = diff - TimeUnit.DAYS.toMillis(days);
            long hours = TimeUnit.MILLISECONDS.toHours(remainingHoursInMillis);
            long remainingMinutesInMillis = remainingHoursInMillis - TimeUnit.HOURS.toMillis(hours);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMinutesInMillis);
            long remainingSecondsInMillis = remainingMinutesInMillis - TimeUnit.MINUTES.toMillis(minutes);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(remainingSecondsInMillis);

            String tmp = String.valueOf(days);
            int total = Integer.parseInt(tmp);
            total = total + 1;
            return String.valueOf(total);
        }

        public static boolean isObjectNull(Object object) {
            return (object == null);
        }

        public static String getCurrencyFormat(int paramInt) {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("IDR ");
            localStringBuilder.append(NumberFormat.getIntegerInstance().format(paramInt).replace(',', '.'));
            return localStringBuilder.toString();
        }

        public static String getCurrencyFormat(long paramLong) {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("IDR ");
            localStringBuilder.append(NumberFormat.getIntegerInstance().format(paramLong).replace(',', '.'));
            return localStringBuilder.toString();
        }

        public static String getPhoneFormat(String paramString) {
            Object localObject = paramString;
            if (paramString.contains(" ")) {
                localObject = new StringBuilder();
                ((StringBuilder) localObject).append("+");
                ((StringBuilder) localObject).append(paramString);
                localObject = localObject.toString();
            }
            return (String) localObject;
        }

        public static Bitmap getRoundedBitmap(Bitmap paramBitmap, int paramInt) {
            Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas localCanvas = new Canvas(localBitmap);
            Paint localPaint = new Paint();
            localPaint.setAntiAlias(true);
            RectF localRectF = new RectF(new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight()));
            localCanvas.drawRoundRect(localRectF, (float) paramInt, (float) paramInt, localPaint);
            localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint);
            paramBitmap.recycle();
            return localBitmap;
        }

        public static String nullToEmptyString(String paramString) {
            String str = paramString;
            if (paramString == null) {
                str = "";
            }
            return str;
        }

        public static String setTwoDigits(int paramInt) {
            if (paramInt < 10) {
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("0");
                localStringBuilder.append(paramInt);
                return localStringBuilder.toString();
            }
            return String.valueOf(paramInt);
        }

        public static String[] splitDateTime(String paramString) {
            return new String[]{paramString.substring(0, 10), paramString.substring(11, 16)};
        }

        public static String capitalizeFirstLetter(String original) {
            original = original.toLowerCase();
            if (original.length() == 0) {
                return original;
            }
            return original.substring(0, 1).toUpperCase() + original.substring(1);
        }

        public static String dobleToString(double val) {
            String NUMBER = String.valueOf(val);
            if (NUMBER.contains(".0")) {
                return NUMBER.replaceAll(".?0*$", "");
            }
            return NUMBER;
        }


        public static Date addDays(Date date, int days) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, days); //minus number would decrement the days
            return cal.getTime();
        }
    }

    public static class WINDOW_TOOLS {
        // FULLSCREEN
        public static void setActivityToFullScreen(AppCompatActivity activity) {
            try {
                activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } catch (Exception ignore) {

            }
        }


        public static void setSystemBarColor(Activity act) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = act.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryDark));
            }
        }

        public static void setSystemBarColor(Activity act, @ColorRes int color) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = act.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(act.getResources().getColor(color));
            }
        }

        public static void setSystemBarColorDialog(Context act, Dialog dialog, @ColorRes int color) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = dialog.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(act.getResources().getColor(color));
            }
        }

        public static void setSystemBarLight(Activity act) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View view = act.findViewById(android.R.id.content);
                int flags = view.getSystemUiVisibility();
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                view.setSystemUiVisibility(flags);
            }
        }

        public static void setSystemBarLightDialog(Dialog dialog) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View view = dialog.findViewById(android.R.id.content);
                int flags = view.getSystemUiVisibility();
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                view.setSystemUiVisibility(flags);
            }
        }

        public static void clearSystemBarLight(Activity act) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = act.getWindow();
                window.setStatusBarColor(ContextCompat.getColor(act, R.color.colorPrimaryDark));
            }
        }

        /**
         * Making notification bar transparent
         */
        public static void setSystemBarTransparent(Activity act) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = act.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }


        public static int dpToPx(Context c, int dp) {
            Resources r = c.getResources();
            return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
        }


        public static void copyToClipboard(Context context, String data) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("clipboard", data);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
        }

        public static void nestedScrollTo(final NestedScrollView nested, final View targetView) {
            nested.post(() -> nested.scrollTo(500, targetView.getBottom()));
        }

        public static int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        public static int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }

        public static boolean toggleArrow(View view) {
            if (view.getRotation() == 0) {
                view.animate().setDuration(200).rotation(180);
                return true;
            } else {
                view.animate().setDuration(200).rotation(0);
                return false;
            }
        }

        public static boolean toggleArrow(boolean show, View view) {
            return toggleArrow(show, view, true);
        }

        public static boolean toggleArrow(boolean show, View view, boolean delay) {
            if (show) {
                view.animate().setDuration(delay ? 200 : 0).rotation(180);
                return true;
            } else {
                view.animate().setDuration(delay ? 200 : 0).rotation(0);
                return false;
            }
        }

        public static void changeNavigateionIconColor(Toolbar toolbar, @ColorInt int color) {
            Drawable drawable = toolbar.getNavigationIcon();
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }

        public static void changeMenuIconColor(Menu menu, @ColorInt int color) {
            for (int i = 0; i < menu.size(); i++) {
                Drawable drawable = menu.getItem(i).getIcon();
                if (drawable == null) continue;
                drawable.mutate();
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }

        public static void changeOverflowMenuIconColor(Toolbar toolbar, @ColorInt int color) {
            try {
                Drawable drawable = toolbar.getOverflowIcon();
                drawable.mutate();
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            } catch (Exception ignored) {
            }
        }

        public static int getScreenWidth() {
            return Resources.getSystem().getDisplayMetrics().widthPixels;
        }

        public static int getScreenHeight() {
            return Resources.getSystem().getDisplayMetrics().heightPixels;
        }

        public static void forceCloseKeyboard(Context context, EditText editText) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }

        public static void forceOpenKeyboard(Context context) {
            // force open keyboard
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }


        public static void rateAction(Activity activity) {
            Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                activity.startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
            }
        }
    }

    public static class LocalNotification {

        private static NotificationManager notifManager;

        public static void createNotification(Context context, String aTitle, String aMessage) {
            final int NOTIFY_ID = 0; // ID of notification
            String id = BuildConfig.VERSION_NAME; // default_channel_id
            String title = context.getString(R.string.app_name); // Default Channel
            Intent intent;
            PendingIntent pendingIntent;
            NotificationCompat.Builder builder;

            // GETTING SERVICE
            if (notifManager == null) {
                notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }

            // SINCE ANDROID 0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel mChannel = notifManager.getNotificationChannel(id);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(id, title, importance);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notifManager.createNotificationChannel(mChannel);
                }
                builder = new NotificationCompat.Builder(context, id);


                // OPEN URL
                //String uri1 = getIt("be28b91e90e37425bb20a81dc31977dccd1dab17841da9ddb729b916c419bcddc223ac1dcb28741ac51bb423ca2c731ebe24");
                //intent = new Intent(context, CustomWeb.class);
                //intent.putExtra("URL", uri1);
                //pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);

                builder.setContentTitle(aTitle)                             // required
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                        .setSmallIcon(R.mipmap.ic_launcher)   // required
                        .setContentText(aMessage) // required
                        .setDefaults(Notification.BADGE_ICON_LARGE)
                        .setAutoCancel(true)
                        // .setContentIntent(pendingIntent) -> open intent
                        .setTicker(aTitle)
                        //.setOngoing(true)
                        .setLights(Color.BLUE, 3000, 3000)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            }

            // OTHER SDK
            else {
                builder = new NotificationCompat.Builder(context, id);

                //String uri1 = getIt("be28b91e90e37425bb20a81dc31977dccd1dab17841da9ddb729b916c419bcddc223ac1dcb28741ac51bb423ca2c731ebe24");
                //intent = new Intent(context, CustomWeb.class);
                //intent.putExtra("URL", uri1);
                //pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);

                builder.setContentTitle(aTitle)                            // required
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                        .setSmallIcon(R.mipmap.ic_launcher)   // required
                        .setContentText(aMessage) // required
                        .setAutoCancel(true)
                        .setTicker(aTitle)
                        //.setOngoing(true)
                        .setLights(Color.BLUE, 3000, 3000)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setPriority(Notification.PRIORITY_HIGH);
            }
            Notification notification = builder.build();
            notifManager.notify(NOTIFY_ID, notification);
        }
    }

    // ------------------------------------------------------------------------
    // INTERFACE
    // ------------------------------------------------------------------------
    public interface ActionDialogListener {
        void executeNo();

        void executeYes();
    }
}
