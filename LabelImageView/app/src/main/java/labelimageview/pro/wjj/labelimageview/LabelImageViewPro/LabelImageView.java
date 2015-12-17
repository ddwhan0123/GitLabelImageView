package labelimageview.pro.wjj.labelimageview.LabelImageViewPro;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import labelimageview.pro.wjj.labelimageview.R;

/**
 * Created by Ezreal on 2015/12/16.
 */
public class LabelImageView extends View {
    final static String LeftTop = "LeftTop";
    final static String LeftBottom = "LeftBottom";
    final static String RightTop = "RightTop";
    final static String RightBottom = "RightBottom";

    private Context context;
    private int textColor;
    private String contentStr;
    private int imageSrc;
    private Bitmap bitmap;
    private Paint paint;
    private float bitmapWidth;
    private float bitmapHeight;
    private int textSize;
    private String textLocation;

    public LabelImageView(Context context) {
        super(context);
    }

    public LabelImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    public LabelImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.labelImageView);
        try {
            //获取颜色，默认白色字体
            textColor = typedArray.getColor(R.styleable.labelImageView_text_color, getResources().getColor(R.color.Black));
            contentStr = typedArray.getString(R.styleable.labelImageView_text_content);
            imageSrc = typedArray.getResourceId(R.styleable.labelImageView_image_src, R.drawable.icon_zbx);
            textSize = typedArray.getInteger(R.styleable.labelImageView_text_size, 30);
            textLocation = typedArray.getString(R.styleable.labelImageView_text_location);
            if (textLocation == null || textLocation.length() <= 0) {
                textLocation = RightBottom;
            }
            //初始化画布
            bitmap = BitmapFactory.decodeResource(getResources(), imageSrc);
            bitmapWidth = typedArray.getDimension(R.styleable.labelImageView_image_width, bitmap.getWidth());
            bitmapHeight = typedArray.getDimension(R.styleable.labelImageView_image_height, bitmap.getHeight());
            if (bitmapWidth > getScreenWidth(context)) {
                bitmapWidth = getScreenWidth(context);
            }

            if (bitmapHeight >= getScreenHeight(context)) {
                bitmapHeight = getScreenHeight(context);
            }

            Log.d("--->LabelImageView", " init bitmapHeight " + bitmapHeight + "  bitmapWidth  " + bitmapWidth + "\n" + "--->LabelImageView init imageSrc " + imageSrc + "  R.drawable.icon_zbx= " + R.drawable.icon_zbx
                    + "\n" + "--->LabelImageView init contentStr " + contentStr + "\n" + "--->LabelImageView init textColor " + textColor
                    + "--->LabelImageView init  textSize  " + textSize + "\n" + "--->LabelImageView init  textLocation  " + textLocation);

            paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        } catch (Exception ex) {
            Log.e("Exception", "Unable to parse attributes due to: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画画操作
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        switch (textLocation) {
            case RightBottom:
                //右下
                canvas.drawText(contentStr, (int) bitmapWidth - paint.measureText(contentStr), (int) bitmapHeight - fontMetrics.bottom, paint);
                break;
            case RightTop:
                //右上
                canvas.drawText(contentStr, (int) bitmapWidth - paint.measureText(contentStr), 0 + textSize, paint);
                break;
            case LeftTop:
                //左上
                canvas.drawText(contentStr, 0, 0 + textSize, paint);
                break;
            case LeftBottom:
                //左下
                canvas.drawText(contentStr, 0, (int) bitmapHeight - fontMetrics.bottom, paint);
                break;
        }
    }

    //定制大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) bitmapWidth, (int) bitmapHeight);
    }

    /**
     * 获取屏幕宽度，单位为px
     *
     * @param context 应用程序上下文
     * @return 屏幕宽度，单位px
     */
    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * 获取屏幕高度，单位为px
     *
     * @param context 应用程序上下文
     * @return 屏幕高度，单位px
     */
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    /**
     * 获取DisplayMetrics对象
     *
     * @param context 应用程序上下文
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    //设置颜色
    public void setTextColor(int value){
        textColor=value;
    }

    //设置字体大小
    public void setTextSize(int value){
        textSize=value;
    }

    //设置文字内容
    public void setContentStr(String value){
        contentStr=value;
    }

    //设置位置
    public void setTextLocation(String value){
        textLocation=value;
    }
}
