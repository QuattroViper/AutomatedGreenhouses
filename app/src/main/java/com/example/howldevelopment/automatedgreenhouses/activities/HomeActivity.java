package com.example.howldevelopment.automatedgreenhouses.activities;

import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.howldevelopment.automatedgreenhouses.R;
import com.example.howldevelopment.automatedgreenhouses.interfaces.Raspberry;
import com.example.howldevelopment.automatedgreenhouses.models.Status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.howldevelopment.automatedgreenhouses.functions.RetrofitClass.createRetrofit;

public class HomeActivity extends AppCompatActivity {

    private TextView tvStatus;
    private TextView tvDiseaseOne;
    private TextView tvDiseaseTwo;
    private TextView tvDiseaseThree;
    private TextView tvDiseaseFour;
    private TextView tvDiseaseFive;
    private TextView tvDiseaseOnePercentage;
    private TextView tvDiseaseTwoPercentage;
    private TextView tvDiseaseThreePercentage;
    private TextView tvDiseaseFourPercentage;
    private TextView tvDiseaseFivePercentage;
    private TextView tvTemperature;
    private TextView tvHumidity;
    private TextView tvOther;
    private TextView tvDate;

    private Button btnHistory;
    private ConstraintLayout cvWarningLayout;

    private Retrofit retrofit;

    private void init() {
        tvStatus = findViewById(R.id.tvDiseaseWarning);
        tvDiseaseOne = findViewById(R.id.tvDiseaseOne);
        tvDiseaseTwo = findViewById(R.id.tvDiseaseTwo);
        tvDiseaseThree = findViewById(R.id.tvDiseaseThree);
        tvDiseaseFour = findViewById(R.id.tvDiseaseFour);
        tvDiseaseFive = findViewById(R.id.tvDiseaseFive);
        tvDiseaseOnePercentage = findViewById(R.id.tvDiseaseOnePercentage);
        tvDiseaseTwoPercentage = findViewById(R.id.tvDiseaseTwoPercentage);
        tvDiseaseThreePercentage = findViewById(R.id.tvDiseaseThreePercentage);
        tvDiseaseFourPercentage = findViewById(R.id.tvDiseaseFourPercentage);
        tvDiseaseFivePercentage = findViewById(R.id.tvDiseaseFivePercentage);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvOther = findViewById(R.id.tvSomething);
        tvDate = findViewById(R.id.tvDate);
        btnHistory = findViewById(R.id.btnHistory);
        cvWarningLayout = findViewById(R.id.cvWarningLayout);

    }

    private String getRealName(String rawName) {
        String realName = "";

        switch (rawName) {
            case "yellowshoulder" : realName = "Yellow Shoulder";
                break;
            case "septorialeafspot" : realName = "Septorial Leafspot";
                break;
            case "yellowcurved" : realName = "Yellow Curved";
                break;
            case "bacterialspot" : realName = "Bacterial Spot";
                break;
            case "tomato mosaic" : realName = "Tomato Mosaic";
                break;
            default: realName = "N/A";
                break;

        }

        return realName;
    }

    private void downloadData() {
        retrofit = createRetrofit();

        Raspberry raspberryService = retrofit.create(Raspberry.class);

        Call<com.example.howldevelopment.automatedgreenhouses.models.Result> resultsCall = raspberryService.results();

        resultsCall.enqueue(new Callback<com.example.howldevelopment.automatedgreenhouses.models.Result>() {
            @Override
            public void onResponse(Call<com.example.howldevelopment.automatedgreenhouses.models.Result> call, Response<com.example.howldevelopment.automatedgreenhouses.models.Result> response) {
                if (response.code() == 200) {
                    tvDiseaseOne.setText( getRealName(response.body().getName().get(0)) + ":");
                    tvDiseaseOnePercentage.setText( String.format("%.2f", (Float.parseFloat(response.body().getPrediction().get(0))) * 100) + "%" );
                    tvDiseaseTwo.setText(getRealName(response.body().getName().get(1)) + ":");
                    tvDiseaseTwoPercentage.setText( String.format("%.2f", (Float.parseFloat(response.body().getPrediction().get(1))) * 100) + "%"  );
                    tvDiseaseThree.setText(getRealName(response.body().getName().get(2)) + ":");
                    tvDiseaseThreePercentage.setText( String.format("%.2f", (Float.parseFloat(response.body().getPrediction().get(2))) * 100) + "%"  );
                    tvDiseaseFour.setText(getRealName(response.body().getName().get(3)) + ":");
                    tvDiseaseFourPercentage.setText( String.format("%.2f", (Float.parseFloat(response.body().getPrediction().get(3))) * 100) + "%"  );
                    tvDiseaseFive.setText(getRealName(response.body().getName().get(4)) + ":");
                    tvDiseaseFivePercentage.setText( String.format("%.2f", (Float.parseFloat(response.body().getPrediction().get(4))) * 100) + "%" );

                    double percentage = 0;
                    String name = "";
                    int count = 0;

                    for (String predict: response.body().getPrediction()) {
                        if (Double.parseDouble(predict) > 0.6) {
                            if (Double.parseDouble(predict) > percentage) {
                                percentage = Double.parseDouble(predict);
                                name = getRealName(response.body().getName().get(count)) ;
                            }

                        }
                        count++;
                    }

                    if (Build.VERSION.SDK_INT >= 23 ) {
                        if (percentage != 0) {
                            tvStatus.setText(name + " Detected");
                            cvWarningLayout.setBackgroundColor( getResources().getColor(R.color.colorFailed,getTheme()));
                        } else {
                            tvStatus.setText("No Disease Detected");
                            cvWarningLayout.setBackgroundColor( getResources().getColor(R.color.colorSuccess,getTheme()));
                        }
                    } else {
                        if (percentage != 0) {
                            tvStatus.setText(name + " Detected");
                            cvWarningLayout.setBackgroundColor( getResources().getColor(R.color.colorFailed));
                        } else {
                            tvStatus.setText("No Disease Detected");
                            cvWarningLayout.setBackgroundColor( getResources().getColor(R.color.colorSuccess));
                        }
                    }

                    String dateString = "";
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
                    try {
                        Date date = format.parse(response.body().getTimeStamp());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        dateString = String.format("- %s, %s %s",calendar.get(Calendar.YEAR), calendar.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault()), calendar.get(Calendar.DAY_OF_MONTH) );
                    } catch (ParseException e) {

                    }

                    tvDate.setText(dateString);

                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.howldevelopment.automatedgreenhouses.models.Result> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        Raspberry raspberryServiceStatus = retrofit.create(Raspberry.class);

        Call<Status> statusCall = raspberryServiceStatus.status();

        statusCall.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                //Toast.makeText(getApplicationContext(), String.format("%s", response.body().getTemperature() ),Toast.LENGTH_SHORT).show();
                if (response.code() == 200) {
                    tvHumidity.setText( String.format("%s ",response.body().getHumidity()) + "RH(%)");
                    tvTemperature.setText( String.format( "%s Celcius ", response.body().getTemperature()) );
                    tvOther.setText( String.format("%s Units", response.body().getSomething()) );
                } else {
                    Toast.makeText(getApplicationContext(), response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(),Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ActionBar Properties
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");
        //actionBar.hide();

        try {
            init();
            downloadData();
        } catch (Exception e) {

        }




    }
}
