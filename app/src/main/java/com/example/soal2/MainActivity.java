package com.example.soal2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView mImgView;
    Bitmap mBitmap;
    Canvas mCanvas;
    private int mColorBackground;
    Paint mCirclePaint = new Paint();
    Paint mHeadPaint = new Paint();

    private ShapeDrawable ovalShape;
    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImgView = findViewById(R.id.my_img_view);

        mCirclePaint.setColor(getResources().getColor(R.color.black));
        mHeadPaint.setColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int vWidth = mImgView.getWidth();
        int vHeight = mImgView.getHeight();

        mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
        mImgView.setImageBitmap(mBitmap);
        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.yellow, null);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(mColorBackground);

        drawHead();
        drawRightEye();
        drawLeftEye();
        drawEyeConnector();

        // Animasi Fade In
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(mImgView, "alpha", 0f, 1f);
        fadeIn.setDuration(2000);

        // Animasi Flip
        AnimatorSet flip = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.flip);
        flip.setTarget(mImgView);

        // Animasi Fade Out
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(mImgView, "alpha", 1f, 0f);
        fadeOut.setDuration(2000);

        // Menerapakan Animasi
        mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(flip).before(fadeOut).after(fadeIn);

                animatorSet.start();
            }
        });
    }

    public void drawHead() {
        ovalShape = new ShapeDrawable(new OvalShape());
        ovalShape.getPaint().setColor(Color.WHITE);
        ovalShape.setBounds(200, 740, 880, 1190);
        ovalShape.draw(mCanvas);
    }

    public void drawRightEye() {
        paint = new Paint();
        paint.setColor(Color.BLACK);

        int centerX = ( mCanvas.getWidth() / 2 ) - 160;
        int centerY = mCanvas.getHeight() / 2;
        int radius = 55;

        mCanvas.drawCircle(centerX, centerY, radius, paint);
    }

    public void drawLeftEye() {
        paint = new Paint();
        paint.setColor(Color.BLACK);

        int centerX = ( mCanvas.getWidth() / 2 ) + 160;
        int centerY = mCanvas.getHeight() / 2;
        int radius = 55;

        mCanvas.drawCircle(centerX, centerY, radius, paint);
    }

    public void drawEyeConnector() {
        paint = new Paint();
        paint.setColor(Color.BLACK);

        int centerX = mCanvas.getWidth() / 2;
        int centerY = mCanvas.getHeight() / 2;

        int left = centerX - 160;
        int top = centerY - 11;
        int right = centerX + 160;
        int bottom = centerY + 11;

        mCanvas.drawRect(left, top, right, bottom, paint);
    }
}