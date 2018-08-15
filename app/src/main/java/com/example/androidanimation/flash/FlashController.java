package com.example.androidanimation.flash;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.*;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Surface;

import java.util.Collections;
import java.util.Objects;

import static android.Manifest.permission.CAMERA;

public class FlashController {

    private static final int REQUEST_CAMERA_PERMISSION = 2;

    private boolean mFlashSupported;
    private boolean mTorchOn;
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mPreviewBuilder;
    private CameraCaptureSession mPreviewSession;
    private SurfaceTexture mTexture;

    public void init(Context context) {
        CameraManager manager = Objects.requireNonNull((CameraManager)context.getSystemService(Context.CAMERA_SERVICE));
        try {
            String mCameraId = manager.getCameraIdList()[0];
            CameraCharacteristics mCharacteristics = manager.getCameraCharacteristics(mCameraId);

            Boolean available = mCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            mFlashSupported = available == null ? false : available;

            manager.openCamera(mCameraId, new CameraDevice.StateCallback() {
                        @Override
                        public void onOpened(@NonNull CameraDevice cameraDevice) {
                            mCameraDevice = cameraDevice;
                            startPreview();
                        }

                        @Override
                        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                            cameraDevice.close();
                            mCameraDevice = null;
                        }

                        @Override
                        public void onError(@NonNull CameraDevice cameraDevice, int error) {
                            cameraDevice.close();
                            mCameraDevice = null;
                        }
                    }, null
            );
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void release() {
        closeCamera();
    }

    public boolean checkPermissions(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[] {CAMERA}, REQUEST_CAMERA_PERMISSION);
    }

    public boolean isFlashSupported() {
        return mFlashSupported;
    }

    public boolean isTorchOn() {
        return mTorchOn;
    }

    public void turnFlashOn() {
        try {
            mPreviewBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH);
            mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null, null);
            mTorchOn = true;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void turnFlashOff() {
        try {
            mPreviewBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF);
            mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null, null);
            mTorchOn = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        closePreviewSession();
        if (null != mCameraDevice) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }

    private void closePreviewSession() {
        if (mPreviewSession != null) {
            mTexture.release();
            mPreviewSession.close();
            mPreviewSession = null;
        }
    }

    private void startPreview() {
        if (null == mCameraDevice) {
            return;
        }

        try {
            closePreviewSession();
            mTexture = new SurfaceTexture(123);//mTextureView.getSurfaceTexture();
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

            Surface previewSurface = new Surface(mTexture);
            mPreviewBuilder.addTarget(previewSurface);

            mCameraDevice.createCaptureSession(Collections.singletonList(previewSurface),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession session) {
                            mPreviewSession = session;
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                            Log.e("FlashController", "Configuration failed");
                        }
                    }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
