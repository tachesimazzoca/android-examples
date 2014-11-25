package com.github.tachesimazzoca.android.example.asynctask;

import android.os.AsyncTask;

public class MeanTask extends AsyncTask<Integer, String, Float> {
    private Listener mListener;

    public interface Listener {
        public void onProgress(String message);

        public void onResult(Float result);
    }

    public MeanTask(Listener listener) {
        mListener = listener;
    }

    @Override
    protected Float doInBackground(Integer... params) {
        int len = params.length;
        int sum = 0;
        for (int i = 0; i < len; i++) {
            publishProgress(String.format(
                    "sum = %d + %d (%d/%d)", sum, params[i], i + 1, len));
            sum += params[i];
            waitFor(1000L);
        }
        return ((float) sum) / len;
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        mListener.onProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(Float result) {
        mListener.onResult(result);
    }

    private void waitFor(long msec) {
        try {
            Thread.sleep(msec);
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
