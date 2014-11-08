package com.dvictor.doodle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DoodleView extends View {
	// Member Variables
	Paint p;
	/** Description of transformations you can make to a canvas.  Lines, shapes, etc.  Don't draw individual points.  Track small lines for any direction until they change directions.  Paths are so smart they will loop themselves.  Just tell the path to draw.  */
	Path path;
	
	// 3 constructors are used in different situations.
	
	// 1 - Used for: 
	public DoodleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	// 2 - Layout inflator calls #2 or 3, this one if not applying a theme.
	public DoodleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// Load and store attributes for the view
		p = new Paint();
		p.setColor(Color.BLUE);
		p.setStrokeWidth(5);
		p.setStyle(Style.STROKE);
		
		path = new Path();
	}

	// 3 - Layout inflator calls this if a custom style.
	public DoodleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// Load and store attributes for the view
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// Look at state (int, floats, text)
		// Use the state to create a visual representation.
		// NEVER initialize objects in onDraw.  This is called lots of times.
		//canvas.drawCircle(50, 100, 100, p); // USE VARIABLES, not hard code, detect width, height.
		canvas.drawPath(path,p);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				path.moveTo(event.getX(),event.getY());
				postInvalidate();
				return true; // Still care about following events. (looking for more moves or up)
			case MotionEvent.ACTION_MOVE:
				path.lineTo(event.getX(),event.getY());
				postInvalidate();
				return true; // Still care about following events (looking for more moves or up)
			case MotionEvent.ACTION_UP:
				return false; // done listening to this line of events.
		}
		return false;
	}
	
	// TODO: SET THE CONTENT HEIGHT & WIDTH
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	// TODO: Save any state you want to keep if the screen is rotated -- path.
	//       This is written to a special spot when it comes back into memory.
	@Override
	protected Parcelable onSaveInstanceState() {
		// SEE EXAMPLE IN DEFINING CUSTOM VIEWS
		// TODO Auto-generated method stub
		return super.onSaveInstanceState();
	}
	
	// TODO: Then you take the keys from the bundle to restore
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		// SEE EXAMPLE IN DEFINING CUSTOM VIEWS
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(state);
	}

}
