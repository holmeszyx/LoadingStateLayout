package z.hol.loadingstate.test;

import java.util.ArrayList;
import java.util.List;

import z.hol.loadingstate.R;
import z.hol.loadingstate.LoadingStateLayout.ReloadingListener;
import z.hol.loadingstate.view.ListViewWithLoadingState;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener, ReloadingListener{
    
    private ListViewWithLoadingState mListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        mListView = (ListViewWithLoadingState) findViewById(android.R.id.list);
        mListView.getDataView().setOnItemClickListener(this);
        mListView.setReloadingListener(this);
        loading();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mLoadingTask != null && mLoadingTask.getStatus() == Status.RUNNING){
            mLoadingTask.cancel(true);
        }
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
        if (position == 3){
            mAdapter.clear();
        }else if (position == 4){
            mAdapter.clear();
            mListView.error();
        }else if (position == 5){
            mAdapter.clear();
            loading();
        }
    }

    @Override
    public void onErrorReloading() {
        // TODO Auto-generated method stub
        loading();
    }

    @Override
    public void onEmptyReloading() {
        // TODO Auto-generated method stub
        loading();
    }



    private LoadingTask mLoadingTask;
    private void loading(){
        mLoadingTask = new LoadingTask();
        mLoadingTask.execute();
    }

    private class LoadingTask extends AsyncTask<Void, Void, List<String>>{
        
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mListView.startLoading();
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            List<String> result = new ArrayList<String>();
            SystemClock.sleep(3500);
            for (int i = 0; i < 35; i ++){
                result.add("Item " + i);
            }
            return result;
        }
        
        @Override
        protected void onPostExecute(List<String> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            mListView.stopLoading();
            if (result != null){
                if (mAdapter == null){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_text, result);
                    mListView.setAdapter(adapter);
                    mAdapter = adapter;
                }else{
                    mAdapter.addAll(result);
                }
            }
        }
    }
}
