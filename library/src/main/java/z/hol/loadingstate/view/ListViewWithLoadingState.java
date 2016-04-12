package z.hol.loadingstate.view;

import z.hol.loadingstate.AdapterViewWithLoadingState;
import z.hol.loadingstate.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

/**
 * Loading state layout which has a ListView
 * @author holmes
 *
 */
public class ListViewWithLoadingState extends AdapterViewWithLoadingState<ListView>{

    public ListViewWithLoadingState(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public ListViewWithLoadingState(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ListViewWithLoadingState(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected ListView initDataView(LayoutInflater inflater, AttributeSet attrs) {
        // TODO Auto-generated method stub
        //return super.initDataView(inflater, attrs);
        View listParent = inflater.inflate(R.layout.ls__inc_listview, null);
        ListView listView = (ListView) listParent.findViewById(android.R.id.list);
        return listView;
    }
}
