package com.indapp.daroodapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fonts.CipherNormal;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.utils.Constants;
import com.utils.Contact;
import com.utils.DatabaseHandler;
import com.zcw.togglebutton.ToggleButton;

/**
 * Simple Activity for curl testing.
 * 
 * @author harism
 * 15 para ka 755 page missing hai
 */
public class CurlActivity extends Activity implements OnPageChangeListener, OnLoadCompleteListener {

	

	int lastread=0;
	String bookmarkPage="";
	TextView pagenotxt;
	int currentPage=0;
PDFView pdfView;
ImageView fav;
int rediRectPage=0;

RelativeLayout heaerMain;
	ImageView imgBack;
	boolean nigthMode=false;
	CipherNormal txtDay,txtNight;
	ToggleButton toggleBtn_nightmode;

	String BOTTOM_MENU_NAMES[]={"Go To","Bookmarks","Para Index","Surah/ Juzz Index","Night Mode (On)"};


	LinearLayout tab_english,tab_urdu;
	ImageView imgClose,info_icon;
	LinearLayout layout_tuotorial;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  		setContentView(R.layout.paging);
		pdfView=(PDFView)findViewById(R.id.pdfView);
		fav=(ImageView)findViewById(R.id.fav);
		toggleBtn_nightmode=(ToggleButton)findViewById(R.id.toggle_button);
		if(getIntent().getExtras()!=null)
		{

			if(getIntent().getExtras().containsKey("noQurran")==true)
			{
				isQuraan=false;
				rediRectPage= 0;//Integer.parseInt(getIntent().getExtras().getString("noQurran"));
			}
			if(getIntent().getExtras().containsKey("page")==true)
			currentPage=getIntent().getExtras().getInt("page");
		}



        tab_english=(LinearLayout)findViewById(R.id.tab_english);
        tab_urdu=(LinearLayout)findViewById(R.id.tab_urdu);
		layout_tuotorial=(LinearLayout)findViewById(R.id.layout_tuotorial);
		layout_tuotorial.setVisibility(View.VISIBLE);
		imgClose=(ImageView)findViewById(R.id.imgClose);
		info_icon=(ImageView)findViewById(R.id.img_info);
		info_icon.setVisibility(View.GONE);
		Constants.chalngeImageColor(imgClose,"#FFFFFF");
		Constants.chalngeImageColor(info_icon,"#FFFFFF");

		imgClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				layout_tuotorial.setVisibility(View.GONE);

			}
		});
		info_icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				layout_tuotorial.setVisibility(View.VISIBLE);

			}
		});
		if(isQuraan==false)
		{
			fav.setVisibility(View.INVISIBLE);
//            fav.setLayoutParams(new ImageView);
		}
		fav.setImageResource(R.drawable.favunsel);
		fav.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{

				AddToBarmak();
			}
		});
        heaerMain=(RelativeLayout)findViewById(R.id.heaerMain);

		txtDay=(CipherNormal)findViewById(R.id.txtDay);
		txtNight=(CipherNormal)findViewById(R.id.txtNight);


		if(Constants.sp.getBoolean("nightmode",false)==false)
		{
			nigthMode=false;
			toggleBtn_nightmode.setToggleOff();
			txtNight.setPaintFlags(txtNight.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}
		else
		{
			nigthMode=true;
			toggleBtn_nightmode.setToggleOn();
			txtDay.setPaintFlags(txtDay.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

		}
		if(isQuraan==true)
		{
			pdfView.fromAsset("darood.pdf")
					.defaultPage(currentPage)
					.onPageChange(this)
					.nightMode(nigthMode)

//				.enableAnnotationRendering(true)
					.onLoad(this)
					.scrollHandle(new DefaultScrollHandle(this))
					.spacing(0) // in dp
//					.pageFitPolicy(FitPolicy.BOTH)
//					.fitEachPage(true)
					.load();
		}
		else
		{
			pdfView.fromAsset("apnibaat.pdf")
					.defaultPage(rediRectPage)
					.onPageChange(this)
//				.enableAnnotationRendering(true)
					.onLoad(this)
					.scrollHandle(new DefaultScrollHandle(this))
					.spacing(0) // in dp

					.load();
		}
		Log.v("APP","Night Mode--->"+nigthMode);

		findViewById(R.id.iconup).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {


				pdfView.jumpTo(pdfView.getCurrentPage()-1);

			}
		});
		findViewById(R.id.icondown).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				pdfView.jumpTo(pdfView.getCurrentPage()+1);
			}
		});

		imgBack=(ImageView)findViewById(R.id.imgBack);
		imgBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Constants.chalngeImageColor(imgBack,"#ffffff");

		toggleBtn_nightmode.setOnToggleChanged(new ToggleButton.OnToggleChanged(){
			@Override
			public void onToggle(boolean on) {
				if(on==false)
				{
					pdfView.setNightMode(false);
					pdfView.invalidate();

					Constants.editor.putBoolean("nightmode",false);
					Constants.editor.commit();

					txtNight.setPaintFlags(txtNight.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
					txtDay.setPaintFlags(0);
					txtDay.setText("Day");
				}
				else {
					pdfView.setNightMode(true);
					pdfView.invalidate();
					Constants.editor.putBoolean("nightmode",true);
					Constants.editor.commit();
					txtDay.setPaintFlags(txtDay.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
					txtNight.setPaintFlags(0);
					txtNight.setText("Night");
				}

			}
		});

        heaerMain.setVisibility(View.GONE);
		pdfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(heaerMain.getVisibility()==View.VISIBLE)
                {
                    heaerMain.setVisibility(View.GONE);
                }
                else
                {
                    heaerMain.setVisibility(View.VISIBLE);
                }
            }
        });
		final CipherNormal txt_english=(CipherNormal)findViewById(R.id.txt_english);
        final CipherNormal txt_urdu=(CipherNormal)findViewById(R.id.txt_urdu);

		tab_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt_urdu.setTextColor(Color.WHITE);
                txt_urdu.setBackgroundColor(Color.BLACK);

                txt_english.setTextColor(Color.BLACK);
                txt_english.setBackgroundColor(Color.WHITE);

                ((LinearLayout)findViewById(R.id.layout_english)).setVisibility(View.VISIBLE);
                ((LinearLayout)findViewById(R.id.layout_urdu)).setVisibility(View.GONE);

            }
        });
        tab_urdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                txt_english.setTextColor(Color.WHITE);
                txt_english.setBackgroundColor(Color.BLACK);

                txt_urdu.setTextColor(Color.BLACK);
                txt_urdu.setBackgroundColor(Color.WHITE);

                ((LinearLayout)findViewById(R.id.layout_english)).setVisibility(View.GONE);
                ((LinearLayout)findViewById(R.id.layout_urdu)).setVisibility(View.VISIBLE);
            }
        });
        tab_urdu.performClick();

		Constants.sp=getSharedPreferences(Constants.prefName, Activity.MODE_PRIVATE);
		Constants.editor= Constants.sp.edit();
//		if(Constants.sp.getBoolean("hint",false)==false)
//		{
//			layout_tuotorial.setVisibility(View.VISIBLE);
//			Constants.editor.putBoolean("hint",true);
//			Constants.editor.commit();
//		}
//		else
			{
			layout_tuotorial.setVisibility(View.GONE);
		}
	}
	public void AddToBarmak()
	{

//        AlertDialog.Builder builder=	new AlertDialog.Builder(MainActivity.this);
//        builder.setIcon(android.R.drawable.ic_dialog_alert);
//
//        String yes="Yes",no="No";
//        int temp,temp1;
//
//            builder.setTitle("Add to Book Mark");
//
//            builder.setMessage("Would you like to add to Book Mark");
//
//        builder.setPositiveButton(yes, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                //Stop the activity

		DatabaseHandler db = new DatabaseHandler(CurlActivity.this);

		String str=""+(pdfView.getCurrentPage()+1);
		System.out.println("Current Page-------"+str);

		if(db.getContactsCount(""+str)==0)
		{

			String dataStr="";
				dataStr= Constants.getCurrentTimeStamp();

			System.out.println("Data Strr;;;"+dataStr);
			db = new DatabaseHandler(CurlActivity.this);
			db.addContact(new Contact("", ""+str,dataStr));
			db.close();


			Constants.chalngeImageColor(fav,"#663d33");
//                    Toast.makeText(getApplicationContext(),"Added to bookmark",Toast.LENGTH_SHORT).show();

			Toast.makeText(getApplicationContext(),"Added to Bookmark", Toast.LENGTH_LONG).show();
//			View view = toast.getView();
//			view.setBackgroundColor(Color.parseColor(Constants.TOASTCOLOR));
//			TextView text = (TextView) view.findViewById(android.R.id.message);
////				text.setTextColor(Color.parseColor(Constants.TOASTCOLOR_TEXT));
//
//			text.setTextColor(Color.parseColor(Constants.TOASTCOLOR_TEXT));
//			text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
//			toast.show();
		}
		else
		{
			db = new DatabaseHandler(CurlActivity.this);
			db.deleteExistingBookmark(str);
			db.close();

			Constants.chalngeImageColor(fav,"#DCB148");
			Toast.makeText(getApplicationContext(),"Bookmark Deleted", Toast.LENGTH_LONG).show();
//			View view = toast.getView();
//			view.setBackgroundColor(Color.parseColor(Constants.TOASTCOLOR));
//			TextView text = (TextView) view.findViewById(android.R.id.message);
////				text.setTextColor(Color.parseColor(Constants.TOASTCOLOR_TEXT));
//
//			text.setTextColor(Color.parseColor(Constants.TOASTCOLOR_TEXT));
//			text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
//			toast.show();
		}

	}
	boolean isQuraan=true;
	@Override
	public void onPause() {
		super.onPause();

		
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	public void back(final String title, final String message)
	{
		

		
	}

    @Override
    public void onActivityResult(int req, int res, Intent data)
    {
    	if(res==100)
    	{
    		
//    		if(flipper.getDisplayedChild()==0)
//    	        Constants.editor.putInt("lastread", mBitmapIds1.length-lastread+1);
//    	        else Constants.editor.putInt("lastread", mBitmapIds1.length-lastread-1);
//    	        

    	}
    	
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
    { 
       if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
           // Do your thing 
    	   
//    	   showSettingDialog();

		   finish();
           return true;
       } else {
           return super.onKeyDown(keyCode, event); 
       }
    }

	@Override
	public void onPageChanged(int page, int pageCount) {
//        pageNumber = page;
//        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));

		if(isQuraan) {
			if (Constants.sp == null) {
				Constants.sp = getSharedPreferences(Constants.prefName, Activity.MODE_PRIVATE);
			}
			if (Constants.editor == null) {
				Constants.editor = Constants.sp.edit();
			}
			Constants.editor.putInt("lastread", page + 1);
			Constants.editor.commit();
			DatabaseHandler db = new DatabaseHandler(this);
			if (db.getContactsCount("" + (page + 1)) == 0)
			{
				fav.setImageResource(R.drawable.bookmark_nonfill);
//				Constants.chalngeImageColor(fav,"#FFFFFF");
				Constants.chalngeImageColor(fav,"#DCB148");

			} else {
				fav.setImageResource(R.drawable.bookmark_fill);

				Constants.chalngeImageColor(fav,"#663d33");
			}

			db.close();
			if(heaerMain.getVisibility()==View.VISIBLE) {
                heaerMain.setVisibility(View.GONE);
            }
		}

	}
	@Override
	public void loadComplete(int nbPages) {

	}

	@Override
	public void onBackPressed()
	{
		finish();
	}
}