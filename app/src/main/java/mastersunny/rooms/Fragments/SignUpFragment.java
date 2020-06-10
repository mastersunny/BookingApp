package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ornach.richtext.RichEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import mastersunny.rooms.R;
import mastersunny.rooms.activities.LoginActivity;
import mastersunny.rooms.listeners.LoginListener;
import mastersunny.rooms.models.ApiResponse;
import mastersunny.rooms.models.CustomerRequestDto;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {

    private String TAG = "SignUpFragment";

    private Activity mActivity;
    private View view;
    private LoginListener loginListener;
    Unbinder unbinder;
    ApiInterface apiInterface;

    @BindView(R.id.edt_name)
    RichEditText edt_name;

    @BindView(R.id.edt_email)
    RichEditText edt_email;

    @BindView(R.id.edt_mobile_no)
    RichEditText edt_mobile_no;

    @BindView(R.id.edt_address)
    RichEditText edt_address;

    @BindView(R.id.edt_age)
    RichEditText edt_age;

    @BindView(R.id.radioSex)
    RadioGroup radioSex;

    @BindView(R.id.edt_city)
    RichEditText edt_city;

    @BindView(R.id.edt_country)
    RichEditText edt_country;

    @BindView(R.id.edt_nid)
    RichEditText edt_nid;

    @BindView(R.id.edt_date_of_birth)
    RichEditText edt_date_of_birth;

    String message = "Cannot be empty";

    private static String phoneNumber;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
        if (!(context instanceof LoginListener)) {
            throw new ClassCastException("Activity must implement LoginListener");
        }
        this.loginListener = (LoginActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.signup_fragment_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
            apiInterface = ApiClient.createService(getActivity(), ApiInterface.class);
            getIntentData();
        }
        return view;
    }

    private void getIntentData(){
        Bundle bundle = getArguments();
        if(bundle.getString("phoneNo")!=null) {
            phoneNumber = bundle.getString("phoneNo");
            phoneNumber = "+"+phoneNumber;
            edt_mobile_no.setText(phoneNumber);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_register})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                signUp();
                break;
        }
    }

    private void signUp() {
        String name = edt_name.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String address = edt_address.getText().toString().trim();
        String age = edt_age.getText().toString().trim();
        String gender = "";
        int selectedId = radioSex.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radioMale:
                gender= "Male";
                break;
            case R.id.radioFemale:
                gender= "Female";
                break;
        }
        String city = edt_city.getText().toString().trim();
        String country = edt_country.getText().toString().trim();
        String nid = edt_nid.getText().toString().trim();
        String dob = edt_date_of_birth.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            edt_name.setError(message);
        } else if (TextUtils.isEmpty(email)) {
            edt_email.setError(message);
        } else if (TextUtils.isEmpty(address)) {
            edt_address.setError(message);
        } else if (TextUtils.isEmpty(age)) {
            edt_age.setError(message);
        }  else if (TextUtils.isEmpty(gender)) {
            Toast.makeText(mActivity, "Gender is required",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(city)) {
            edt_city.setError(message);
        } else if (TextUtils.isEmpty(country)) {
            edt_country.setError(message);
        }else if (TextUtils.isEmpty(nid)) {
            edt_nid.setError(message);
        } else if (TextUtils.isEmpty(dob)) {
            edt_date_of_birth.setError(message);
        }else {
            CustomerRequestDto customerRequestDto = new CustomerRequestDto();
            customerRequestDto.setName(name);
            customerRequestDto.setEmail(email);
            customerRequestDto.setMobileNo(phoneNumber);
            customerRequestDto.setAddress(address);
            customerRequestDto.setAge(Integer.parseInt(age));
            customerRequestDto.setCity(city);
            customerRequestDto.setCountry(country);
            customerRequestDto.setNid(nid);
            customerRequestDto.setGender(gender);
            customerRequestDto.setDateOfBirth(dob);

            apiInterface.registerCustomer(customerRequestDto).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    Constants.debugLog(TAG, response+"");
                    if(response.isSuccessful()&&response.body()!=null && response.body().getCustomer()!=null){
                        Toasty.success(mActivity, "Customer registration success", Toasty.LENGTH_SHORT).show();
                        loginListener.customerRegister(response.body().getCustomer());
                    }else {
                        Toasty.error(mActivity, "Customer registration error", Toasty.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Constants.debugLog(TAG, t.getMessage());
                    Toasty.error(mActivity, "Customer registration error", Toasty.LENGTH_SHORT).show();
                }
            });
        }

    }
}
