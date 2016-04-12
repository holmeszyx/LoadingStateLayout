package z.hol.loadingstate.view;

import z.hol.loadingstate.AdapterViewWithLoadingState;
import z.hol.loadingstate.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * LoadingState which can set a AdapteView in XML
 * @author holmes
 *
 */
public class SimpleAdapterViewWithLoadingState extends AdapterViewWithLoadingState<AbsListView>{
    
    /** 
     * can't get dataview in {@link #initDataView(LayoutInflater, AttributeSet)} method
     *  
     */
    private boolean mIsNoDataViewInInit = false;

    public SimpleAdapterViewWithLoadingState(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public SimpleAdapterViewWithLoadingState(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public SimpleAdapterViewWithLoadingState(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected AbsListView initDataView(LayoutInflater inflater, AttributeSet attrs) {
        // TODO Auto-generated method stub
        //return super.initDataView(inflater, attrs);
        View dataview = null;
        if (getChildCount() > 0){
            // there are views found in xml,
            // use the first view for dataView;
            View firstView = getChildAt(0);
            removeAllViews();
            dataview = firstView;
        }else{
            TypedArray a = getResources().obtainAttributes(attrs, R.styleable.LoadingState);
            int dataViewRes = 0;
            try{
                dataViewRes = a.getResourceId(R.styleable.LoadingState_lsDataView, 0);
            }finally{
                a.recycle();
            }
            if (dataViewRes != 0){
                dataview = inflater.inflate(dataViewRes, null);
            }
        }
        
        if (dataview == null || !(dataview instanceof AbsListView)){
            if (isInEditMode()){
                dataview = new ListView(getContext());
            }else{
                // if set child in xml 
                // dataview == null
                // so not throw exception here
                // throw new IllegalArgumentException("Data view is " + dataview);
                mIsNoDataViewInInit = true;
            }
        }
        return (AbsListView) dataview;
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
                        if (cv instanceof AbsListView){
                            setDataView((AbsListView)cv);
                            hasFirstChild = true;
                            mIsNoDataViewInInit = false;
                        }
                    }
                }
            }
        }
        
        if (mIsNoDataViewInInit && !isInEditMode()){
            throw new IllegalArgumentException("Data view is " + getDataView());
        }
    }    
}
