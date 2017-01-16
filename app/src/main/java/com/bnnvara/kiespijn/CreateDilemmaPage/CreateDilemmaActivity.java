package com.bnnvara.kiespijn.CreateDilemmaPage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.bnnvara.kiespijn.ArticleSearchPage.ArticleSearchFragment;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.SingleFragmentActivity;

public class CreateDilemmaActivity extends SingleFragmentActivity {

    private static final String DILEMMA_OBJECT = "dilemma_object";

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context) {
        return new Intent(context, CreateDilemmaActivity.class);
    }


    @Override
    protected Fragment createFragment() {
        Intent i = getIntent();
        Dilemma dilemma = (Dilemma) i.getSerializableExtra(DILEMMA_OBJECT);
        return CreateDilemmaFragment.newInstance(dilemma);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();

            closeDilemma();

        } else {
            getFragmentManager().popBackStack();
        }
    }

    private void closeDilemma() {
        AlertDialog.Builder closeDilemmaAlert = new AlertDialog.Builder(getBaseContext());
        closeDilemmaAlert.setMessage("Are you sure you want to exit? ");
        closeDilemmaAlert.setCancelable(false);

        closeDilemmaAlert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();

            }
        });

        closeDilemmaAlert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        closeDilemmaAlert.show();
    }
}
