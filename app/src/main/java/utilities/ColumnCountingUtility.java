package utilities;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by nickromero on 1/3/17.
 */

public class ColumnCountingUtility {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int numberOfColumns = (int) (dpWidth / 180);
        return numberOfColumns;
    }
}
