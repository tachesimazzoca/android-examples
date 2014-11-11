package com.github.tachesimazzoca.android.example.asynctask;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

public class DownloaderTask extends AsyncTask<String, DownloaderTask.Progress, Long> {
    private static long NETWORK_DELAY = 100L;

    private final WeakReference<Listener> mListener;

    public class Progress {
        public final String filename;
        public final float percent;

        public Progress(String filename, float percent) {
            this.filename = filename;
            this.percent = percent;
        }

        @Override
        public String toString() {
            return String.format(java.util.Locale.getDefault(), "%.02f%% %s", percent, filename);
        }
    }

    public interface Listener {
        public void onProgress(Progress progress);

        public void onResult(Long result);
    }

    public DownloaderTask(Listener listener) {
        super();

        mListener = new WeakReference<Listener>(listener);
    }

    @Override
    protected Long doInBackground(String... params) {
        // simulate preparing ...
        try {
            Thread.sleep(NETWORK_DELAY * 10);
        } catch (InterruptedException e) {
            throw new Error(e);
        }

        int len = params.length;
        long t = System.currentTimeMillis();
        final int MAX = 100;
        for (int i = 0; i < len; i++) {
            for (int n = 0; n < MAX; n++) {
                // simulate network delay ...
                try {
                    Thread.sleep(NETWORK_DELAY);
                } catch (InterruptedException e) {
                    throw new Error(e);
                }
                Progress[] progress = new Progress[1];
                progress[0] = new Progress(params[i], ((float) n / MAX));
                // publish each progress
                publishProgress(progress);
            }
        }
        return System.currentTimeMillis() - t;
    }

    @Override
    protected void onProgressUpdate(Progress... progress) {
        Listener listener = mListener.get();
        if (null != listener)
            listener.onProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(Long result) {
        Listener listener = mListener.get();
        if (null != listener)
            listener.onResult(result);
    }
}
