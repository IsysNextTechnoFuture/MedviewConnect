package com.isysnext.medviewmd.medviewconnect.doctor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.isysnext.medviewmd.medviewconnect.modelDr.PdfDocActivityDTO;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.adapterDr.PdfDocFileAdapter;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harsh on 06/05/18.
 */
public class PdfDocViewActivity extends AppCompatActivity implements AppConstants {
    /*Declaration of variables*/
    private Context context;
    AppSession appSession;
    Utilities utilities;
    ListView lvFile;
    TextView tvBlankList;
    private Toolbar toolbar;
    PdfDocFileAdapter pdfDocFileAdapter;
    private ArrayList<File> fileList = new ArrayList<File>();
    private ArrayList<String> mineTypeList = new ArrayList<String>();
    private File root;
    private String type = "", imageType = "", imagePath = "", imagePathVehicle = "", title = "", strFilePath = "",
        mimeType="";
    private List<PdfDocActivityDTO> strFileList;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dr_activity_pdf_doc_view);
        context = this;
        appSession = new AppSession(this);
        utilities = Utilities.getInstance(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar_splash);
        setSupportActionBar(toolbar);
        initToolBar();
        initView();
        setValues();
    }

    /*
     ****Set Toolbar*/
    private void initToolBar() {
        ActionBar actionBar = ((PdfDocViewActivity) context).getSupportActionBar();
        actionBar.show();
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(getResources().getColor(R.color.blue));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.back_arrow_left));
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(((PdfDocViewActivity) context).getResources().getString(R.string.Attach_file));
        Spannable text = new SpannableString(actionBar.getTitle());
        text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)),
                0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(text);
    }

    /*Method for initializing view*/
    private void initView() {
        lvFile = (ListView) findViewById(R.id.lv_file);
        tvBlankList = (TextView) findViewById(R.id.tv_blank_list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(getClass().getName(), "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra(FILE_PATH, "");
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*Method for setting values*/
    private void setValues() {
        strFileList = new ArrayList<>();
        //getting SDcard root path
        root = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath());
        getfile(root);
        getStrFileList();

        if (fileList.size() == 0) {
            tvBlankList.setVisibility(View.VISIBLE);
        } else {
            pdfDocFileAdapter = new PdfDocFileAdapter(context, R.layout.dr_list_item_pdf_doc_activity, strFileList);
            lvFile.setAdapter(pdfDocFileAdapter);
        }
        lvFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                strFilePath = fileList.get(position).getPath();
                Intent intent = new Intent();
                intent.putExtra(FILE_PATH, strFilePath);
                intent.putExtra(MINE_TYPE,mimeType);
                setResult(Activity.RESULT_OK, intent);
                finish();//finishing activity
            }
        });
    }

    /*Method for preparing list of files*/
    public ArrayList<File> getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for(int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    //fileList.add(listFile[i]);
                    getfile(listFile[i]);
                } else {
                    Uri selectedUri = Uri.fromFile(listFile[i]);
                    String fileExtension
                            = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                    mimeType
                            = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
                    fileList.add(listFile[i]);
                }
            }
        }
        return fileList;
    }

    /*Method for getting string list of file name and path*/
    private void getStrFileList() {
        if (fileList.size() > 0) {
            for (int i = 0; i < fileList.size(); i++) {
                strFileList.add(new PdfDocActivityDTO(fileList.get(i).getName()));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra(FILE_PATH, "");
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
