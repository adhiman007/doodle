package com.dvictor.doodle;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DoodleView extends View {
	// Static Variables
	private static final int   BACKGROUND_COLOR    = Color.WHITE;
	private static final float DEFAULT_STROKEWIDTH = 5;
	// Member Variables
	/** Description of transformations you can make to a canvas.  Lines, shapes, etc.  Don't draw individual points.  Track small lines for any direction until they change directions.  Paths are so smart they will loop themselves.  Just tell the path to draw.  */
	//private Paint p;   Instead going to use a combined paint & path so we can pair multiple
	//private Path path; Using paired paing & path instead.
	//private PaintPath path;  Instead using a list so we can have multiple paint-path pairings painted in the right order.
	private LinkedList<PaintPath> paths;
	
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
		initializePath();
	}

	// 3 - Layout inflator calls this if a custom style.
	public DoodleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// Load and store attributes for the view
	}
	
	/** Setup/initialize the paint & path for the first time. */
	private void initializePath(){
		paths = new LinkedList<PaintPath>();
		Paint p = new Paint();
		p.setColor(Color.BLUE);
		p.setStrokeWidth(5);
		p.setStyle(Style.STROKE);
		paths.add(new PaintPath(p));
	}
	
	/** Find the last visible paint (anything that wasn't erase. */
	private Paint getLastVisiblePaint(){
		// Go through all the paths, remembering the last visible paint (not eraser), and by the time we get to the end we'll have it.
		// - NOTE: would be better to iterate backwards.
		Paint lastVisiblePaint = null;
		for(PaintPath path : paths){
			if(path.getPaint().getColor()!=Color.WHITE) lastVisiblePaint = path.getPaint();
		}
		// If didn't find one - SHOULD NEVER HAPPEN because we init the list at the start), just error
		if(lastVisiblePaint==null) throw new RuntimeException("Failed to find last visible paint.  There should at least have been one back at the beginning.");
		return lastVisiblePaint;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// Look at state (int, floats, text)
		// Use the state to create a visual representation.
		// NEVER initialize objects in onDraw.  This is called lots of times.
		//canvas.drawCircle(50, 100, 100, p); // USE VARIABLES, not hard code, detect width, height.
		//canvas.drawPath(path,path.getPaint()); // Loop over multiple of different colors & brushes we kept in the list in the order the user made them.
		canvas.drawColor(Color.WHITE);
		for(PaintPath path : paths){
			canvas.drawPath(path,path.getPaint());
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				paths.getLast().moveTo(event.getX(),event.getY());
				postInvalidate();
				return true; // Still care about following events. (looking for more moves or up)
			case MotionEvent.ACTION_MOVE:
				paths.getLast().lineTo(event.getX(),event.getY());
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
	
	/** User selects to create new canvas / start over. */
	public void onNew(){
		initializePath();
		postInvalidate();
	}
	
	/** User changes color. */
	public void onColorChange(int newColor){
		// If not already that color, change:
		Paint existingPaint = paths.getLast().getPaint(); 
		if(existingPaint.getColor()!=newColor){
			Paint p = new Paint(existingPaint);
			p.setColor(newColor);
			// Also if it was previously erase (white), change width back to default.
			if(existingPaint.getColor()==BACKGROUND_COLOR) p.setStrokeWidth(getLastVisiblePaint().getStrokeWidth());
			paths.add(new PaintPath(p));
		}
	}
	
	/** User changes brush size. */
	public void onBrushChange(float newStrokeWidth){
		// If not already that size, change:
		Paint existingPaint = paths.getLast().getPaint(); 
		if(existingPaint.getStrokeWidth()!=newStrokeWidth){
			Paint p = new Paint(existingPaint);
			p.setStrokeWidth(newStrokeWidth);
			paths.add(new PaintPath(p));
		}
	}
	
	/** User changes to erase, which involves a color and brush size change. */
	public void onErase(){
		// If not already that color, change:
		Paint existingPaint = paths.getLast().getPaint(); 
		if(existingPaint.getColor()!=BACKGROUND_COLOR){
			Paint p = new Paint(existingPaint);
			p.setColor(BACKGROUND_COLOR);
			p.setStrokeWidth(40);
			paths.add(new PaintPath(p));
		}
	}

}
