package cn.com.hiss.www.multilib.utils;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by wuyanzhe on 2017/4/20.
 */

public class HissFileDownloader {
    private static final String TAG = HissFileDownloader.class.getSimpleName();

    public synchronized static void startDownload(String url, String path, DownloadListener downloadListener) {
        FileDownloader.getImpl().create(url).setPath(path).setCallbackProgressTimes(300).setMinIntervalUpdateSpeed(400).setListener(downloadListener).start();
    }

    public static abstract class DownloadListener extends FileDownloadListener {

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void warn(BaseDownloadTask task) {

        }
    }

    //    private static class ViewHolder {
    //        private ProgressBar pb;
    //        private TextView detailTv;
    //        private TextView speedTv;
    //        private int position;
    //        private TextView filenameTv;
    //
    //        private WeakReference<FragmentActivity> weakReferenceContext;
    //
    //        public ViewHolder(WeakReference<FragmentActivity> weakReferenceContext, final ProgressBar pb, final TextView detailTv, final TextView speedTv, final int position) {
    //            this.weakReferenceContext = weakReferenceContext;
    //            this.pb = pb;
    //            this.detailTv = detailTv;
    //            this.position = position;
    //            this.speedTv = speedTv;
    //        }
    //
    //        public void setFilenameTv(TextView filenameTv) {
    //            this.filenameTv = filenameTv;
    //        }
    //
    //        private void updateSpeed(int speed) {
    //            speedTv.setText(String.format("%dKB/s", speed));
    //        }
    //
    //        public void updateProgress(final int sofar, final int total, final int speed) {
    //            if (total == -1) {
    //                // chunked transfer encoding data
    //                pb.setIndeterminate(true);
    //            } else {
    //                pb.setMax(total);
    //                pb.setProgress(sofar);
    //            }
    //
    //            updateSpeed(speed);
    //
    //            if (detailTv != null) {
    //                detailTv.setText(String.format("sofar: %d total: %d", sofar, total));
    //            }
    //        }
    //
    //        public void updatePending(BaseDownloadTask task) {
    //            if (filenameTv != null) {
    //                filenameTv.setText(task.getFilename());
    //            }
    //        }
    //
    //        public void updatePaused(final int speed) {
    //            toast(String.format("paused %d", position));
    //            updateSpeed(speed);
    //            pb.setIndeterminate(false);
    //        }
    //
    //        public void updateConnected(String etag, String filename) {
    //            if (filenameTv != null) {
    //                filenameTv.setText(filename);
    //            }
    //        }
    //
    //        public void updateWarn() {
    //            toast(String.format("warn %d", position));
    //            pb.setIndeterminate(false);
    //        }
    //
    //        public void updateError(final Throwable ex, final int speed) {
    //            toast(String.format("error %d %s", position, ex));
    //            updateSpeed(speed);
    //            pb.setIndeterminate(false);
    //            ex.printStackTrace();
    //        }
    //
    //        public void updateCompleted(final BaseDownloadTask task) {
    //
    //            toast(String.format("completed %d %s", position, task.getTargetFilePath()));
    //
    //            if (detailTv != null) {
    //                detailTv.setText(String.format("sofar: %d total: %d", task.getSmallFileSoFarBytes(), task.getSmallFileTotalBytes()));
    //            }
    //
    //            updateSpeed(task.getSpeed());
    //            pb.setIndeterminate(false);
    //            pb.setMax(task.getSmallFileTotalBytes());
    //            pb.setProgress(task.getSmallFileSoFarBytes());
    //        }
    //
    //        private void toast(final String msg) {
    //            if (this.weakReferenceContext != null && this.weakReferenceContext.get() != null) {
    //                Toast.makeText(this.weakReferenceContext.get(), msg, Toast.LENGTH_SHORT).show();
    //            }
    //        }
    //
    //    }

}
