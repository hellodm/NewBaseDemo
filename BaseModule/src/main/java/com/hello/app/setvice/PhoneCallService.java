package com.hello.app.setvice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PhoneCallService extends Service {

    public PhoneCallService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
