package com.dvictor.doodle;

import android.graphics.Paint;
import android.graphics.Path;

/** Path pairing with a specific paint & brush. */
public class PaintPath extends Path {
	private final Paint p; 

	/** Paint info to apply to this path.  Usually color and brush size. */
	public PaintPath(Paint p) {
		this.p = p;
	}
	
	/** Get the paint for this path. */
	public Paint getPaint(){
		return p;
	}
	
}
