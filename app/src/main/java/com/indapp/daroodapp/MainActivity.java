package com.indapp.daroodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.animation.ActivityAnimator;
import com.fonts.AlviNastaLiqBold;
import com.utils.Constants;
import com.utils.NavDrawerItem;
import com.utils.NavDrawerListAdapter;

import java.util.ArrayList;

public class MainActivity extends Activity {

    LinearLayout contentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Constants.sp=getSharedPreferences(Constants.prefName, Activity.MODE_PRIVATE);
        Constants.editor= Constants.sp.edit();

        findViewById(R.id.iv_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(Gravity.START);
            }
        });
        contentLayout=(LinearLayout)findViewById(R.id.contentLayout);
        resetIndex();
        initSideMenu();

    }
    public void resetIndex()
    {
        for(int i=0;i<Constants.INDEX_URDU.length;i++)
        {
            View view=View.inflate(getApplicationContext(),R.layout.inflate_checklist_items_urdu,null);
            AlviNastaLiqBold txtTitle1=(AlviNastaLiqBold)view.findViewById(R.id.txtTitle1);
            txtTitle1.setText(""+Constants.INDEX_URDU[i]);
            view.setOnClickListener(new ItemClickListener(i));
            contentLayout.addView(view);
        }
    }
    public class ItemClickListener implements android.view.View.OnClickListener
    {
        int position;
        ItemClickListener(int position)
        {
            this.position=position;
        }
        public void onClick(View view)
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setClass(MainActivity.this,CurlActivity.class);
            intent.putExtra("page", Constants.INDEX[position]-1);
            startActivityForResult(intent,1000);
            ActivityAnimator anim = new ActivityAnimator();

            try {
                anim.getClass().getMethod("appearTopLeft" + "Animation", Activity.class).invoke(anim, MainActivity.this);
            }
            catch(Exception e ){

            }
        }
    }


    DrawerLayout drawer_layout;
    public void initSideMenu()
    {
        drawer_layout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ListView mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        final ArrayList navDrawerItems = new ArrayList<NavDrawerItem>();
//        navDrawerItems.add(new NavDrawerItem("Haj & Umrah Dua's"));
        //navDrawerItems.add(new NavDrawerItem("Haj & Umrah Guide"));
        //navDrawerItems.add(new NavDrawerItem("Ziyarat in baghdad"));
//        navDrawerItems.add(new NavDrawerItem("Visit Website",R.drawable.deleie_icon));
        navDrawerItems.add(new NavDrawerItem("Apni Baat",R.drawable.deleie_icon));
        navDrawerItems.add(new NavDrawerItem("Resume",R.drawable.deleie_icon));
        navDrawerItems.add(new NavDrawerItem("Share App",R.drawable.deleie_icon));
        navDrawerItems.add(new NavDrawerItem("Rate App",R.drawable.deleie_icon));
        navDrawerItems.add(new NavDrawerItem("About Developer",R.drawable.deleie_icon));
        navDrawerItems.add(new NavDrawerItem("Exit",R.drawable.deleie_icon));



        NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NavDrawerItem nav= (NavDrawerItem)navDrawerItems.get(position);
                String TITLE=nav.title;

                if(TITLE.equalsIgnoreCase("About Developer")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setClass(MainActivity.this, AboutDeveloper.class);
                    startActivityForResult(intent, 500);
                    ActivityAnimator anim = new ActivityAnimator();

                    try {
                        anim.getClass().getMethod("appearTopLeft" + "Animation", Activity.class).invoke(anim, MainActivity.this);
                    } catch (Exception e) {

                    }
                }
                else if(TITLE.equalsIgnoreCase("Apni Baat"))
                {
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setClass(MainActivity.this,CurlActivity.class);
                    intent.putExtra("page", 2);
                    startActivityForResult(intent,1000);
                    ActivityAnimator anim = new ActivityAnimator();
                    try {
                        anim.getClass().getMethod("appearTopLeft" + "Animation", Activity.class).invoke(anim, MainActivity.this);
                    }
                    catch(Exception e ){
                    }
                }
                else if(TITLE.equalsIgnoreCase("Resume"))
                {
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setClass(MainActivity.this,CurlActivity.class);
                    intent.putExtra("page", Constants.sp.getInt("lastread", 0)-1);
                    startActivityForResult(intent,1000);
                    ActivityAnimator anim = new ActivityAnimator();

                    try {
                        anim.getClass().getMethod("appearTopLeft" + "Animation", Activity.class).invoke(anim, MainActivity.this);
                    }
                    catch(Exception e ){

                    }
                }
                else if(TITLE.equalsIgnoreCase("Visit Website"))
                {
//                    Intent intent=new Intent(Intent.ACTION_VIEW);
//                    intent.setClass(MainActivity.this,VisitWebsite.class);
//                    startActivityForResult(intent,100);
                }
                else if(TITLE.equalsIgnoreCase("Share App"))
                {
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setType("text/plain");
//                    intent.putExtra(Intent.EXTRA_TEXT, Constants.share_data);
//                    startActivity(intent);

                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());


                    Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.shareimage);
                    String path = getExternalCacheDir()+"/shareimage.jpg";
                    java.io.OutputStream out = null;
                    java.io.File file=new java.io.File(path);
                    try { out = new java.io.FileOutputStream(file); bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); out.flush(); out.close(); } catch (Exception e) { e.printStackTrace(); } path=file.getPath();
                    Uri bmpUri = Uri.parse("file://"+path);

                    Intent shareIntent = new Intent(); shareIntent = new Intent(android.content.Intent.ACTION_SEND); shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri); shareIntent.setType("image/jpg");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, Constants.share_data);
                    startActivity(Intent.createChooser(shareIntent,"Share with"));

                }
                else if(TITLE.equalsIgnoreCase("Rate App"))
                {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Constants.RATE_ID)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?" + Constants.RATE_ID)));
                    }
                }
                else if(TITLE.equalsIgnoreCase("Exit"))
                {
                    finish();
                }
                drawer_layout.closeDrawer(Gravity.START);
            }
        });
    }

}
