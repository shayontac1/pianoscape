package piano.pianotrainer.fragments;

import android.app.AlertDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import piano.pianotrainer.R;

/**
 * Created by Matthew on 11/24/2017.
 */

public class MusicDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.music_dialog)
                .setItems(R.array.musicDialogOptions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bundle = getArguments();
                        String filename = bundle.getString("filename","");
                        String rootpath = bundle.getString("xmlFilePath","");

                        if (which == 2) {
                            try {
                                // open text editor
                                Intent intent = new Intent(Intent.ACTION_EDIT);
                                String path = rootpath + filename + ".xml";
                                File file = new File(path);
                                Log.d("MusicDialogFragment", file.getName());
                                Uri uri = Uri.parse("file://" + path);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                                intent.setDataAndType(uri, "text/xml");

                                PackageManager packageManager = getActivity().getPackageManager();
                                List activities = packageManager.queryIntentActivities(intent,
                                        PackageManager.MATCH_DEFAULT_ONLY);
                                boolean isIntentSafe = activities.size() > 0;
                                if (isIntentSafe) {
                                    getActivity().startActivity(intent);
                                } else {
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(getContext(), "No Application can open this file.", duration);
                                    toast.show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        return builder.create();
    }
}
