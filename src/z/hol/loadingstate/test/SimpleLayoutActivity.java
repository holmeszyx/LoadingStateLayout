package z.hol.loadingstate.test;

import z.hol.loadingstate.R;
import z.hol.loadingstate.view.SimpleViewWithLoadingState;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

public class SimpleLayoutActivity extends Activity{
    
    private SimpleViewWithLoadingState mLoadingState;
    
    private LoadingTask mLoadingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_simple_view);
        mLoadingState = (SimpleViewWithLoadingState) findViewById(R.id.loading);
        mLoadingTask = new LoadingTask();
        mLoadingTask.execute();
    }
    
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mLoadingTask != null && mLoadingTask.getStatus() == Status.RUNNING){
            mLoadingTask.cancel(true);
        }
    }
    
    private class LoadingTask extends AsyncTask<Void, Void, Void>{
        
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mLoadingState.startLoading();
            mLoadingState.getDataView().setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            SystemClock.sleep(2500);
            return null;
        }
        
        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            mLoadingState.stopLoading();
            mLoadingState.getDataView().setVisibility(View.VISIBLE);
        }
    }
}
