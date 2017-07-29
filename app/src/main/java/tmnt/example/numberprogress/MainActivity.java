package tmnt.example.numberprogress;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import tmnt.example.numberprogress.NumberProgress.NumberProgress;

public class MainActivity extends AppCompatActivity {

    private NumberProgress mNumberProgress;
    private Button btn;

    private static final String TAG = "MainActivity";

    private int i = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (i<=3600){

                mNumberProgress.setProgess(i);
                Log.i(TAG, "handleMessage: "+String.valueOf(i));
                i+=200;
                mHandler.sendEmptyMessageDelayed(0, 100);

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumberProgress = (NumberProgress) findViewById(R.id.number);
        btn = (Button) findViewById(R.id.btn);
        mNumberProgress.setMax(3600);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessageAtTime(0, 10);
            }
        });


    }
}
