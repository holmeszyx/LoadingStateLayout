package z.hol.loadingstate.view;

import z.hol.loadingstate.BaseLoadingStateLayout;
import z.hol.loadingstate.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Loading State view can set any child view in xml
 * @author holmes
 *
 */
public class SimpleViewWithLoadingState extends BaseLoadingStateLayout<View>{

    public SimpleViewWithLoadingState(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public SimpleViewWithLoadingState(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public SimpleViewWithLoadingState(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected View initDataView(LayoutInflater inflater, AttributeSet attrs) {
        // TODO Auto-generated method stub
        //return super.initDataView(inflater, attrs);
        if (getChildCount() > 0){
            // there are views found in xml,
            // use the first view for dataView;
            View firstView = getChildAt(0);
            removeAllViews();
            return firstView;
        }

        TypedArray a = getResources().obtainAttributes(attrs, R.styleable.LoadingState);
        int dataViewRes = 0;
        try{
            dataViewRes = a.getResourceId(R.styleable.LoadingState_lsDataView, 0);
        }finally{
            a.recycle();
        }
        View dataview = null;
        if (dataViewRes != 0){
            dataview = inflater.inflate(dataViewRes, this, false);
        }
        return dataview;
    }
    
    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
        int viewCount = getChildCount();
        if (viewCount > 0){
            boolean hasFirstChild = false;
            for (int i = 0; i < viewCount; i ++){
                View cv = getChildAt(i);
                if (cv == null) continue;
                //System.out.println("tag is " + cv.getTag());
                int id = cv.getId();
                if (id == R.id.ls__data||
                        id == R.id.ls__empty ||
                        id == R.id.ls__error ||
                        id == R.id.ls__loading){
                    continue;
                }else{
                    removeView(cv);
                    if (!hasFirstChild){
                        setDataView(cv);
                        hasFirstChild = true;
                    }
                }
            }
        }        
    }
}
