package com.example.sofra.ui.fragment.navigationViewCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.contactUs.ContactUs;
import com.example.sofra.data.rest.ApiServices;
import com.example.sofra.data.rest.RetrofitGeneral;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactAsFragment extends Fragment {
    View view;
    @BindView(R.id.Contact_Us_Et_Name)
    EditText ContactUsEtName;
    @BindView(R.id.Contact_Us_Et_Email)
    EditText ContactUsEtEmail;
    @BindView(R.id.Contact_Us_Et_Phone)
    EditText ContactUsEtPhone;
    @BindView(R.id.Contact_Us_Et_Message)
    EditText ContactUsEtMessage;
    @BindView(R.id.Contact_Us_Rb_Inquiry)
    RadioButton ContactUsRbInquiry;
    @BindView(R.id.Contact_Us_Rb_Suggestion)
    RadioButton ContactUsRbSuggestion;
    @BindView(R.id.Contact_Us_Rb_Complaint)
    RadioButton ContactUsRbComplaint;
    @BindView(R.id.Contact_Us_Rg_Type)
    RadioGroup ContactUsRgType;
    Unbinder unbinder;
    private RadioButton radioButton;
    private ApiServices apiServices;
    private String messageType=null;

    public ContactAsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_as, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Contact_Us_Btn_Register, R.id.Contact_Us_Civ_Facebook_Image, R.id.Contact_Us_Civ_Twitter_Image, R.id.Contact_Us_Civ_Instagram_Image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Contact_Us_Btn_Register:
                addListenerOnButton();
                break;
            case R.id.Contact_Us_Civ_Facebook_Image:
                break;
            case R.id.Contact_Us_Civ_Twitter_Image:
                break;
            case R.id.Contact_Us_Civ_Instagram_Image:
                break;
        }
    }

    private void addListenerOnButton() {
        if (ContactUsRgType.getCheckedRadioButtonId() == -1)
        {
            // no radio buttons are checked
            Toast.makeText(getActivity(), "تاكيد اختيار نوع الرساله", Toast.LENGTH_SHORT).show();
        }
        else {
            // one of the radio buttons is checked

            int selectedId = ContactUsRgType.getCheckedRadioButtonId();
            radioButton = (RadioButton) view.findViewById(selectedId);
            if (radioButton.getText().equals("شكوى")){messageType = "complaint";}
            if (radioButton.getText().equals("اقتراح")){messageType = "suggestion";}
            if (radioButton.getText().equals("استعلام")){messageType = "inquiry";}
            sendMessageRequest();
        }
    }

    private void sendMessageRequest() {
        String name = ContactUsEtName.getText().toString();
        String email = ContactUsEtEmail.getText().toString();
        String phone = ContactUsEtPhone.getText().toString();
        String content = ContactUsEtMessage.getText().toString();
        apiServices = RetrofitGeneral.getGeneral().create(ApiServices.class);
        apiServices.addContact(name,email,phone,messageType,content).enqueue(new Callback<ContactUs>() {
            @Override
            public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), "تم ارسال الرسالة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContactUs> call, Throwable t) {

            }
        });

    }
}
