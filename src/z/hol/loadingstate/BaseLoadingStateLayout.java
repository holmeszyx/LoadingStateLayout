package z.hol.loadingstate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class BaseLoadingStateLayout <T extends View>extends LoadingStateLayout<T>{

    
    private CharSequence mLoadingText, mEmptyText, mErrorText;
    
    private TextView mLoadingTextView, mEmptyTextView, mErrorTextView;
    
    private boolean mAutoShowEmpty = true;
    
    public BaseLoadingStateLayout(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public BaseLoadingStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public BaseLoadingStateLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        T data = initDataView(inflater, attrs);
        View loading = inflater.inflate(R.layout.inc_loading, null);
        View empty = inflater.inflate(R.layout.inc_empty, null);
        View error = inflater.inflate(R.layout.inc_error, null);
        
        setDataView(data);
        setLoadingView(loading);
        setEmptyView(empty);
        setErrorView(error);
    }
    
    protected T initDataView(LayoutInflater inflater, AttributeSet attrs){
        return null;
    }
    
    @Override
    public void setLoadingView(View v) {
        // TODO Auto-generated method stub
        super.setLoadingView(v);
        mLoadingTextView = (TextView) v.findViewById(android.R.id.text1);
    }

    @Override
    public void setErrorView(View v) {
        // TODO Auto-generated method stub
        super.setErrorView(v);
        mErrorTextView = (TextView) v.findViewById(android.R.id.text1);
    }

    @Override
    public void setEmptyView(View v) {
        // TODO Auto-generated method stub
        super.setEmptyView(v);
        mEmptyTextView = (TextView) v.findViewById(android.R.id.text1);
    }

    public void setLoadingText(CharSequence text){
        mLoadingText = text;
        setTextViewText(mLoadingTextView, mLoadingText);
    }

    public void setEmptyText(CharSequence text){
        mEmptyText = text;
        setTextViewText(mEmptyTextView, mEmptyText);
    }

    public void setErrorText(CharSequence text){
        mErrorText = text;
        setTextViewText(mErrorTextView, mErrorText);
    }
    
    private static void setTextViewText(TextView v, CharSequence text){
        if (v != null){
            v.setText(text);
        }
    }
    
    public void setAutoShowEmpty(boolean auto){
        mAutoShowEmpty = auto;
    }
    
    public boolean isAutoShowEmpty(){
        return mAutoShowEmpty;
    }
}
