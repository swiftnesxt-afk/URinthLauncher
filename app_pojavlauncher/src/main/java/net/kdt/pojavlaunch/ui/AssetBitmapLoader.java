package net.kdt.pojavlaunch.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public final class AssetBitmapLoader {
    private AssetBitmapLoader() {}

    public static Bitmap loadBase64Jpeg(Context context, int rawId) {
        try (InputStream in = context.getResources().openRawResource(rawId);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = in.read(buffer)) != -1) out.write(buffer, 0, read);
            String encoded = out.toString("UTF-8").replaceAll("\\s+", "");
            byte[] bytes = Base64.decode(encoded, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            if (bitmap == null) throw new IllegalArgumentException("Decoded artwork is not a bitmap");
            return bitmap;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to load launcher artwork", e);
        }
    }
}
