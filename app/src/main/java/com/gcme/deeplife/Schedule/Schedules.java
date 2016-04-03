package com.gcme.deeplife.Schedule;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gcme.deeplife.Activities.Send.SendActivity;
import com.gcme.deeplife.Database.DeepLife;
import com.gcme.deeplife.Models.Disciples;
import com.gcme.deeplife.Models.Schedule;
import com.gcme.deeplife.R;
import com.gcme.deeplife.SyncService.SyncService;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class Schedules extends Fragment {

	
	public static ListView lv_schedule;
	Button addSchedule;

	ArrayList<Schedule> schedules;

    static String scheduled_disciple_id_phone;
    private Calendar cal;

    //
    // Date Format
    //
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "kk:mm";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
    private static Context myContext;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.schedule_list, container,
                false);

        lv_schedule = (ListView) view.findViewById(R.id.ls_schedule);
        myContext = getActivity();
        populateList();

        addSchedule = (Button) view.findViewById(R.id.bt_add_schedule);
        addSchedule.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), AddSchedule.class);
                startActivity(intent);
            }
		});

		setHasOptionsMenu(true);
    	return 	view;
		
	}

    public static void populateList(){
        ArrayList<Schedule> schedules = com.gcme.deeplife.DeepLife.myDatabase.get_All_Schedule();
        lv_schedule.setAdapter(new MyDiscipleListAdapter(myContext,schedules));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
	public void onPause() {
		// TODO Auto-generated method stub
	//	unregisterForContextMenu(newsView);
		super.onPause();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
	//	registerForContextMenu(newsView);
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);


	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
	       AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
	        switch (item.getItemId()){
	            default:
	                return super.onContextItemSelected(item);

	        }

	}


    public static void delete_Dialog(final int id,final String phone) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        long deleted = com.gcme.deeplife.DeepLife.myDatabase.remove(DeepLife.Table_SCHEDULES,id);
                        if(deleted!=-1){
                            Toast.makeText(myContext,"Successfully Deleted",Toast.LENGTH_SHORT).show();

                            ContentValues log = new ContentValues();
                            log.put(com.gcme.deeplife.Database.DeepLife.LOGS_FIELDS[0],"Schedule");
                            log.put(com.gcme.deeplife.Database.DeepLife.LOGS_FIELDS[1], SyncService.Sync_Tasks[0]);
                            log.put(com.gcme.deeplife.Database.DeepLife.LOGS_FIELDS[2], SendActivity.DISCIPLE_ID);
                            com.gcme.deeplife.DeepLife.myDatabase.insert(com.gcme.deeplife.Database.DeepLife.Table_LOGS, log);
                            populateList();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setTitle("Remove Schedule ").setMessage("Are You sure you want to remove this schedule" )
                .setPositiveButton("Yes ", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }


    /*public void reload(){
        dbadapter.dispose();
        Intent intent = new Intent(this.getActivity(),MainMenu.class);

        startActivity(intent);
    }
*/




    public class MySpinnerAdapter extends ArrayAdapter<String> {

        ArrayList<String> object;
        public MySpinnerAdapter(Context ctx, int txtViewResourceId, ArrayList<String> objects) {
            super(ctx, txtViewResourceId, objects);
            this.object = objects;
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }
        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }
        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.countries_spinner, parent,
                     false);
            TextView main_text = (TextView) mySpinner
                    .findViewById(R.id.spinner_text);
            main_text.setText(object.get(position));

            return mySpinner;
        }
    }


    public class NameSpinnerAdapter extends ArrayAdapter<Disciples> {

        ArrayList<Disciples> object;
        public NameSpinnerAdapter(Context ctx, int txtViewResourceId, ArrayList<Disciples> objects) {
            super(ctx, txtViewResourceId, objects);
            this.object = objects;
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }
        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }
        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.countries_spinner, parent,
                    false);
            TextView main_text = (TextView) mySpinner
                    .findViewById(R.id.spinner_text);
            main_text.setText(object.get(position).getFull_Name().toString());

            return mySpinner;
        }
    }



    public static class MyDiscipleListAdapter extends BaseAdapter
    {
        Context context;
        ArrayList<Schedule> schedule;
        public MyDiscipleListAdapter(Context context,ArrayList<Schedule> schedule)
        {
            this.context = context;
            this.schedule = schedule;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return schedule.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflate = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflate.inflate(R.layout.schedule_item,null);


            TextView tv_name=(TextView)convertView.findViewById(R.id.schedule_with_name);
            TextView tv_phone=(TextView)convertView.findViewById(R.id.schedulephone);
            TextView tv_disc=(TextView)convertView.findViewById(R.id.scheduledisciption);
            TextView tv_time=(TextView)convertView.findViewById(R.id.scheduletime);
            TextView tv_title=(TextView)convertView.findViewById(R.id.schedule_title);


            //final String name = schedule.get(position).ge;
            final String Dis_id = schedule.get(position).getID();
            final String phone = schedule.get(position).getDisciple_Phone();
            final String time = schedule.get(position).getAlarm_Time();
            final String title = schedule.get(position).getTitle();
            final String discription = schedule.get(position).getDescription();
            final int id = Integer.parseInt(schedule.get(position).getID());

            Disciples disciple = com.gcme.deeplife.DeepLife.myDatabase.getDiscipleProfile(Dis_id);
            if(disciple != null){
                //set the values
                tv_name.setText(disciple.getFull_Name());
                tv_phone.setText(disciple.getPhone());
                tv_time.setText(time);
                tv_disc.setText(discription);
                tv_title.setText(title);
            }
            convertView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    delete_Dialog(id,phone);
                    return true;
                }
            });


            return convertView;
        }
    }

	
}
