package biz.melamart.www.cov19.helperClass;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContacts;
import biz.melamart.www.cov19.utils.customCallerDilouge;

public class contactTraceAdapter  extends RecyclerView.Adapter<contactTraceAdapter.MyViewHolder> {

    private List<emergencyContacts> contactsList;
    Activity activity;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contactTitle, contactAddress,contactNumber,tv_sn;
        ImageView dialer;

        public MyViewHolder(View view) {
            super(view);
            contactTitle = (TextView) view.findViewById(R.id.contactTitle);
            contactAddress = (TextView) view.findViewById(R.id.contactAddress);
            tv_sn = (TextView) view.findViewById(R.id.tv_sn) ;
            contactNumber = (TextView) view.findViewById(R.id.contactNumber);
            dialer = (ImageView) view.findViewById(R.id.dialer);
        }
    }


    public contactTraceAdapter(List<emergencyContacts> contactsList,Activity activity) {
        this.contactsList = contactsList;
        this.activity = activity;
    }

    @Override
    public contactTraceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_trace_item_list, parent, false);

        return new contactTraceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(contactTraceAdapter.MyViewHolder holder, int position) {
        emergencyContacts contacts = contactsList.get(position);
        String contactNum = "Tel 1 : "+contacts.getTelephone1();
        holder.contactTitle.setText(contacts.getName());
        holder.contactAddress.setText(contacts.getAddress());
        holder.contactNumber.setText(contactNum);

        int curpos = position+1;
        holder.tv_sn.setText(curpos + "");
        holder.dialer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customCallerDilouge customCallerDilouge = new customCallerDilouge(activity,contacts);
                customCallerDilouge.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}
