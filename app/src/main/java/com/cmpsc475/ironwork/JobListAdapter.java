package com.cmpsc475.ironwork;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class JobListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE = 1;
    private final Context context;
    private final List<Object> listRecyclerItem;
    private List<Job> jobs;

    public JobListAdapter(Context context, List<Object> listRecyclerItem) {
        this.context = context;
        this.listRecyclerItem = listRecyclerItem;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView jobID;
        private TextView jobTitle;
        private TextView jobDescription;
        private TextView jobLocation;
        private TextView jobPay;
        private TextView jobDate;
        private TextView jobTime;
        private TextView jobDuration;
        private TextView jobContact;
        private TextView jobContactPhone;
        private TextView jobContactEmail;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            jobID = (TextView) itemView.findViewById(R.id.job_ID);
            jobTitle = (TextView) itemView.findViewById(R.id.job_title);
            jobDescription = (TextView) itemView.findViewById(R.id.job_description);
            jobLocation = (TextView) itemView.findViewById(R.id.job_location);
            jobPay = (TextView) itemView.findViewById(R.id.job_pay);
            jobDate = (TextView) itemView.findViewById(R.id.job_date);
            jobTime = (TextView) itemView.findViewById(R.id.job_time);
            jobDuration = (TextView) itemView.findViewById(R.id.job_duration);
            jobContact = (TextView) itemView.findViewById(R.id.job_contact);
            jobContactPhone = (TextView) itemView.findViewById(R.id.job_contact_phone);
            jobContactEmail = (TextView) itemView.findViewById(R.id.job_contact_email);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case TYPE:

            default:

                View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.job_list_item, viewGroup, false);

                return new ItemViewHolder((layoutView));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int viewType = getItemViewType(i);

        switch (viewType) {
            case TYPE:
            default:
                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                Job job = (Job) listRecyclerItem.get(i);

                Log.d("JobListAdapter", "onBindViewHolder: " + job.getJobID());
                itemViewHolder.jobID.setText(String.valueOf(job.getJobID()));
                itemViewHolder.jobTitle.setText(job.getJobTitle());
                itemViewHolder.jobDescription.setText(job.getJobDescription());
                itemViewHolder.jobLocation.setText(job.getJobLocation());
                itemViewHolder.jobPay.setText(job.getJobPay());
                itemViewHolder.jobDate.setText(job.getJobDate());
                itemViewHolder.jobTime.setText(job.getJobTime());
                itemViewHolder.jobDuration.setText(job.getJobDuration());
                itemViewHolder.jobContact.setText(job.getJobContact());
                itemViewHolder.jobContactPhone.setText(job.getJobContactPhone());
                itemViewHolder.jobContactEmail.setText(job.getJobContactEmail());
        }

    }

    public void setJobs(List<Job> jobs) {
        jobs = jobs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }
}
