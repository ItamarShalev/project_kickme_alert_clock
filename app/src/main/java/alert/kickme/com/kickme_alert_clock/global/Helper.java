package alert.kickme.com.kickme_alert_clock.global;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Date;

import alertData.kickme.com.kickme_alert_clock.R;

import static android.content.Context.NOTIFICATION_SERVICE;


public class Helper {


    public static class Global {


        public static void makeNotification(Context context, String message, String text) {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "chanelId");
            notification.setAutoCancel(true);
            notification.setTicker(message);
            notification.setContentText(text);
            notification.setSubText(message);
            notification.setSmallIcon(R.drawable.ic_add_alarm);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (uri != null) {
                notification.setSound(uri);
            }
            /* Intent intent = new Intent(context, SplashActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, GlobalTag.REQUEST_CODE_NOTIFICATION,intent,0);
            notification.setContentIntent(pendingIntent);*/
            NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.notify(-2, notification.build());
            }
        }
    }


    public static class Time {



        public static long getSpaceOfWeek() {
            return 7 * 24 * 60 * 1000;
        }

        public static long getTimeMillis(int dayOfWeek, int hour, int minute) {
            return getCalender(dayOfWeek, hour, minute).getTimeInMillis();
        }


        public static Calendar getCalender(int dayOfWeek, int hour, int minute) {
            Calendar alarmCalendar = Calendar.getInstance();
            alarmCalendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            setHourMinuteSecond(alarmCalendar,hour,minute,0);
            return alarmCalendar;
        }

        private static void setHourMinuteSecond(Calendar calendar, int hour, int minute, int second){
            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, second);
        }

        public static Calendar getCalender(int year, int month, int day, int hour, int minute){
            Calendar alarmCalendar = Calendar.getInstance();
            alarmCalendar.set(Calendar.YEAR,year);
            alarmCalendar.set(Calendar.MONTH,month);
            alarmCalendar.set(Calendar.DAY_OF_MONTH ,day);
            setHourMinuteSecond(alarmCalendar,hour,minute,0);
            return alarmCalendar;

        }


        public static Calendar getCalender(long timeInMillis) {
            Calendar alarmCalendar = Calendar.getInstance();
            alarmCalendar.setTimeInMillis(timeInMillis);
            return alarmCalendar;
        }

        public static Calendar getCalender(Date date) {
            return getCalender(date.getTime());
        }

        public static Calendar getCalender(java.sql.Time time) {
            return getCalender(time.getTime());
        }


    }


    public static class Image {

        public static void makeImageBlackAndWhite(ImageView imageView) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            imageView.setColorFilter(filter);
        }


        public static Bitmap getBitmapFromImageView(ImageView imageView) {



            return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        }

        public static void setImageViewCorner(ImageView imageView, int pixels) {
            imageView.setBackgroundResource(R.drawable.border_image);
          /*  Bitmap bitmap = getBitmapFromImageView(imageView);
            imageView.setImageBitmap(getRoundedCornerBitmap(imageView.getResources(),bitmap, pixels));*/
        }

        public static Bitmap getRoundedCornerBitmap(Resources resources, Bitmap bitmap, int pixels) {


          /*  RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap);
            roundedBitmapDrawable.setCircular(true);
            roundedBitmapDrawable.setCornerRadius(pixels);*/


           /* Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            RectF rectF = new RectF(rect);
            float roundPx = pixels;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
*/
            return null;
        }
    }
}
