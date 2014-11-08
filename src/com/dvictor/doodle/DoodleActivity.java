package com.dvictor.doodle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

public class DoodleActivity extends FragmentActivity {
	// Remembered Views
	private DoodleView doodleView;
	private BrushSizeDialog lastBrushSizeDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doodle);
		doodleView = (DoodleView) findViewById(R.id.doodleView);
	}
	
	public void onNew(View v){
		doodleView.onNew();
	}
	
	/** When use picks brush, need to show brush size dialog. */
    public void onBrush(View v){
        FragmentManager fm = getSupportFragmentManager();
        BrushSizeDialog brushSizeDialog = BrushSizeDialog.newInstance("Select Brush Size",doodleView);
        brushSizeDialog.show(fm, "layout_brush_sizes");
        lastBrushSizeDialog = brushSizeDialog;
    }
    
    /** When user picks a specific brush size on the brush size dialog. */
    public void onBrushSize(View v){
    	switch(v.getId()){
	    	case R.id.bSmall:
	    		doodleView.onBrushChange(5);
	    		break;
	    	case R.id.bMedium:
	    		doodleView.onBrushChange(20);
	    		break;
	    	case R.id.bLarge:
	    		doodleView.onBrushChange(40);
	    		break;
			default:
				Toast.makeText(this, "ERROR: Unknown View", Toast.LENGTH_SHORT).show();
				break;
    	}
    	lastBrushSizeDialog.dismiss();
    }

    public void onColorChange(View v){
    	switch(v.getId()){
	    	case R.id.bErase:
	    		doodleView.onColorChange(Color.WHITE);
	    		break;
	    	case R.id.bRed:
	    		doodleView.onColorChange(Color.RED);
	    		break;
	    	case R.id.bOrange:
	    		doodleView.onColorChange(Color.rgb(252,162,5));
	    		break;
	    	case R.id.bYellow:
	    		doodleView.onColorChange(Color.YELLOW);
	    		break;
	    	case R.id.bGreen:
	    		doodleView.onColorChange(Color.GREEN);
	    		break;
	    	case R.id.bBlue:
	    		doodleView.onColorChange(Color.BLUE);
	    		break;
	    	case R.id.bBrown:
	    		doodleView.onColorChange(Color.rgb(99,64,2));
	    		break;
	    	case R.id.bBlack:
	    		doodleView.onColorChange(Color.BLACK);
	    		break;
    		default:
    			Toast.makeText(this, "ERROR: Unknown View", Toast.LENGTH_SHORT).show();
    			break;
    	}
    }
    
    public void onErase(View v){
    	doodleView.onErase();
    }

}
