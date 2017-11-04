package com.bitmapdemo.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class LoadImageActivity extends Activity {

    private Button    mBtn_load;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);

        //初始化控件
        mBtn_load = (Button) findViewById(R.id.btn_load);
        mIv = (ImageView) findViewById(R.id.iv);

        //设置监听
        mBtn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拿到位图
                //                Bitmap bitmap= BitmapFactory.decodeFile("mnt/sdcard/image.jpg");
                //                mIv.setImageBitmap(bitmap);
                //                method1();

                //第二种,如：ImageView所期望的图片大小为150*150像素，加载显示图片
                mIv.setImageBitmap(decodeSampleBitmapFromResource(getResources(), R.drawable.image, 150, 150));

            }
        });
    }

    /**
     * @param res
     * @param resId
     * @param reqWidth  期望图片宽（像素）
     * @param reqHeight 期望图片高（像素）
     * @return
     */
    public static Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        //计算采样率
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 获取采样率
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //获取图片的宽和高
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            //计算最大的采样率，采样率为2的指数
            while ((halfHeight / inSampleSize) >= reqHeight && (halfHeight / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * 加载大图片的方法一
     */
    private void method1() {
        //解码图片的配置选项
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置options里面的参数，为true,不去真实地解析Bitmap,而是查询Bitmap的宽高信息
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile("mnt/sdcard/image.jpg", options);
        Log.e("TAG", "bitmap==" + bitmap);//bitmap=null

        //获取图片的宽高
        int height = options.outHeight;
        int width = options.outWidth;
        Log.e("TAG", "图片的宽度，width==" + width);
        Log.e("TAG", "图片的高度，width==" + height);

        //获取手机屏幕的宽高,拿到窗体管理者
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        //getDefaultDisplay获取屏幕分辨率
        int screenWidth = wm.getDefaultDisplay().getWidth();
        int screenHeight = wm.getDefaultDisplay().getHeight();
        Log.e("TAG", "屏幕的宽度，width==" + screenWidth);
        Log.e("TAG", "屏幕的高度，width==" + screenHeight);

        //计算图片和屏幕宽高的比例
        int dx = width / screenWidth;
        int dy = height / screenHeight;
        //缩放比例
        int scale = 1;

        //比如图片：960*480  屏幕：480*320  dx=2  dy=1.5,取dx=2，它的宽高都在屏幕里面了
        //dx<1说明图片还没有屏幕高，就不需要缩放了
        if (dx > dy && dy > 1) {
            scale = dx;
        }
        if (dy > dx && dx > 1) {
            scale = dy;
        }

        Log.e("TAG", "scale==" + scale);

        //以缩放的方式把图片加载到手机内存
        options.inSampleSize = scale;
        //真实地解析bitmap
        options.inJustDecodeBounds = false;
        Bitmap bitmap2 = BitmapFactory.decodeFile("mnt/sdcard/image.jpg", options);
        mIv.setImageBitmap(bitmap2);
    }
}
