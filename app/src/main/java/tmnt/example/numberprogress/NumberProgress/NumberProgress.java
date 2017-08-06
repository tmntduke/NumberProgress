package tmnt.example.numberprogress.NumberProgress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import tmnt.example.androidutils.DensityUtils;
import tmnt.example.numberprogress.R;

/**
 * Created by tmnt on 2017/7/29.
 */

public class NumberProgress extends View {

    private int bgColor = Color.BLUE;
    private int textColor = Color.BLACK;
    private int progressColor = Color.parseColor("#009dff");
    private float textSize;
    private Paint outPaint;
    private Paint insidePaint;
    private Paint textPaint;
    private int width;
    private int height;
    private int strok_witch;
    private int max;
    private int progress = 0;
    private int current;
    private final static int MAX_PROGRESS = 100;
    private static final String TAG = "NumberProgress";

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgess(int progress) {
        Log.i(TAG, "setProgess: width" + width);
        Log.i(TAG, "setProgess: max" + max);

        this.progress = (width * progress / max);
        this.current = progress;
        if (max != 0 && this.progress == width) {
            this.progress = this.progress - height;
        }
        Log.i(TAG, "setProgess: " + this.progress);
        invalidate();
    }

    public NumberProgress(Context context) {
        super(context);
        init(context);
    }

    public NumberProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttribute(context, attrs);
        init(context);
    }

    public NumberProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context, attrs);
        init(context);
    }

    private void init(Context context) {
        outPaint = new Paint();
        outPaint.setAntiAlias(true);
        outPaint.setStrokeCap(Paint.Cap.ROUND);
        outPaint.setColor(bgColor);
        outPaint.setStyle(Paint.Style.FILL);

        insidePaint = new Paint();
        insidePaint.setAntiAlias(true);
        insidePaint.setStrokeCap(Paint.Cap.ROUND);
        insidePaint.setStyle(Paint.Style.FILL);
        insidePaint.setColor(progressColor);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);

    }

    private void initAttribute(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.NumberProgress);
        progressColor = typedArray.getColor(R.styleable.NumberProgress_progressColor, Color.parseColor("#009dff"));
        textColor = typedArray.getColor(R.styleable.NumberProgress_textColor, Color.BLACK);
        textSize = typedArray.getDimension(R.styleable.NumberProgress_textSize, DensityUtils.sp2px(context, 12));
        bgColor = typedArray.getColor(R.styleable.NumberProgress_backgroundColor, Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            result = 15;
            result = Math.min(size, 15);
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            result = 750;
            result = Math.min(size, 750);
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        strok_witch = h - 15;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        width = width - paddingLeft - paddingRight;
        height = height - paddingBottom - paddingTop;
        Log.i(TAG, "onDraw: " + current + " " + max);
        int show = current * 100 / max;
        Log.i(TAG, "onDraw: show" + show);
        insidePaint.setStrokeWidth(strok_witch);
        canvas.drawCircle(height / 2, height / 2, height / 2, outPaint);
        canvas.drawRect(height / 2, 0, width - height / 2
                , height, outPaint);
        canvas.drawCircle(width - height / 2, height / 2
                , height / 2, outPaint);

        canvas.drawLine(height / 2, height / 2, height / 2 + progress
                , height / 2, insidePaint);

        canvas.drawText(String.valueOf(show), width / 2, height / 2 + 10, textPaint);
    }

}
