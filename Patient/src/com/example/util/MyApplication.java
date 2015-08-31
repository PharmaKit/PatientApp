package com.example.util;

import org.acra.*;
import org.acra.annotation.*;
import com.example.patient.R;

import android.app.Application;

@ReportsCrashes(
formUri = "http://pharmakit.co/android_api/reports/reportupload.php",
//formUriBasicAuthLogin = "yourlogin", // optional
//formUriBasicAuthPassword = "y0uRpa$$w0rd", // optional
httpMethod = org.acra.sender.HttpSender.Method.POST,
mode = ReportingInteractionMode.TOAST,
resToastText = R.string.crash_toast_text)
    public class MyApplication extends Application {
        @Override
        public void onCreate() {
            super.onCreate();

            // The following line triggers the initialization of ACRA
            ACRA.init(this);
        }
    }