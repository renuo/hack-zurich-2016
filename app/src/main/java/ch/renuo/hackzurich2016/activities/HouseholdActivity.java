package ch.renuo.hackzurich2016.activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

import ch.renuo.hackzurich2016.R;
import ch.renuo.hackzurich2016.UI;
import ch.renuo.hackzurich2016.mocks.HouseholdDatabaseMock;
import ch.renuo.hackzurich2016.models.Cluster;
import ch.renuo.hackzurich2016.models.ClusterAlarm;
import ch.renuo.hackzurich2016.models.ClusterAlarmImpl;
import ch.renuo.hackzurich2016.models.SystemAlarm;

public class HouseholdActivity extends ListActivity {

    public static final int EDIT_ALARM_REQUEST = 1;

    private String deviceId;
    private String householdId;

    private HouseholdDatabaseMock hdb;

    private HouseholdActivity self = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household);

        final Intent intent = getIntent();
        this.deviceId = intent.getStringExtra(getString(R.string.device_id));
        this.householdId = intent.getStringExtra(getString(R.string.household_id));

        this.hdb = this.initializeDatabase(this.householdId);

        final ClusterListAdapter adapter = new ClusterListAdapter(this, this.hdb.getHousehold().getClusters());
        this.setListAdapter(adapter);

        UI.ui().registerRefreshCallback(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        getListView().invalidateViews();
                        adapter.notifyDataSetChanged();
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
        if(HouseholdDatabaseMock.db == null){
            HouseholdDatabaseMock.db = new HouseholdDatabaseMock(householdId);
        }
        Log.e("e", "initializing db");
        return HouseholdDatabaseMock.db;
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
            final Cluster cluster = clusters.get(position);
            View rowView = inflater.inflate(R.layout.list_row, parent, false);
            ((TextView)rowView.findViewById(R.id.clusterName)).setText(cluster.getName());
            ((TextView)rowView.findViewById(R.id.clusterName)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText input = new EditText(self);
                    new AlertDialog.Builder(self).setTitle("Change Name").setView(input).setPositiveButton(
                            "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String result = input.getText().toString();
                                    if(result != null && result.length() > 0) {
                                        cluster.setName(result);
                                        hdb.saveCluster(hdb.getHousehold(), cluster);
                                    }
                                }
                            }
                    ).setNegativeButton(
                            "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }
                    ).show();
                }
            });
            ((ImageButton)rowView.findViewById(R.id.addAlarmButton)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(self, EditAlarmActivity.class);
                    intent.putExtra(getString(R.string.cluster_id), cluster.getId().toString());
                    intent.putExtra(getString(R.string.alarm_id), UUID.randomUUID().toString());
                    intent.putExtra(getString(R.string.alarm_time), "12:00");
                    intent.putExtra(getString(R.string.alarm_active), true);
                    intent.putExtra(getString(R.string.alarm_new), true);
                    startActivityForResult(intent, EDIT_ALARM_REQUEST);
                }
            });

            RecyclerView tlv = (RecyclerView)rowView.findViewById(R.id.timerListView);
            tlv.setLayoutManager(new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false));
            TimerListAdapter adapter = new TimerListAdapter(cluster, cluster.getClusterAlarms());
            tlv.setAdapter(adapter);
            return rowView;
        }

        private class TimerListAdapter extends RecyclerView.Adapter<ViewHolder>{

            private Cluster cluster;
            private List<ClusterAlarm> alarms;

            public TimerListAdapter(Cluster cluster, List<ClusterAlarm> alarms){
                this.cluster = cluster;
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
                        hdb.saveClusterAlarm(cluster, alarm);
                    }
                });
                tc.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(self, EditAlarmActivity.class);
                        intent.putExtra(getString(R.string.cluster_id), cluster.getId().toString());
                        intent.putExtra(getString(R.string.alarm_id), alarm.getId().toString());
                        intent.putExtra(getString(R.string.alarm_time), alarm.getTime());
                        intent.putExtra(getString(R.string.alarm_active), alarm.getActive());
                        intent.putExtra(getString(R.string.alarm_new), false);
                        startActivityForResult(intent, EDIT_ALARM_REQUEST);
                        return true;
                    }
                });
                tc.setText(alarm.getTime());
                if (alarm.getActive()) {
                    tc.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                } else {
                    tc.setBackgroundColor(ContextCompat.getColor(context, R.color.colorSecondary));
                }
            }

            @Override
            public int getItemCount() {
                return this.alarms.size();
            }

        }
    }

    private Cluster findClusterById(String id){
        for (Cluster cluster : this.hdb.getHousehold().getClusters()) {
            if(cluster.getId().toString().equals(id)){
                return cluster;
            }
        }
        return null;
    }

    private Pair<Cluster, ClusterAlarm> findAlarmById(String id){
        for (Cluster cluster : this.hdb.getHousehold().getClusters()) {
            for (ClusterAlarm alarm : cluster.getClusterAlarms()) {
                if(alarm.getId().toString().equals(id)){
                    return Pair.create(cluster, alarm);
                }
            }
        }
        return null;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == EDIT_ALARM_REQUEST){
            String clusterId = data.getStringExtra(getString(R.string.cluster_id));
            String alarmId = data.getStringExtra(getString(R.string.alarm_id));
            String alarmTime = data.getStringExtra(getString(R.string.alarm_time));
            boolean alarmActive = data.getBooleanExtra(getString(R.string.alarm_active), false);
            boolean alarmNew = data.getBooleanExtra(getString(R.string.alarm_new), false);

            Pair<Cluster, ClusterAlarm> pp = findAlarmById(alarmId);
            if(resultCode > 0 && pp != null){
                Cluster cluster = pp.first;
                ClusterAlarm alarm = pp.second;
                this.hdb.deleteClusterAlarm(cluster, alarm);
            }

            else if(resultCode == 0){

                if(alarmNew) {
                    Cluster cluster = findClusterById(clusterId);
                    ClusterAlarm alarm = new ClusterAlarmImpl(UUID.randomUUID(), alarmTime, alarmActive, new ArrayList<SystemAlarm>());
                    this.hdb.saveClusterAlarm(cluster, alarm);
                }

                else if(pp != null){
                    Cluster cluster = pp.first;
                    ClusterAlarm alarm = pp.second;
                    alarm.setTime(alarmTime);
                    alarm.setActive(alarmActive);
                    this.hdb.saveClusterAlarm(cluster, alarm);
                }
            }

        }
    }

    public void addMemberButtonClicked(View view){
        Intent intent = new Intent(this, BarcodeActivity.class);
        intent.putExtra(getString(R.string.household_id), this.householdId);
        startActivity(intent);
    }

}
