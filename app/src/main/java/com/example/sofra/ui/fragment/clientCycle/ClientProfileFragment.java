package com.example.sofra.ui.fragment.clientCycle;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sofra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientProfileFragment extends Fragment {
    View view;
    @BindView(R.id.Client_Profile_Civ_product_Image)
    CircleImageView ClientProfileCivProductImage;
    @BindView(R.id.Client_Profile_Et_Name)
    EditText ClientProfileEtName;
    @BindView(R.id.Client_Profile_Et_Email)
    EditText ClientProfileEtEmail;
    @BindView(R.id.Client_Profile_Et_Phone)
    EditText ClientProfileEtPhone;
    @BindView(R.id.Client_Profile_Spn_City)
    Spinner ClientProfileSpnCity;
    @BindView(R.id.Client_Profile_Spn_Region)
    Spinner ClientProfileSpnRegion;
    @BindView(R.id.Client_Profile_Et_Home)
    EditText ClientProfileEtHome;
    @BindView(R.id.Client_Profile_Et_Password)
    EditText ClientProfileEtPassword;
    @BindView(R.id.Client_Profile_Et_RePassword)
    EditText ClientProfileEtRePassword;
    Unbinder unbinder;
    private static final int RESULT_LOAD_IMAGE = 1;

    public ClientProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_client_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Client_Profile_Btn_Add_Photo, R.id.Client_Profile_Btn_Register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Client_Profile_Btn_Add_Photo:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
                break;
            case R.id.Client_Profile_Btn_Register:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri photoSelected = data.getData();
            ClientProfileCivProductImage.setImageURI(photoSelected);

        }
    }
}
