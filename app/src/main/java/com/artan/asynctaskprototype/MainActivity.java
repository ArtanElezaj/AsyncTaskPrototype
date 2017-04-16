package com.artan.asynctaskprototype;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button mBtnStart, mBtnStop;
    TextView mTvCount;
    Boolean threadActive;
    int currentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStart = (Button) findViewById(R.id.start);
        mBtnStop = (Button) findViewById(R.id.stop);
        mTvCount = (TextView) findViewById(R.id.tv_count);
        currentCount = 0;


        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threadActive = true;
                new AsyncT().execute(currentCount);
            }
        });

        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threadActive = false;
            }
        });


    }

    //The first parameter is for doInBackground method
    //The second one is for onProgressUpdate method
    //The third one is for onPostExecute method
    public class AsyncT extends AsyncTask<Integer, Integer, Integer> {

        int number;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            number = 0;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {

            number = integers[0];

            while (threadActive) {
                number++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(number); //this line send the value of the 'number' to onProgressUpdate;
            }
            return number;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            mTvCount.setText("" + values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            mTvCount.setText("" + integer);
            currentCount = integer;
        }
    }
}
