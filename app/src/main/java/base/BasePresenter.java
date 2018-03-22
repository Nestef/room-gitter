package base;

import android.support.annotation.NonNull;

/**
 * Created by Noah Steffes on 3/22/18.
 */

public abstract class BasePresenter<V> {
    protected V mView;

    public void setView(V view){
        mView = view;
    }
    public void unsetView(){
        mView = null;
    }
    public boolean hasView(){
        return mView != null;
    }

}
