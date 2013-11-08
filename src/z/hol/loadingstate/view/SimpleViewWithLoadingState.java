package z.hol.loadingstate.view;

import z.hol.loadingstate.BaseLoadingStateLayout;
import z.hol.loadingstate.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

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
        TypedArray a = getResources().obtainAttributes(attrs, R.styleable.LoadingState);
        int dataViewRes = 0;
        try{
            dataViewRes = a.getResourceId(R.styleable.LoadingState_DataView, 0);
        }finally{
            a.recycle();
        }
        View dataview = null;
        if (dataViewRes != 0){
            dataview = inflater.inflate(dataViewRes, null);
        }
        return dataview;
    }
}