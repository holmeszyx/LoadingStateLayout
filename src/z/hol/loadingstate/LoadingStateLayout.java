package z.hol.loadingstate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

public abstract class LoadingStateLayout <T extends View> extends RelativeLayout{
    public static final int ID_DATA = 0x7f1989;
    public static final int ID_EMPTY = 0x7f1990;
    public static final int ID_ERROR = 0x7f1991;
    public static final int ID_LOADING = 0x7f1992;
    
    /**
     * State
     * @author holmes
     *
     */
    public static enum State{
        NORMAL, LOADING, EMPTY, ERROR
    }
    
    protected T mDataView;
    
    protected View mLoadingView;
    protected View mEmptyView;
    protected View mErrorView;

    private State mState = State.NORMAL;
    private boolean mRelayout = true;

    private ReloadingListener mReloadingListener;
    
    private OnClickListener mStateViewClickListener = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (mReloadingListener != null) {
                int id = v.getId();
                System.out.println(id);
                if (id == ID_EMPTY) {
                    mReloadingListener.onEmptyReloading();
                } else if (id == ID_ERROR) {
                    mReloadingListener.onErrorReloading();
                }
            }
        }
    };
    
    public LoadingStateLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init(context, attrs);
    }

    public LoadingStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context, attrs);
    }

    public LoadingStateLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context, null);
    }

    protected abstract void init(Context context, AttributeSet attrs);
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mRelayout){
            mRelayout = false;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
    
    public void setReloadingListener(ReloadingListener listener){
        mReloadingListener = listener;
    }
    
    protected static void removeOldView(View v){
        if (v != null){
            v.setOnClickListener(null);
            ViewParent parent = v.getParent();
            if (parent != null){
                ((ViewGroup) parent).removeView(v);
            }
        }
    }
    
    public void setDataView(T dataView){
        removeOldView(mDataView);
        mDataView = dataView;
        if (mDataView != null){
            mRelayout = true;
            mDataView.setId(ID_DATA);
            setDataViewLayoutParams(mDataView);
        }
    }
    
    public T getDataView(){
        return mDataView;
    }
    
    public void setLoadingView(View v){
        removeOldView(mLoadingView);
        mLoadingView = v;
        if (mLoadingView != null){
            mRelayout = true;
            mLoadingView.setId(ID_LOADING);
            setViewLayoutParams(mLoadingView, ID_LOADING);
        }
    }
    
    public View getLoadingView(){
        return mLoadingView;
    }
    
    public void setErrorView(View v){
        removeOldView(mErrorView);
        mErrorView = v;
        if (mErrorView != null){
            mRelayout = true;
            mErrorView.setId(ID_ERROR);
            mErrorView.setOnClickListener(mStateViewClickListener);
            setViewLayoutParams(mErrorView, ID_ERROR);
        }
    }
    
    public View getErrorView(){
        return mErrorView;
    }
    
    public void setEmptyView(View v){
        removeOldView(mEmptyView);
        mEmptyView = v;
        if (mEmptyView != null){
            mRelayout = true;
            mEmptyView.setId(ID_EMPTY);
            mEmptyView.setOnClickListener(mStateViewClickListener);
            setViewLayoutParams(mEmptyView, ID_EMPTY);
        }
    }
    
    public View getEmptyView(){
        return mEmptyView;
    }
    
    protected boolean setDataViewLayoutParams(T v){
        if (v != null){
            LayoutParams params = (LayoutParams) v.getLayoutParams();
            if (params == null){
                params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            }
            addView(v, params);
            return true;
        }
        return false;
    }
    
    protected boolean setViewLayoutParams(View v, int id){
        if (v != null){
            LayoutParams params = (LayoutParams) v.getLayoutParams();
            if (params == null){
                params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.addRule(CENTER_IN_PARENT, TRUE);
            }
            addView(v, params);
            return true;
        }
        return false;
    }
    
    private void removeAllStateViews(){
        removeOldView(mEmptyView);
        removeOldView(mErrorView);
        removeOldView(mLoadingView);
    }
    
    public void startLoading(){
        setState(State.LOADING);
        removeAllStateViews();
        setViewLayoutParams(mLoadingView, ID_LOADING);
    }
    
    public void stopLoading(){
        setState(State.NORMAL);
        removeOldView(mLoadingView);
    }
    
    public void error(){
        setState(State.ERROR);
        removeAllStateViews();
        setViewLayoutParams(mErrorView, ID_ERROR);
    }
    
    public void empty(){
        setState(State.EMPTY);
        removeAllStateViews();
        setViewLayoutParams(mEmptyView, ID_EMPTY);
    }
    
    public void hideEmpty(){
        setState(State.NORMAL);
        removeAllStateViews();
    }
    
    public boolean isError(){
        return mState == State.ERROR;
    }
    
    public boolean isEmpty(){
        return mState == State.EMPTY;
    }
    
    protected void setState(State state){
        mState = state;
    }
    
    public State getState(){
        return mState;
    }
    
    /**
     * The call back for empty view or error view clicked
     * @author holmes
     *
     */
    public interface ReloadingListener{
        
        /**
         * reloading on error
         */
        public void onErrorReloading();
        
        /**
         * reloading on empty
         */
        public void onEmptyReloading();
    }
    
}
