package edu.wschina.a03;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CircleView extends androidx.appcompat.widget.AppCompatImageView {

    private Paint mPaint;
    private Bitmap mBitmap;
    private float circleCornerRadius; // 圆角半径

    public CircleView(@NonNull Context context) {
        this(context,null);
    }

    public CircleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Drawable drawable = getDrawable();

        if (drawable instanceof BitmapDrawable){

            mBitmap = ((BitmapDrawable) drawable).getBitmap();

        }

    }

    private void init(AttributeSet attrs) {

        //初始化画笔，设置画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);

        // 从 XML 中获取圆角半径属性
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircleView);
            circleCornerRadius = a.getDimension(R.styleable.CircleView_circleCornerRadius, 0f);  // 默认值为 0f
            a.recycle(); // 记得回收
        }

    }


    //判断执行绘画
    @Override
    protected void onDraw(Canvas canvas) {

        if (mBitmap != null){

            drawCircle(canvas);

        }else {

            super.onDraw(canvas);

        }

    }

    //绘制方法
    private void drawCircle(Canvas canvas) {
        BitmapShader bitmapShader = new BitmapShader(mBitmap,
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);

        float scaleX = getWidth() / (float) mBitmap.getWidth();
        float scaleY = getHeight() / (float) mBitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY);

        bitmapShader.setLocalMatrix(matrix);

        mPaint.setShader(bitmapShader);

        if (circleCornerRadius > 0f) {
            Path path = new Path();
            path.moveTo(0, circleCornerRadius); // 从左上角圆角的起始点开始
            path.arcTo(new RectF(0, 0, 2 * circleCornerRadius, 2 * circleCornerRadius), 180, 90);

            path.lineTo(getWidth() - circleCornerRadius, 0);
            path.arcTo(new RectF(getWidth() - 2 * circleCornerRadius, 0, getWidth(), 2 * circleCornerRadius), -90, 90);
            path.lineTo(getWidth(), getHeight());
            path.lineTo(0, getHeight());
            path.close();
            canvas.drawPath(path, mPaint);
        } else {
            canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        }
    }
}
