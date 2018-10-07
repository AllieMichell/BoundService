package actpracticarecepcion.app.com.boundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity_BoundS extends AppCompatActivity {
        LService mService;
        boolean mBound = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main__bound_s);
        }

        @Override
        protected void onStart() {
            super.onStart();
            // Bind to LocalService
            Intent intent = new Intent(this, LService.class);
            bindService(intent, Connection, Context.BIND_AUTO_CREATE);
        }

        @Override
        protected void onStop() {
            super.onStop();
            // Unbind from the service
            if (mBound) {
                unbindService(Connection);
                mBound = false;
            }
        }
        public void stopService(View view) {
            Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
            onStop();
        }
        public void startService(View view) {
            Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
            onStart();
        }
        public void randomNumber (View view) {
            if(mBound) {
                int num = mService.getRandomNumber();
                Toast.makeText(this, "Number --> " + num, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Random Number", Toast.LENGTH_SHORT).show();
            }
        }
        private ServiceConnection Connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                LService.LocalBinder binder = (LService.LocalBinder) iBinder;
                mService = binder.getService();
                mBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mBound = false;
            }
        };
    }