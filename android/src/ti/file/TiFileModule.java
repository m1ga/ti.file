/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2018 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.file;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;
import org.appcelerator.titanium.TiApplication;

import java.io.File;
import java.io.RandomAccessFile;

@Kroll.module(name = "TiFile", id = "ti.file")
public class TiFileModule extends KrollModule {

    private static final String LCAT = "TiFileModule";

    public TiFileModule() {
        super();
    }

    @Kroll.onAppCreate
    public static void onAppCreate(TiApplication app) {
    }

    @Kroll.method
    public void deleteFiles() {
        for (File child : TiApplication.getAppCurrentActivity().getFilesDir().listFiles()) {
            child.delete();
        }
    }

    @Kroll.method
    public void create(KrollDict options) {

        String filename = options.getString("filename");
        String value = options.getString("size");
        int intValue = 0;
        int loopValue = 1;

        if (value.contains("M")) {
            // MB
            value = value.replace("M", "");
            intValue = Integer.parseInt(value);
            loopValue = 1;
        } else if (value.contains("G")) {
            // GB
            value = value.replace("G", "");
            intValue = Integer.parseInt(value);
            loopValue = 2;
        } else {
            intValue = Integer.parseInt(value);
            loopValue = 1;
        }
        try {
            File file = new File(TiApplication.getAppCurrentActivity().getFilesDir(), filename);
            if (file.exists()) {
                file.delete();
            }
            RandomAccessFile f = new RandomAccessFile(file, "rw");
            long size = 1024;
            for (var i = 0; i < loopValue; ++i) {
                size *= 1024;
            }

            byte[] x = new byte[1048576];
            KrollDict kd = new KrollDict();
            long maxSize = size * intValue;

            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        while (f.length() < maxSize) {
                            kd.put("currentSize", f.length());
                            kd.put("maxSize", maxSize);
                            fireEvent("writing", kd);
                            f.write(x);
                        }
                        kd.put("currentSize", maxSize);
                        kd.put("maxSize", maxSize);
                        fireEvent("writing", kd);
                        f.close();
                    } catch (Exception e) {

                    }
                }
            };

            thread.start();


        } catch (Exception e) {
            Log.i(LCAT, e.getMessage());
        }
    }
}
