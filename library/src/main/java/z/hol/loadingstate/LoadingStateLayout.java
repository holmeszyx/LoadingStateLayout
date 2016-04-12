package z.hol.loadingstate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

/**
 * The origin layout with the loading state
 * @author holmes
 *
 * @param <T>
 */
public abstract class LoadingStateLayout <T extends View> extends RelativeLayout{

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
    private OnStateChangeListener mStateChangedListener;
    
    private OnClickListener mStateViewClickListener = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
            if (mReloadingListener != null) {
                int id = v.getId();
                if (id == R.id.ls__empty) {
                    mReloadingListener.onEmptyReloading();
                } else if (id == R.id.ls__error) {
                    mReloadingListener.onErrorReloading();
                }
            }
        }
    };
    
    public LoadingStateLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
        removeAllStateViews();
    }

    public LoadingStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, R.attr.loadingStateStyle);
        removeAllStateViews();
    }

    public LoadingStateLayout(Context context) {
        super(context);
        init(context, null, R.attr.loadingStateStyle);
        removeAllStateViews();
    }

    protected abstract void init(Context context, AttributeSet attrs, int defStyle);
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mRelayout){
            mRelayout = false;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * Set reloading listener.
     * the listener for Empty or Error click
     * @param listener
     */
    public void setReloadingListener(ReloadingListener listener){
        mReloadingListener = listener;
    }

    /**
     * Set OnStateChanged Listener
     * @param listener
     */
    public void setOnStateChangedListener(OnStateChangeListener listener){
        mStateChangedListener = listener;
    }
    
    /**
     * remove a view from its parent
     * @param v
     */
    protected static void removeOldView(View v){
        if (v != null){
            if (!(v instanceof AdapterView<?>)){
                v.setOnClickListener(null);
            }
            ViewParent parent = v.getParent();
            if (parent != null){
                ((ViewGroup) parent).removeView(v);
            }
        }
    }
    
    /**
     * set data view
     * @param dataView
     */
    public void setDataView(T dataView){
        int oldVisibility = mDataView == null ? -1 : mDataView.getVisibility();
        removeOldView(mDataView);
        mDataView = dataView;
        if (mDataView != null){
            mRelayout = true;
            mDataView.setId(R.id.ls__data);
            if (oldVisibility != -1){
                dataView.setVisibility(oldVisibility);
            }
            setDataViewLayoutParams(mDataView);
        }
    }
    
    /**
     * get data view
     * @return
     */
    public T getDataView(){
        return mDataView;
    }
    
    /**
     * set loading state view
     * @param v
     */
    public void setLoadingView(View v){
        removeOldView(mLoadingView);
        mLoadingView = v;
        if (mLoadingView != null){
            mRelayout = true;
            mLoadingView.setId(R.id.ls__loading);
            setViewLayoutParams(mLoadingView, R.id.ls__loading);
        }
    }
    
    /**
     * Get loading view
     * @return
     */
    public View getLoadingView(){
        return mLoadingView;
    }
    
    /**
     * set error state view
     * @param v
     */
    public void setErrorView(View v){
        removeOldView(mErrorView);
        mErrorView = v;
        if (mErrorView != null){
            mRelayout = true;
            mErrorView.setId(R.id.ls__error);
            setViewLayoutParams(mErrorView, R.id.ls__error);
        }
    }
    
    /**
     * get Error state view
     * @return
     */
    public View getErrorView(){
        return mErrorView;
    }
    
    /**
     * Set empty view
     * @param v
     */
    public void setEmptyView(View v){
        removeOldView(mEmptyView);
        mEmptyView = v;
        if (mEmptyView != null){
            mRelayout = true;
            mEmptyView.setId(R.id.ls__empty);
            setViewLayoutParams(mEmptyView, R.id.ls__empty);
        }
    }
    
    /**
     * Get empty view
     * @return
     */
    public View getEmptyView(){
        return mEmptyView;
    }
    
    /**
     * to set data view layout_params.
     * default: match_parent
     * @param v
     * @return
     */
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
    
    /**
     * Set state view layout_params, center in parent is default.
     * @param v
     * @param id
     * @return
     */
    protected boolean setViewLayoutParams(View v, int id){
        if (v != null){
            LayoutParams params = (LayoutParams) v.getLayoutParams();
            if (params == null){
                params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.addRule(CENTER_IN_PARENT, TRUE);
            }
            addView(v, params);
            if (id != R.id.ls__loading){
                v.setFocusable(true);
                v.setClickable(true);
                v.setOnClickListener(mStateViewClickListener);
            }
            return true;
        }
        return false;
    }

    /**
     * Remove all state views.
     * Only data view be remained.
     */
    private void removeAllStateViews(){
        removeOldView(mEmptyView);
        removeOldView(mErrorView);
        removeOldView(mLoadingView);
    }
    
    /**
     * Show loading state view 
     */
    public void startLoading(){
        setState(State.LOADING);
        removeAllStateViews();
        setViewLayoutParams(mLoadingView, R.id.ls__loading);
        invokeOnStateChanged(State.LOADING);
    }
    
    /**
     * Hide loading view, and set State to State.NORMAL
     * @see  #startLoading()
     */
    public void stopLoading(){
        setState(State.NORMAL);
        removeOldView(mLoadingView);
        invokeOnStateChanged(State.NORMAL);
    }
    
    /**
     * Show error state
     * @see  #hideError()
     * @see  #hideEmpty()
     */
    public void error(){
        setState(State.ERROR);
        removeAllStateViews();
        setViewLayoutParams(mErrorView, R.id.ls__error);
        invokeOnStateChanged(State.ERROR);
    }
    
    /**
     * Hide error state
     */
    public void hideError(){
        setState(State.NORMAL);
        removeAllStateViews();
        invokeOnStateChanged(State.NORMAL);
    }
    
    /**
     * Show empty state view.
     * @see #hideEmpty()
     */
    public void empty(){
        setState(State.EMPTY);
        removeAllStateViews();
        setViewLayoutParams(mEmptyView, R.id.ls__empty);
        mEmptyView.requestFocus();
        invokeOnStateChanged(State.EMPTY);
    }
    
    /**
     * Hide the empty state view
     */
    public void hideEmpty(){
        setState(State.NORMAL);
        removeAllStateViews();
        invokeOnStateChanged(State.NORMAL);
    }
    
    /**
     * Is Error loading state
     * @return
     */
    public boolean isError(){
        return mState == State.ERROR;
    }
    
    /**
     * Is Data set empty
     * @return
     */
    public boolean isEmpty(){
        return mState == State.EMPTY;
    }
    
    /**
     * set current loading state
     * @param state
     */
    protected void setState(State state){
        mState = state;
    }
    
    /**
     * Get current loading state
     * @return
     */
    public State getState(){
        return mState;
    }

    /**
     * try to invoke onStateChanged Listener if it is no null.
     * just for internal calls
     * @param state
     */
    protected void invokeOnStateChanged(State state){
        if (mStateChangedListener != null){
            mStateChangedListener.onStateChanged(this, state);
        }
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

    /**
     * State changed listener,
     * for state, Loading, Error, Empty
     */
    public interface OnStateChangeListener{

        /**
         * State changed
         * @param layout
         * @param state current state
         */
        void onStateChanged(LoadingStateLayout<?> layout, State state);
    }
    
}
