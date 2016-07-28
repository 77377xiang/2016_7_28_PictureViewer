package com.xiang.pictureviewerproject;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    int[] images = new int[]{R.drawable.a, R.drawable.b, R.drawable.e, R.drawable.d, R.drawable.e};

    //定义默认显示图片
    int currentImg = 2;
    //定义初始图片的透明度
    private int alpha = 255;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button plus=(Button)findViewById(R.id.plus);
        final Button minus=(Button)findViewById(R.id.minus);
        Button next=(Button)findViewById(R.id.next);
        final ImageView image1=(ImageView)findViewById(R.id.image1);
        final ImageView image2=(ImageView)findViewById(R.id.image2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //控制显示下一张
                image1.setImageResource(images[++currentImg%images.length]);
            }
        });
        //定义改变透明度的方法
        View.OnClickListener listener=new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if (v == plus) {
                    alpha+=20;
                }
                if (v == minus) {
                    alpha-=20;
                }
                if (alpha >= 255) {
                    alpha=255;
                }
                if (alpha <= 0) {
                    alpha=0;
                }
                image1.setImageAlpha(alpha);
            }
        };
        //按钮监听事件
        plus.setOnClickListener(listener);
        minus.setOnClickListener(listener);
        assert image1 != null;
        image1.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BitmapDrawable bitmapDrawable=(BitmapDrawable) image1.getDrawable();
                //获取第一张图片显示框中的位置
                Bitmap bitmap=bitmapDrawable.getBitmap();
                //bitmap图片实际大小与第一个Image view 的放缩比例
                double scale=1.0*bitmap.getHeight()/image1.getHeight();
                //获取需要显示图片的开始点
                int x=(int)(event.getX()*scale);
                int y=(int)(event.getY()*scale);
                if (x+120>bitmap.getWidth()){
                    x=bitmap.getWidth()-120;
                }
                if (x+120>bitmap.getHeight()){
                    x=bitmap.getHeight()-120;
                }
                //显示图片的指定区域
                image2.setImageBitmap(Bitmap.createBitmap(bitmap,x,y,120,120));
                image2.setImageAlpha(alpha);
                return false;
            }
        });
    }
}
