package ch.renuo.hackzurich2016;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by yk on 17/09/16.
 */
public class UI {
    private static UI _ui;
    private UI(){}
    private List<Callable<Void>> refreshCallbacks = new ArrayList<>();

    public static UI ui(){
        if(_ui == null){
            _ui = new UI();
        }
        return _ui;
    }

    public void refreshUI(){
        for (Callable<Void> cb : this.refreshCallbacks) {
            try {
                cb.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registerRefreshCallback(Callable<Void> callback){
        refreshCallbacks.add(callback);
    }

}
