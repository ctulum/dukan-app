package com.example.dukandietapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private LineChart weightChart;
    private BarChart sportChart;
    private BarChart psychoChart;

    private ArrayList<Entry> weightValues;
    private ArrayList<Entry> sportChartValues;
    private ArrayList<Entry> psychoChartValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
        initApp();
        setDrawerLayout();

        setWeightChart();
        setSportBarChart();
        setPsychoBarChart();
    }

    private void setWeightChart() {
        LineData data = getWeightData(365, 200);
        data.setHighlightEnabled(false);
        setUpChartWithWeightData(weightChart, data);
    }

    private LineData getWeightData(int count, int range) {
        weightValues = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            float val = (float) (Math.random()*range)+3;
            weightValues.add(new Entry(i, val, "SP"));
        }

        LineDataSet set = new LineDataSet(weightValues, "Kilo");

        set.setLineWidth(1f);
        set.setDrawCircleHole(true);
        set.setCircleRadius(5f);
        set.setCircleHoleRadius(3.2f);
        set.setCircleHoleColor(Color.WHITE);
        set.setColor(Color.RED);
        set.setCircleColor(Color.RED);
        set.setDrawValues(true);
        set.setDrawFilled(true);
        set.setFillAlpha(30);
        set.setFillColor(Color.RED);
        set.setValueTextSize(10);

        return new LineData(set);
    }

    private void setUpChartWithWeightData(final LineChart weightChart, LineData data) {
        weightChart.setScaleMinima((float)data.getEntryCount() / 10f, 1f);

        weightChart.getDescription().setEnabled(false);
        weightChart.setDrawGridBackground(false);

        weightChart.setTouchEnabled(true);
        weightChart.setDragEnabled(true);
        weightChart.setScaleEnabled(false);
        weightChart.setPinchZoom(false);

        weightChart.setBackgroundColor(Color.WHITE);
        weightChart.getXAxis().setLabelCount(10);
        weightChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        weightChart.getLegend().setEnabled(false);

        weightChart.getAxisLeft().setDrawGridLines(false);
        weightChart.getAxisLeft().setDrawAxisLine(false);
        weightChart.getAxisRight().setDrawGridLines(false);
        weightChart.getAxisRight().setDrawAxisLine(false);
        weightChart.getAxisLeft().setEnabled(false);
        weightChart.getAxisRight().setEnabled(false);

        weightChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                for(int i = 0; i < weightValues.size(); i++) {
                    if(weightValues.get(i).getX() == value) {
                        return weightValues.get(i).getData().toString();
                    }
                }
                return "PP";
            }
        });

        weightChart.setData(data);
        weightChart.invalidate();
    }

    private void setSportBarChart() {
        sportChart.setDrawBarShadow(false);
        sportChart.setDrawValueAboveBar(true);
        sportChart.getDescription().setEnabled(false);

        sportChart.setDrawGridBackground(false);

        setSportData(365, 3);
    }

    private void setSportData(int count, int range) {
        ArrayList<BarEntry> entries = new ArrayList<>();

        for(int i = 0; i < count; i++) {
           if(i % 2 == 1) {
               if(i%3 == 0) {
                   entries.add(new BarEntry(i, 3, ContextCompat.getDrawable(getApplicationContext(), R.drawable.workout)));
               } else entries.add(new BarEntry(i, 1));
           } else
               entries.add(new BarEntry(i, 2, ContextCompat.getDrawable(getApplicationContext(), R.drawable.walk)));
        }

        BarDataSet set = new BarDataSet(entries, "Sport Bar Data Set");
        set.setColor(Color.rgb(58, 180, 244));
        set.setDrawValues(false);
        set.setBarBorderColor(Color.rgb(58, 139, 244));
        set.setBarBorderWidth(2f);
        set.setDrawIcons(true);

        BarData data = new BarData(set);
        data.setBarWidth(1f);


        sportChart.setScaleMinima((float)data.getEntryCount() / 10f, 1f);
        sportChart.setTouchEnabled(true);
        sportChart.setDragEnabled(true);
        sportChart.setScaleEnabled(false);
        sportChart.setPinchZoom(false);
        sportChart.setBackgroundColor(Color.WHITE);
        sportChart.getLegend().setEnabled(false);
        sportChart.getAxisLeft().setEnabled(false);
        sportChart.getAxisRight().setEnabled(false);
        sportChart.getAxisLeft().setDrawGridLines(false);
        sportChart.getAxisLeft().setDrawAxisLine(false);
        sportChart.getAxisRight().setDrawGridLines(false);
        sportChart.getAxisRight().setDrawAxisLine(false);
        sportChart.getXAxis().setDrawAxisLine(false);
        sportChart.getXAxis().setDrawGridLines(false);
        sportChart.getXAxis().setEnabled(false);

        sportChart.setData(data);
        sportChart.invalidate();

        for (IDataSet set1 : sportChart.getData().getDataSets())
            set1.setDrawIcons(true);

        sportChart.invalidate();
    }

    private void setPsychoBarChart() {
        psychoChart.setDrawBarShadow(false);
        psychoChart.setDrawValueAboveBar(true);
        psychoChart.getDescription().setEnabled(false);

        psychoChart.setDrawGridBackground(false);

        setPsychoData(365, 3);
    }

    private void setPsychoData(int count, int range) {
        ArrayList<BarEntry> entries = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            if(i % 2 == 1) {
                if(i%3 == 0) {
                    entries.add(new BarEntry(i, 3, ContextCompat.getDrawable(getApplicationContext(), R.drawable.happy)));
                } else entries.add(new BarEntry(i, 1, ContextCompat.getDrawable(getApplicationContext(), R.drawable.sad)));
            } else
                entries.add(new BarEntry(i, 2, ContextCompat.getDrawable(getApplicationContext(), R.drawable.neutral)));
        }

        BarDataSet set = new BarDataSet(entries, "Psycho Status Bar Data Set");
        set.setColor(Color.rgb(251, 194, 27 ));
        set.setDrawValues(false);
        set.setBarBorderColor(Color.rgb(251, 164, 27));
        set.setBarBorderWidth(2f);
        set.setDrawIcons(true);

        BarData data = new BarData(set);
        data.setBarWidth(1f);


        psychoChart.setScaleMinima((float)data.getEntryCount() / 10f, 1f);
        psychoChart.setTouchEnabled(true);
        psychoChart.setDragEnabled(true);
        psychoChart.setScaleEnabled(false);
        psychoChart.setPinchZoom(false);
        psychoChart.setBackgroundColor(Color.WHITE);
        psychoChart.getLegend().setEnabled(false);
        psychoChart.getAxisLeft().setEnabled(false);
        psychoChart.getAxisRight().setEnabled(false);
        psychoChart.getAxisLeft().setDrawGridLines(false);
        psychoChart.getAxisLeft().setDrawAxisLine(false);
        psychoChart.getAxisRight().setDrawGridLines(false);
        psychoChart.getAxisRight().setDrawAxisLine(false);
        psychoChart.getXAxis().setDrawAxisLine(false);
        psychoChart.getXAxis().setDrawGridLines(false);
        psychoChart.getXAxis().setEnabled(false);

        psychoChart.setData(data);
        psychoChart.invalidate();

        for (IDataSet set1 : psychoChart.getData().getDataSets())
            set1.setDrawIcons(true);

        psychoChart.invalidate();
    }

    private void initActionBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initApp() {
        fab = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        weightChart = findViewById(R.id.weight_line_chart);
        sportChart = findViewById(R.id.sport_bar_chart);
        psychoChart = findViewById(R.id.psycho_status_bar_chart);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setDrawerLayout() {
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_camera:
                break;
            case R.id.nav_gallery:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
            default:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
