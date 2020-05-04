package biz.melamart.www.cov19.helperClass;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContacts;
import biz.melamart.www.cov19.models.faq;
import biz.melamart.www.cov19.utils.customCallerDilouge;

public class faqAdapter extends RecyclerView.Adapter<faqAdapter.MyViewHolder> {

private List<faq> faqList;
        Activity activity;
public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView faqQuestion, faqAnswer, tv_sn;

    public MyViewHolder(View view) {
        super(view);
        faqQuestion = (TextView) view.findViewById(R.id.faqQuestion);
        faqAnswer = (TextView) view.findViewById(R.id.faqAnswer);
        tv_sn = (TextView) view.findViewById(R.id.tv_sn) ;
    }
}


    public faqAdapter(List<faq> faqList, Activity activity) {
        this.faqList = faqList;
        this.activity = activity;
    }

    @Override
    public faqAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_list_item, parent, false);

        return new faqAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(faqAdapter.MyViewHolder holder, int position) {
        faq faqs = faqList.get(position);
        int curpos = position+1;
        holder.tv_sn.setText(curpos + "");

        holder.faqQuestion.setText(faqs.getFaqQuestion());
        holder.faqAnswer.setText(faqs.getFaqAnswer());

    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }
}
