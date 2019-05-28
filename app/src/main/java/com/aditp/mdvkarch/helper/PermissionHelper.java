package com.aditp.mdvkarch.helper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import com.aditp.mdvkarch.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class PermissionHelper {

    // ------------------------------------------------------------------------
    // LOCATION
    // ------------------------------------------------------------------------
    public static void requestLocation(AppCompatActivity context) {
        Dexter.withActivity(context)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showMessageWarning(context, "Kami memerlukan Akses Lokasi .");
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    // ------------------------------------------------------------------------
    // STORAGE
    // ------------------------------------------------------------------------
    public static void requestStorage(AppCompatActivity context) {
        Dexter.withActivity(context)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showMessageWarning(context, "Kami memerlukan Akses Storage.");
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    // ------------------------------------------------------------------------
    // CAMERA
    // ------------------------------------------------------------------------
    public static void requestCamera(AppCompatActivity context) {
        Dexter.withActivity(context)
                .withPermissions(Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showMessageWarning(context, "Kami memerlukan Akses ke kamera .");
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private static void showMessageWarning(Context context, String msg) {
        MDVKHelper.DIALOG_HELPER.showCustomDialog(context,
                "Permissions required!", msg,
                new MDVKHelper.ActionDialogListener() {
                    @Override
                    public void executeNo() {

                    }

                    @Override
                    public void executeYes() {
                        openSettings(context);
                    }
                });
    }


    private static void openSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public interface PermissionListener {
        void onPermissionGranted();
    }
}
