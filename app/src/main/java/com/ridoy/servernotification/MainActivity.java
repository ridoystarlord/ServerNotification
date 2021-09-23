package com.ridoy.servernotification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ridoy.servernotification.API.APIUtilities;
import com.ridoy.servernotification.Model.NotificationData;
import com.ridoy.servernotification.Model.PushNotification;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText title,message;
    private Button notificationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title=findViewById(R.id.titile_ET);
        message=findViewById(R.id.message_ET);
        notificationBtn=findViewById(R.id.send_btn);

        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC);

        notificationBtn.setOnClickListener(v -> {
            String title_Txt=title.getText().toString();
            String message_Txt=message.getText().toString();

            if (!title_Txt.isEmpty() && !message_Txt.isEmpty()){
                PushNotification notification=new PushNotification(new NotificationData(title_Txt,message_Txt),Constants.TOPIC);
                sendNotification(notification);
            }
        });
    }

    private void sendNotification(PushNotification notification) {

        APIUtilities.getClient().sendNotification(notification).enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                if (response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure:"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}