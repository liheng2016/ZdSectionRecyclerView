package view.niudong.com.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import base.BaseActivity;

public class Main2Activity extends BaseActivity {

    private ImageView imageView;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_main2);
        imageView = findViewById(R.id.iv);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        String url = "https://dev.img.zhiervip.com/official/share/7120bf239ca3624fbde517a17cbac791.jpg";
        Glide.with(Main2Activity.this).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource != null) {
                    Bitmap bitmap = add2WeiMa(resource);
                    imageView.setImageBitmap(bitmap);
                }

            }
        });
    }
    /**
     * 分享图标的时候，统一加上二维码在底部。 <br>
     * 微博分享的除外。
     */
    private Bitmap add2WeiMa(Bitmap bitmap1) {
        Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.mthq);
        Bitmap bitmap3 = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight() + bitmap2.getHeight(),
                bitmap1.getConfig());//Bitmap.Config.ARGB_8888);——代表32位ARGB位图
        Canvas canvas = new Canvas(bitmap3);
        //1.
        canvas.drawBitmap(bitmap1, new Matrix(), null);
        //2.
        Paint paint = new Paint();
        paint.setColor(this.getResources().getColor(R.color.xn_white));
        canvas.drawRect(new Rect(0, bitmap1.getHeight(), bitmap1.getWidth(), bitmap1.getHeight() + bitmap2.getHeight()), paint);

        int offX = (int) ((bitmap1.getWidth() - bitmap2.getWidth()) / 2.0);
        //3.
        canvas.drawBitmap(bitmap2, offX > 0 ? offX : 0, bitmap1.getHeight(), null); // x、y为bitmap2写入点的x、y坐标
        //清空图片
        return bitmap3;
    }


}
