package com.TimeToWork.TimeToWork.Jobseeker;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.TimeToWork.TimeToWork.CustomClass.CustomProgressDialog;
import com.TimeToWork.TimeToWork.Database.Control.MaintainReport;
import com.TimeToWork.TimeToWork.Database.Entity.Report;
import com.TimeToWork.TimeToWork.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.TimeToWork.TimeToWork.MainApplication.userId;

public class IncomeReportActivity extends AppCompatActivity {

    private MaintainReport maintainReport;
    private TextView generateDate;
    private CustomProgressDialog mProgressDialog;
    private Handler handler;
    private TextView title1, title2;

//    LinearLayout mChartLayout;
//    TableLayout mTableLayout;
//    Report report;

    List<Report> reportList = new ArrayList<>();
    List<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_report);

        title1 = (TextView)findViewById(R.id.title);
        title2 = (TextView)findViewById(R.id.title2);

        generateDate = (TextView)findViewById(R.id.dates);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        generateDate.setText("Generated by : " + formatter.format(date));

        mProgressDialog = new CustomProgressDialog(this);
        handler = new Handler();

        final int month = getIntent().getExtras().getInt("month");
        final int year = getIntent().getExtras().getInt("year");

        //Show progress dialog
        mProgressDialog.setMessage("Loading …");
        mProgressDialog.show();

        new Thread(new Runnable() {

            @Override
            public void run() {

                maintainReport = new MaintainReport();
                reportList = maintainReport.getIncomeReport(userId, month, year);

                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(IncomeReportActivity.this,
                                R.layout.list_view_income_report, R.id.report, arrayList);

                        ListView listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(adapter);

                        title2.setText("Income Summary Report for " + month + "  " + year);

                        for (int i = 0; i < reportList.size(); i++) {
                            arrayList.add(i + 1 + "                   "
                                    + reportList.get(i).getCompany_name() + "          "
                                    + reportList.get(i).getJob() + "         "
                                    + reportList.get(i).getDate() + "                "
                                    + reportList.get(i).getSalary());
                            adapter.notifyDataSetChanged();
                        }
                        mProgressDialog.dismiss();
                    }
                });
            }
        }).start();
    }

}