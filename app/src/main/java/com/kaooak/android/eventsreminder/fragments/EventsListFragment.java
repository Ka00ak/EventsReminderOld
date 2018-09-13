package com.kaooak.android.eventsreminder.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kaooak.android.eventsreminder.R;
import com.kaooak.android.eventsreminder.activities.EventActivity;
import com.kaooak.android.eventsreminder.data.Categories;
import com.kaooak.android.eventsreminder.data.Event;
import com.kaooak.android.eventsreminder.database.EventsDbSingleton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 1 on 23.10.2017.
 */

public class EventsListFragment extends Fragment {

//    private static final String TAG = "EventsListFragment";

    private static final String ARG_MODE = "mode";

    private static final int REQUEST_BDAY = 0;
    private static final int REQUEST_SETTINGS = 1;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mButtonNewBday;

    private static final int MODE_ALL = 0;
    private static final int MODE_BDAYS = 1;
    private static final int MODE_WEDDINGS = 2;
    private int mode;

    public static EventsListFragment getInstance(int mode)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_MODE, mode);

        EventsListFragment fragment = new EventsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate()");

        setHasOptionsMenu(true);

        Bundle bundle = getArguments();
        switch (bundle.getInt(ARG_MODE))
        {
            case 0:
                mode = MODE_ALL;
                break;
            case 1:
                mode = MODE_BDAYS;
                break;
            case 2:
                mode = MODE_WEDDINGS;
                break;
            default:
                mode = MODE_ALL;
        }

//        NotificationService.setServiceAlarm(getActivity(), true);

//        boolean alarmOn = BdaysPreferences.getIsAlarmOn(getActivity());
//        if (!alarmOn) {
//            NotificationService.setServiceAlarm(getActivity(), true);
//            BdaysPreferences.setAlarmOn(getActivity(), true);
//        }
    }
    @Override
    public void onStart() {
        super.onStart();
//        Log.d(TAG, "onStart()");
    }
    @Override
    public void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.d(TAG, "onCreateView()");

        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerViewBdays);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        EventAdapter adapter;
        switch (mode)
        {
            case MODE_ALL:
                adapter = new EventAdapter(EventsDbSingleton.get(getActivity()).selectEvents());
                break;
            case MODE_BDAYS:
                adapter = new EventAdapter(EventsDbSingleton.get(getActivity()).selectEvents(Categories.CATEGORY_BDAY));
                break;
            case MODE_WEDDINGS:
                adapter = new EventAdapter(EventsDbSingleton.get(getActivity()).selectEvents(Categories.CATEGORY_WEDDING));
                break;
            default:
                adapter = new EventAdapter(EventsDbSingleton.get(getActivity()).selectEvents());
                break;
        }

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mButtonNewBday = view.findViewById(R.id.buttonNewBday);
        mButtonNewBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                Event event = new Event(Categories.CATEGORY_BDAY, day, month, year);
                EventsDbSingleton.get(getActivity()).insertBday(event);
                Intent intentBday = EventActivity.getIntent(getActivity(), event.getID().toString());
                startActivityForResult(intentBday, REQUEST_BDAY);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_bdays_list, menu);

        MenuItem searchItem = menu.findItem(R.id.searchBday);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                EventAdapter adapter = (EventAdapter) mRecyclerView.getAdapter();

                switch (mode)
                {
                    case MODE_ALL:
                        if (newText.equals(""))
                            adapter.setEvents(EventsDbSingleton.get(getActivity()).selectEvents());
                        else
                            adapter.setEvents(EventsDbSingleton.get(getActivity()).selectEvents(newText));
                        break;
                    case MODE_BDAYS:
                        if (newText.equals(""))
                            adapter.setEvents(EventsDbSingleton.get(getActivity()).selectEvents(Categories.CATEGORY_BDAY));
                        else
                            adapter.setEvents(EventsDbSingleton.get(getActivity()).selectEvents(Categories.CATEGORY_BDAY, newText));
                        break;
                    case MODE_WEDDINGS:
                        if (newText.equals(""))
                            adapter.setEvents(EventsDbSingleton.get(getActivity()).selectEvents(Categories.CATEGORY_WEDDING));
                        else
                            adapter.setEvents(EventsDbSingleton.get(getActivity()).selectEvents(Categories.CATEGORY_WEDDING, newText));
                        break;
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.settings:
//                Intent intentSettings = PreferencesActivity.getIntent(getActivity());
//                startActivityForResult(intentSettings, REQUEST_SETTINGS);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        class BdayViewHolder extends RecyclerView.ViewHolder implements MenuItem.OnMenuItemClickListener {

            private Event mEvent;
            private TextView mTextViewName;

            private TextView mTextViewDate;

            public BdayViewHolder(View itemView) {
                super(itemView);

                mTextViewName = (TextView) itemView.findViewById(R.id.textViewName);
                mTextViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = EventActivity.getIntent(getActivity(), mEvent.getID().toString());
                        startActivityForResult(intent, REQUEST_BDAY);
                    }
                });
                itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        MenuInflater menuInflater = getActivity().getMenuInflater();
                        menuInflater.inflate(R.menu.context_menu_bdays_list, menu);
                        MenuItem item = menu.findItem(R.id.deleteBday);
                        item.setOnMenuItemClickListener(BdayViewHolder.this);
                    }
                });
            }

            public void bind(Event event) {

                mEvent = event;

                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                Calendar calendar1 = Calendar.getInstance();
                int year = currentYear - mEvent.getYear();

                Calendar calendar2 = Calendar.getInstance();
                calendar2.set(currentYear, mEvent.getMonth(), mEvent.getDay());

                if (calendar1.after(calendar2))
                    year++;

                mTextViewName.setText(mEvent.getBdayName());
                mTextViewDate.setText(android.text.format.DateFormat.getDateFormat(getActivity()).format(mEvent.getDate()) +
                        " (Исполнится " + year + " лет)");

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                int day = mEvent.getDay();
                int month = mEvent.getMonth();

                if (day == calendar.get(Calendar.DAY_OF_MONTH) && month == calendar.get(Calendar.MONTH))
                    itemView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                else
                    itemView.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
            }
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.deleteBday:
                        int index = mRecyclerView.getChildLayoutPosition(itemView);
                        Event event = ((EventAdapter) mRecyclerView.getAdapter()).getEvents().get(index);
                        EventsDbSingleton.get(getActivity()).deleteBday(event);
                        ((EventAdapter) mRecyclerView.getAdapter()).setEvents(EventsDbSingleton.get(getActivity()).selectEvents());
                        EventsDbSingleton.get(getActivity()).deleteBday(event);

                        updateRecyclerView();
                        return true;
                    default:
                        return false;
                }
            }
        }
        class WeddingViewHolder extends RecyclerView.ViewHolder implements MenuItem.OnMenuItemClickListener {

            private Event mEvent;
            private TextView mTextViewName;

            private TextView mTextViewDate;

            public WeddingViewHolder(View itemView) {
                super(itemView);

                mTextViewName = (TextView) itemView.findViewById(R.id.textViewName);
                mTextViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = EventActivity.getIntent(getActivity(), mEvent.getID().toString());
                        startActivityForResult(intent, REQUEST_BDAY);
                    }
                });
                itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        MenuInflater menuInflater = getActivity().getMenuInflater();
                        menuInflater.inflate(R.menu.context_menu_bdays_list, menu);
                        MenuItem item = menu.findItem(R.id.deleteBday);
                        item.setOnMenuItemClickListener(WeddingViewHolder.this);
                    }
                });
            }

            public void bind(Event event) {

                mEvent = event;

                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                Calendar calendar1 = Calendar.getInstance();
                int year = currentYear - mEvent.getYear();

                Calendar calendar2 = Calendar.getInstance();
                calendar2.set(currentYear, mEvent.getMonth(), mEvent.getDay());

                if (calendar1.after(calendar2))
                    year++;

                mEvent = event;
                mTextViewName.setText(mEvent.getWeddingNameOne() + " " + mEvent.getWeddingNameTwo());
                mTextViewDate.setText(android.text.format.DateFormat.getDateFormat(getActivity()).format(mEvent.getDate()) +
                        " (Годовщина: " + year + " лет)");

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                int day = mEvent.getDay();
                int month = mEvent.getMonth();

                if (day == calendar.get(Calendar.DAY_OF_MONTH) && month == calendar.get(Calendar.MONTH))
                    itemView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                else
                    itemView.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
            }
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.deleteBday:
                        int index = mRecyclerView.getChildLayoutPosition(itemView);
                        Event event = ((EventAdapter) mRecyclerView.getAdapter()).getEvents().get(index);
                        EventsDbSingleton.get(getActivity()).deleteBday(event);
                        ((EventAdapter) mRecyclerView.getAdapter()).setEvents(EventsDbSingleton.get(getActivity()).selectEvents());
                        EventsDbSingleton.get(getActivity()).deleteBday(event);

                        updateRecyclerView();
                        return true;
                    default:
                        return false;
                }
            }
        }

        List<Event> mEvents;

        public void setEvents(List<Event> events) {
            mEvents = events;
        }
        public List<Event> getEvents() {
            return mEvents;
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }

        public EventAdapter(List<Event> events) {
            mEvents = events;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            switch (viewType) {
                case Categories.CATEGORY_BDAY:
                    View bdayView = inflater.inflate(R.layout.item_bday, parent, false);
                    return new BdayViewHolder(bdayView);
                case Categories.CATEGORY_WEDDING:
                    View weddingView = inflater.inflate(R.layout.item_wedding, parent, false);
                    return new WeddingViewHolder(weddingView);
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Event event = mEvents.get(position);

            switch (holder.getItemViewType()) {
                case Categories.CATEGORY_BDAY:
                    BdayViewHolder bdayViewHolder = (BdayViewHolder) holder;
                    bdayViewHolder.bind(event);
                    break;
                case Categories.CATEGORY_WEDDING:
                    WeddingViewHolder weddingViewHolder = (WeddingViewHolder) holder;
                    weddingViewHolder.bind(event);
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            switch (mEvents.get(position).getCategoryID()) {
                case Categories.CATEGORY_BDAY:
                    return Categories.CATEGORY_BDAY;
                case Categories.CATEGORY_WEDDING:
                    return Categories.CATEGORY_WEDDING;
                default:
                    return Categories.CATEGORY_BDAY;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        updateRecyclerView();

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_BDAY:
//                    updateRecyclerView();
                    break;
                case REQUEST_SETTINGS:
                    Toast.makeText(getActivity(), "settings result", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    }

    private void updateRecyclerView() {
        EventAdapter adapter = (EventAdapter) mRecyclerView.getAdapter();
        adapter.setEvents(EventsDbSingleton.get(getActivity()).selectEvents());
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onPause() {
        super.onPause();
//        Log.d(TAG, "onPause()");
    }
    @Override
    public void onStop() {
        super.onStop();
//        Log.d(TAG, "onStop()");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.d(TAG, "onDestroy()");
    }
}
