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

public class SimpleAdapterViewWithLoadingState extends AdapterViewWithLoadingState<AbsListView>{

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
        if (dataview == null || !(dataview instanceof AbsListView)){
            if (isInEditMode()){
                dataview = new ListView(getContext());
            }else{
                throw new IllegalArgumentException("Data view is " + dataview);
            }
        }
        return (AbsListView) dataview;
    }
}
