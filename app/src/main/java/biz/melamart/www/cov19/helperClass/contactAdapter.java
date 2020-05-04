package biz.melamart.www.cov19.helperClass;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContacts;
import biz.melamart.www.cov19.utils.customCallerDilouge;

public class contactAdapter  extends RecyclerView.Adapter<contactAdapter.MyViewHolder> {

    private List<emergencyContacts> contactsList;
Activity activity;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contactTitle, contactAddress, enchargeText,contactNumber,tv_sn;
        ImageView dialer;

        public MyViewHolder(View view) {
            super(view);
            contactTitle = (TextView) view.findViewById(R.id.contactTitle);
            contactAddress = (TextView) view.findViewById(R.id.contactAddress);
            enchargeText = (TextView) view.findViewById(R.id.enchargeText);
            tv_sn = (TextView) view.findViewById(R.id.tv_sn) ;
            contactNumber = (TextView) view.findViewById(R.id.contactNumber);
            dialer = (ImageView) view.findViewById(R.id.dialer);
        }
    }


    public contactAdapter(List<emergencyContacts> contactsList,Activity activity) {
        this.contactsList = contactsList;
        this.activity = activity;
    }

    @Override
    public contactAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item_list, parent, false);

        return new contactAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(contactAdapter.MyViewHolder holder, int position) {
        emergencyContacts contacts = contactsList.get(position);
        String contactNum = "Tel 1 : "+contacts.getTelephone1()+" \nTel 2 : "+contacts.getTelephone2()+" \nTel 3 : "+contacts.getTelephone3();
        holder.contactTitle.setText(contacts.getName());
        holder.contactAddress.setText(contacts.getAddress());
        holder.contactNumber.setText(contactNum);
        holder.enchargeText.setText(contacts.getEncharge());
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
