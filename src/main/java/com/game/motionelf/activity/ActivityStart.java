package com.game.motionelf.activity;

import android.app.Activity;
import android.os.Bundle;
import android.system.ErrnoException;
import android.system.Os;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ActivityStart extends Activity {

    private static final String TAG = "MotionElf";

    private static final String MOTIONELF = "#!/system/bin/sh\n" +
            "\n" +
            "path=/data/data/me.piebridge.brevent/brevent.sh\n" +
            "comp=me.piebridge.brevent/.ui.BreventActivity\n" +
            "\n" +
            "if [ ! -r $path ]; then\n" +
            "    am start -n $comp\n" +
            "    sleep 3\n" +
            "fi\n" +
            "\n" +
            "sh $path\n" +
            "\n" +
            "if [ $? -eq 0 ]; then\n" +
            "    am start -n $comp\n" +
            "fi\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        makeMotionelf();
        super.onCreate(savedInstanceState);
        finish();
    }

    private void makeMotionelf() {
        d("making motionelf");
        File files = getFilesDir();
        File output = new File(files, "motionelf_server");
        try (
                OutputStream os = new FileOutputStream(output)
        ) {
            os.write(MOTIONELF.getBytes());
            os.flush();
            Os.chmod(output.getPath(), 0644);
        } catch (IOException | ErrnoException e) {
            d("Can't make motionelf", e);
        }
    }

    private void d(String s) {
        android.util.Log.d(TAG, s);
    }

    private void d(String s, Throwable t) {
        android.util.Log.d(TAG, s, t);
    }

}