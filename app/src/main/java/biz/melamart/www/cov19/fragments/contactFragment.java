package biz.melamart.www.cov19.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import biz.melamart.www.cov19.R;
import biz.melamart.www.cov19.helperClass.contactAdapter;
import biz.melamart.www.cov19.interfaces.ViewUpdateListener;
import biz.melamart.www.cov19.models.emergencyContact.emergencyContacts;
import biz.melamart.www.cov19.network.emergencyContactHandler;
import biz.melamart.www.cov19.utils.COVSettings;
import biz.melamart.www.cov19.utils.DialogUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class contactFragment extends Fragment implements ViewUpdateListener {

    @BindView(R.id.contactRecycler)
    RecyclerView recyclerView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;


    contactAdapter mAdapter;
    private List<emergencyContacts> contactsList = new ArrayList<>();

    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.contact_fragment_layout, container, false);

        ButterKnife.bind(this, view);


        mAdapter = new contactAdapter(contactsList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        progressDialog = DialogUtils.showProgressDialog(getActivity(), null, getString(R.string.msg_progress_fetching_data), true, false);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mySwipeRefreshLayout.setRefreshing(true);
                        getContactList();
                    }
                }
        );

        prepareNewsData();
        getContactList();
        return view;


    }

    private void getContactList() {

        emergencyContactHandler emergencyContactHandler = new emergencyContactHandler(getActivity(), this);
        emergencyContactHandler.getFullData();

    }

    public void prepareNewsData() {
        emergencyContacts contact = new emergencyContacts("Teku Hospital","Kathmandu","014253396","","","","1");
        contactsList.add(contact);
         contact = new emergencyContacts("Nepal Police","Kathmandu","100","","","","1");
        contactsList.add(contact);
         contact = new emergencyContacts("Police Emergency Number","Kathmandu","4228435","","","","1");
        contactsList.add(contact);
        contact = new emergencyContacts("Paropakar Ambulance Service","Kathmandu","014260859","","","","1");
        contactsList.add(contact);
        contact = new emergencyContacts("Lalitpur Redcross Ambulance Service","Kathmandu","015545666","","","","1");
        contactsList.add(contact);
        contact = new emergencyContacts("Bishal Bazar Ambulance Service","Kathmandu","014244121","","","","1");
        contactsList.add(contact);
        contact = new emergencyContacts("Redcross Ambulance Service","Kathmandu","014228094","","","","1");
        contactsList.add(contact);
        contact = new emergencyContacts("Agrawal Sewa Centrer","Kathmandu","014424875","","","","1");
        contactsList.add(contact);
        contact = new emergencyContacts("Aasara Drug Rehabilitation Center","Kathmandu","014384881","","","","1");
        contactsList.add(contact);

        mAdapter.notifyDataSetChanged();

    }
    private void populateData() {
        contactsList.clear();
        if (COVSettings.getInstance().getEmergencyContactResponse() != null) {
            contactsList.addAll(COVSettings.getInstance().getEmergencyContactResponse().getResource());
        }prepareNewsData();
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void success() {
        populateData();
        mySwipeRefreshLayout.setRefreshing(false);
        progressDialog.hide();
    }

    @Override
    public void failure(String msg) {
        populateData();
        progressDialog.hide();
        mySwipeRefreshLayout.setRefreshing(false);
    }
}