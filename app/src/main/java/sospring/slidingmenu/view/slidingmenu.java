package sospring.slidingmenu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


public class slidingmenu extends HorizontalScrollView
{

    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mScreenWidth;
    private int mMenuRightPadding = 150;//dp
    private boolean once;
    private int mMenuWidth;

/**
  * 未使用自定义属性时调用
 * Created by sq on 2016/3/14.
 */
    public  slidingmenu (Context context, AttributeSet attrs)
    {
        this(context, attrs,0);

    }

    /*
     *当使用自定义属性时，调用
     *@author Spring
     */
    
    public slidingmenu(Context context,AttributeSet attrs,int defStyle)
    {
        super(context,attrs,defStyle);


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMeytics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMeytics);
        mScreenWidth = outMeytics.widthPixels;
        //把dp转化成px
        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,150,context
                .getResources().getDisplayMetrics());

    }
    
    public slidingmenu(Context context) {
        this(context,null);
    }

    /*
         * 设置子view的宽和高，设置自己的宽和高
         */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if(!once)
        {
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            mWapper.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    /*
    *通过设置偏移量，将menu隐藏
    */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        if(changed)
        {
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        int action = ev.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_UP:
                //隐藏在左边的宽度
                int scrollX = getScrollX();
                if(scrollX >= mMenuWidth / 2)
                {
                    this.smoothScrollTo(mMenuWidth, 0);
                }else
                {
                    this.smoothScrollTo(0,0);
                }
                return  true;

        }
        return super.onTouchEvent(ev);
    }
}


