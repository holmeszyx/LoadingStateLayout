package z.hol.loadingstate;

import z.hol.loadingstate.LoadingStateLayout.ReloadingListener;

public abstract class SingleReloadingListener implements ReloadingListener{

    @Override
    public void onErrorReloading() {
        // TODO Auto-generated method stub
        onReloading();
    }

    @Override
    public void onEmptyReloading() {
        // TODO Auto-generated method stub
        onReloading();
    }

    /**
     * The same reloading method for error and empty
     */
    public abstract void onReloading();
}
