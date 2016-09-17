package ch.renuo.hackzurich2016.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Callable;

import ch.renuo.hackzurich2016.R;
import ch.renuo.hackzurich2016.UI;
import ch.renuo.hackzurich2016.mocks.HouseholdDatabaseMock;
import ch.renuo.hackzurich2016.models.Cluster;
import ch.renuo.hackzurich2016.models.ClusterAlarm;

public class HouseholdActivity extends ListActivity {

    public static final int EDIT_ALARM_REQUEST = 1;

    private String deviceId;
    private String householdId;

    private HouseholdDatabaseMock hdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household);

        final Intent intent = getIntent();
        this.deviceId = intent.getStringExtra(getString(R.string.device_id));
        this.householdId = intent.getStringExtra(getString(R.string.household_id));

        this.hdb = this.initializeDatabase(this.householdId);

        this.setListAdapter(new ClusterListAdapter(this, this.hdb.getHousehold().getClusters()));

        UI.ui().registerRefreshCallback(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getListView().invalidateViews();
                    }
                });
                return null;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private HouseholdDatabaseMock initializeDatabase(String householdId){
        return new HouseholdDatabaseMock(householdId);
    }

    private class ClusterListAdapter extends ArrayAdapter<Cluster>{
        private List<Cluster> clusters;
        private Context context;

        public ClusterListAdapter(Context context, List<Cluster> clusters) {
            super(context, -1, clusters);
            this.clusters = clusters;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_row, parent, false);
            ((TextView)rowView.findViewById(R.id.clusterName)).setText(clusters.get(position).getName());

            RecyclerView tlv = (RecyclerView)rowView.findViewById(R.id.timerListView);
            tlv.setLayoutManager(new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false));
            tlv.setAdapter(new TimerListAdapter(clusters.get(position).getClusterAlarms()));
            return rowView;
        }

        private class TimerListAdapter extends RecyclerView.Adapter<ViewHolder>{

            private List<ClusterAlarm> alarms;

            public TimerListAdapter(List<ClusterAlarm> alarms){
                this.alarms = alarms;
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timer_item, parent, false);
                return new ViewHolder(v){};
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                final TextView tc = (TextView)holder.itemView.findViewById(R.id.textClock);
                final ClusterAlarm alarm = this.alarms.get(position);
                tc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alarm.setActive(!alarm.getActive());
                        hdb.saveClusterAlarm(alarm);
                    }
                });
                tc.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(tc.getContext(), EditAlarmActivity.class);
                        intent.putExtra(getString(R.string.alarm_id), alarm.getId());
                        intent.putExtra(getString(R.string.alarm_time), alarm.getTime());
                        intent.putExtra(getString(R.string.alarm_active), alarm.getActive());
                        intent.putExtra(getString(R.string.alarm_new), false);
                        startActivityForResult(intent, EDIT_ALARM_REQUEST);
                        return true;
                    }
                });
                tc.setText(alarm.getTime());
                if(alarm.getActive()) {
                    tc.setBackgroundColor(Color.parseColor("red"));
                }else{
                    tc.setBackgroundColor(Color.parseColor("white"));
                }
            }

            @Override
            public int getItemCount() {
                return this.alarms.size();
            }

        }
    }

    private ClusterAlarm findAlarmById(String id){
        for (Cluster cluster : this.hdb.getHousehold().getClusters()) {
            for (ClusterAlarm alarm : cluster.getClusterAlarms()) {
                if(alarm.getId() == id){
                    return alarm;
                }
            }
        }
        return null;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == EDIT_ALARM_REQUEST){
            String alarmId = data.getStringExtra(getString(R.string.alarm_id));
            String alarmTime = data.getStringExtra(getString(R.string.alarm_time));
            boolean alarmActive = data.getBooleanExtra(getString(R.string.alarm_active), false);
            boolean alarmNew = data.getBooleanExtra(getString(R.string.alarm_new), false);

            ClusterAlarm alarm = findAlarmById(alarmId);
            if(resultCode > 0 && alarm != null){
                this.hdb.deleteClusterAlarm(alarm);
            }

            else if(resultCode == 0){
                if(alarmNew || alarm != null){
                    alarm.setTime(alarmTime);
                    alarm.setActive(alarmActive);
                    this.hdb.saveClusterAlarm(alarm);
                }
            }

        }
    }
}
