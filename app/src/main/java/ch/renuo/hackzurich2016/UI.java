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
    private Runnable refreshCallback;

    public static UI ui(){
        if(_ui == null){
            _ui = new UI();
        }
        return _ui;
    }

    public void refreshUI(){
        if(this.refreshCallback != null){
            this.refreshCallback.run();
        }
    }

    public void registerRefreshCallback(Runnable callback){
        this.refreshCallback = callback;
    }

}
