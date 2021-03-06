package z.hol.loadingstate;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * <p>
 * A simple full loading state layout.
 * There are empty view, loading view and error view in it.
 * You can change state text or icon runtime, also can set it in XML
 * </p>
 * @author holmes
 *
 * @param <T>
 */
public class BaseLoadingStateLayout <T extends View>extends LoadingStateLayout<T>{

    
    private CharSequence mLoadingText, mEmptyText, mErrorText;
    
    private TextView mLoadingTextView, mEmptyTextView, mErrorTextView;
    private ImageView mEmptyImageView, mErrorImageView;
    
    private boolean mAutoShowEmpty = true;
    
    public BaseLoadingStateLayout(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }

    public BaseLoadingStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLoadingStateLayout(Context context) {
        super(context);
    }

    @Override
    protected void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater inflater = LayoutInflater.from(context);
        T data = initDataView(inflater, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingState, defStyle, 0);
        int loadingViewRes, emptyViewRes, errorViewRes;
        int emptyIcon, errorIcon;
        int progressCycleIcon;
        int stateBackground = 0;
        ColorStateList stateTextColor = null;
        float textSize = -1f;
        // int loadingProgressDuration = -1;
        try{
            loadingViewRes = a.getResourceId(R.styleable.LoadingState_lsLoadingView, R.layout.ls__inc_loading);
            emptyViewRes = a.getResourceId(R.styleable.LoadingState_lsEmptyView, R.layout.ls__inc_empty);
            errorViewRes = a.getResourceId(R.styleable.LoadingState_lsErrorView, R.layout.ls__inc_error);

            emptyIcon = a.getResourceId(R.styleable.LoadingState_lsEmptyIcon, 0);
            errorIcon = a.getResourceId(R.styleable.LoadingState_lsErrorIcon, 0);
            mEmptyText = a.getText(R.styleable.LoadingState_lsEmptyText);
            mErrorText = a.getText(R.styleable.LoadingState_lsErrorText);
            progressCycleIcon = a.getResourceId(R.styleable.LoadingState_lsProgress, 0);
            stateBackground = a.getResourceId(R.styleable.LoadingState_lsStateBackground, 0);
            stateTextColor = a.getColorStateList(R.styleable.LoadingState_lsStateTextColor);
            // loadingProgressDuration = a.getInt(R.styleable.LoadingState_LoadingProgressDuration, -1);
            textSize = a.getDimension(R.styleable.LoadingState_lsStateTextSize, -1f);
        }finally{
            a.recycle();
        }

        View loading = inflater.inflate(loadingViewRes, this, false);
        View empty = inflater.inflate(emptyViewRes, this, false);
        View error = inflater.inflate(errorViewRes, this, false);

        setDataView(data);
        setLoadingView(loading);
        setEmptyView(empty);
        setErrorView(error);
        
        if (progressCycleIcon != 0){
            ProgressBar progress = (ProgressBar) loading.findViewById(android.R.id.progress);
            progress.setIndeterminateDrawable(getResources().getDrawable(progressCycleIcon));
        }
        
        
        if (stateBackground != 0){
            loading.setBackgroundResource(stateBackground);
            empty.setBackgroundResource(stateBackground);
            error.setBackgroundResource(stateBackground);
        }
        
        if (stateTextColor != null){
            setTextViewTextColor(mLoadingTextView, stateTextColor);
            setTextViewTextColor(mEmptyTextView, stateTextColor);
            setTextViewTextColor(mErrorTextView, stateTextColor);
        }
        
        if (textSize != -1f){
            setTextViewTextSize(mLoadingTextView, textSize);
            setTextViewTextSize(mEmptyTextView, textSize);
            setTextViewTextSize(mErrorTextView, textSize);
        }
        
        if (emptyIcon != 0){
            mEmptyImageView.setImageResource(emptyIcon);
        }
        if (errorIcon != 0){
            mErrorImageView.setImageResource(errorIcon);
        }
        if (mEmptyText != null){
            setEmptyText(mEmptyText);
        }
        if (mErrorText != null){
            setErrorText(mErrorText);
        }
    }

    /**
     * instantiate data view
     * @param inflater
     * @param attrs
     * @return
     */
    protected T initDataView(LayoutInflater inflater, AttributeSet attrs){
        return null;
    }
    
    @Override
    public void setLoadingView(View v) {
        super.setLoadingView(v);
        mLoadingTextView = (TextView) v.findViewById(android.R.id.text1);
    }

    @Override
    public void setErrorView(View v) {
        super.setErrorView(v);
        mErrorTextView = (TextView) v.findViewById(android.R.id.text1);
        mErrorImageView = (ImageView) v.findViewById(android.R.id.icon);
    }

    @Override
    public void setEmptyView(View v) {
        super.setEmptyView(v);
        mEmptyTextView = (TextView) v.findViewById(android.R.id.text1);
        mEmptyImageView = (ImageView) v.findViewById(android.R.id.icon);
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

    private static void setTextViewTextColor(TextView v, ColorStateList color){
        if (v != null){
            v.setTextColor(color);
        }
    }

    /**
     * Set Text Size for TextView
     * @param v
     * @param size
     */
    private static void setTextViewTextSize(TextView v, float size){
        if (v != null){
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }
    
    public void setAutoShowEmpty(boolean auto){
        mAutoShowEmpty = auto;
    }
    
    public boolean isAutoShowEmpty(){
        return mAutoShowEmpty;
    }
}
