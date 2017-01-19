package utilities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nickromero.seniorproject.R;
import com.nickromero.seniorproject.views.MainActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.io.File;

/**
 * Created by nickromero on 1/5/17.
 */

public class PDFViewer {

    public PDFViewer() {
    }

    public void openPDF(String pathToFile, String fileName, View view) {
        File pdfToOpen = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                , "pdf.pdf");

        Log.e("HERE", pdfToOpen.toString());
        Uri path = Uri.fromFile(pdfToOpen);
        Log.e("HERE", path.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setDataAndType(Uri.fromFile(pdfToOpen), "application/pdf");

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);


        Intent intentChoose = Intent.createChooser(intent, "Open PDF");

        try {
            view.getContext().startActivity(intentChoose);
        } catch (ActivityNotFoundException e) {
            Toast toast = new Toast(view.getContext().getApplicationContext());
            toast.setText(R.string.open_pdf_error);
            toast.show();
        }

    }

    /**
     * Using the below method inside of the recyclerAdapter works to load the initial page
     * of a pdf onto the screen. However it currently does too much on the UI thread.
     * Maybe each paper could save a bitmpa when it is created or when the app is loaded?
     *
     * @param view
     * @param pdfName
     */
    public void displayFirstPage(ImageView view, String pdfName) {
        /*
            File pdfToRender = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    , "pdf.pdf");
            ParcelFileDescriptor fileDescriptor;

            try {
                fileDescriptor = ParcelFileDescriptor.open(pdfToRender,
                        ParcelFileDescriptor.MODE_READ_WRITE);

                PdfRenderer render = new PdfRenderer(fileDescriptor);

                PdfRenderer.Page firstPage;

                firstPage = render.openPage(0);

                Bitmap bitmap = Bitmap.createBitmap(firstPage.getWidth(), firstPage.getHeight(),
                        Bitmap.Config.ARGB_8888);

                firstPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

                mPaperImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            */


    }
}
