package com.lh16808.app.lhys.laohuangli.page;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.View;

public class PageUtil {
	public static Bitmap takeShort(View v, Bitmap viewShort) {
		v.setDrawingCacheEnabled(true);
		v.buildDrawingCache();
		Bitmap b = v.getDrawingCache();
	
		Canvas canvas = new Canvas(viewShort);
		canvas.drawBitmap(b, 0, 0, null);
		v.destroyDrawingCache();
		return viewShort;
	}
	
	public static PointF getCross(PointF P1, PointF P2, PointF P3, PointF P4) {
		PointF CrossP = new PointF();
		float a1 = (P2.y - P1.y) / (P2.x - P1.x);
		float b1 = ((P1.x * P2.y) - (P2.x * P1.y)) / (P1.x - P2.x);

		float a2 = (P4.y - P3.y) / (P4.x - P3.x);
		float b2 = ((P3.x * P4.y) - (P4.x * P3.y)) / (P3.x - P4.x);
		CrossP.x = (b2 - b1) / (a1 - a2);
		CrossP.y = a1 * CrossP.x + b1;
		return CrossP;
	}

}
