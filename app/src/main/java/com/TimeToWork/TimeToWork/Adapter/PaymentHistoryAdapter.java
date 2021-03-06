package com.TimeToWork.TimeToWork.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TimeToWork.TimeToWork.Company.CompanyJobDetailActivity;
import com.TimeToWork.TimeToWork.Database.Entity.JobPost;
import com.TimeToWork.TimeToWork.Database.Entity.Payment;
import com.TimeToWork.TimeToWork.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.PaymentHistoryViewHolder> {

    private Context mContext;
    private List<Payment> paymentList;
    private List<JobPost> jobPostList;

    public PaymentHistoryAdapter(Context mContext,
                                 List<Payment> paymentList,
                                 List<JobPost> jobPostList) {

        this.mContext = mContext;
        this.paymentList = paymentList;
        this.jobPostList = jobPostList;
    }

    @Override
    public PaymentHistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_payment_history, viewGroup, false);
        return new PaymentHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PaymentHistoryViewHolder paymentHistoryViewHolder, int i) {

        Payment payment = paymentList.get(i);
        final JobPost jobPost = jobPostList.get(i);

        paymentHistoryViewHolder.id.setText(String.format("ID: %s", payment.getId()));
        paymentHistoryViewHolder.paymentDate.setText(String.format("Made on %s",
                new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(payment.getDate())));
        paymentHistoryViewHolder.amount.setText(String.format(Locale.getDefault(), "RM %.2f", payment.getAmount()));
        paymentHistoryViewHolder.cardNumber.setText(payment.getCardNumber());

        paymentHistoryViewHolder.layoutPayment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, CompanyJobDetailActivity.class);
                intent.putExtra("JOBPOST", jobPost);
//                if (jobPost.getStatus().equals("Completed") ||
//                        jobPost.getStatus().equals("Unavailable")) {
//                    intent.putExtra("MENU", jobPost.getStatus());
//                } else {
//                    intent.putExtra("MENU", "Ads");
//                }
                intent.putExtra("MENU", "Paid");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public void clear() {

        int size = paymentList.size();
        paymentList.clear();
        notifyItemRangeRemoved(0, size);
    }

    class PaymentHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView id, paymentDate, amount, cardNumber;
        LinearLayout layoutPayment;

        PaymentHistoryViewHolder(View view) {

            super(view);
            id = (TextView) view.findViewById(R.id.tv_payment_id);
            paymentDate = (TextView) view.findViewById(R.id.tv_payment_date);
            amount = (TextView) view.findViewById(R.id.tv_amount);
            cardNumber = (TextView) view.findViewById(R.id.tv_payment_card_number);
            layoutPayment = (LinearLayout) view.findViewById(R.id.layout_payment);
        }
    }

}
