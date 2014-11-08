package com.dvictor.doodle;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class BrushSizeDialog extends DialogFragment {

	private DoodleView doodleView;
	private Button bSmall;
	private Button bMedium;
	private Button bLarge;

	public BrushSizeDialog(DoodleView doodleView) {
		this.doodleView = doodleView;
	}
	
	public static BrushSizeDialog newInstance(String title, DoodleView doodleView) {
		BrushSizeDialog frag = new BrushSizeDialog(doodleView);
		Bundle args = new Bundle();
		args.putString("title", title);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_brush_sizes, container);
		bSmall  = (Button) view.findViewById(R.id.bSmall );
		bMedium = (Button) view.findViewById(R.id.bMedium);
		bLarge  = (Button) view.findViewById(R.id.bLarge );
		String title = getArguments().getString("title", "Select Color Size");
		getDialog().setTitle(title);
		// Show soft keyboard automatically
		getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		// Add button listeners
		setupButtonListener(bSmall,bMedium,bLarge);
		return view;
	}
	
	private void setupButtonListener(Button... buttons){
		final BrushSizeDialog parentThis = this;
		for(Button b : buttons){
			b.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
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
							Toast.makeText(parentThis.getActivity(), "ERROR: Unknown View", Toast.LENGTH_SHORT).show();
							break;
			    	}
					dismiss();
				}
			});
		}
	}
	
}