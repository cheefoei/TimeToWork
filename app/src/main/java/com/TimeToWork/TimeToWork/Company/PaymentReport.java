package com.TimeToWork.TimeToWork.Company;

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
import java.util.Locale;

import static com.TimeToWork.TimeToWork.MainApplication.userId;

public class PaymentReport extends AppCompatActivity {

    private MaintainReport maintainReport;
    private TextView generateDate;
    private CustomProgressDialog mProgressDialog;
    private Handler handler;
    private TextView title1, title2;
    private String months;

//    LinearLayout mChartLayout;
//    TableLayout mTableLayout;
//    Report report;

    List<Report> reportList = new ArrayList<>();
    List<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_report);

        title1 = (TextView)findViewById(R.id.title);
        title2 = (TextView)findViewById(R.id.title2);

        generateDate = (TextView)findViewById(R.id.dates);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = new Date();
        generateDate.setText(String.format("Generated by : %s", formatter.format(date)));

        mProgressDialog = new CustomProgressDialog(this);
        handler = new Handler();

        final int month = getIntent().getExtras().getInt("month");
        final int year = getIntent().getExtras().getInt("year");

        if(month == 1)
        {
            months = "JANUARY";
        }
        else if(month == 2)
        {
            months = "FEBRUARY";
        }
        else if(month == 3)
        {
            months = "MARCH";
        }
        else if(month == 4)
        {
            months = "APRIL";
        }
        else if(month == 5)
        {
            months = "MAY";
        }
        else if(month == 6)
        {
            months = "JUNE";
        }
        else if(month == 7)
        {
            months = "JULY";
        }
        else if(month == 8)
        {
            months = "AUGUST";
        }
        else if(month == 9)
        {
            months = "SEPTEMBER";
        }
        else if(month == 10)
        {
            months = "OCTOBER";
        }
        else if(month == 11)
        {
            months = "NOVEMBER";
        }
        else if(month == 12)
        {
            months = "DECEMBER";
        }

        //Show progress dialog
        mProgressDialog.setMessage("Loading …");
        mProgressDialog.show();

        new Thread(new Runnable() {

            @Override
            public void run() {

                maintainReport = new MaintainReport();
                reportList = maintainReport.getWorkerReport(userId, month, year);

                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        PaymentListAdapter adapter = new PaymentListAdapter(PaymentReport.this, R.layout.list_view_payment_report, (ArrayList<Report>) reportList);

                        ListView listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(adapter);

                        title1.setText(reportList.get(0).getCompany_name());

                        title2.setText("Payment Summary Report for " + months + "  " + year);

                        mProgressDialog.dismiss();
                    }
                });
            }
        }).start();
    }

}
