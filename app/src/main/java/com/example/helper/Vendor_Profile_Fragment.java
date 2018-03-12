package com.example.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helper.models.ProfileData;
import com.example.helper.models.ProfileModelClass;
import com.example.helper.models.VendorResponseBean;
import com.example.helper.utils.MySharedData;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class Vendor_Profile_Fragment extends Fragment implements View.OnClickListener
{
    TextView vendor_name, vendor_profile;
    Button submit_data;
    View view;
    String name, profile,encodedimages,encodedimages2,vendor_Image;
    RecyclerView Idproof_recycler, workPhoto_recycler;
    List<Bitmap> contentImageList = new ArrayList<>();
    List<Bitmap> workImagesList = new ArrayList<>();
    String imagetype = "";
    int vendorid;
    String lattitude,longitude,vendor_id,profile_pic;
    List<String> idproofs = new ArrayList<>();
    List<String> work_photos = new ArrayList<>();
    ImageView camera;
    Dialog dialog_image;
    Bitmap thumnail1;
    CircleImageView vendor_image;
    Vendor_Register_Interface api_interface;
    ProfileData profileData;
    ByteArrayOutputStream baos;

    private OnFragmentInteractionListener mListener;

    public Vendor_Profile_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vendor__profile_, container, false);
        vendor_name = view.findViewById(R.id.vendor_name);
        vendor_profile = view.findViewById(R.id.service_name);
        submit_data = view.findViewById(R.id.submit);
        submit_data.setOnClickListener(this);
        camera = view.findViewById(R.id.camera);
        contentImageList.add(BitmapFactory.decodeResource(getResources(), R.drawable.addimage));
        workImagesList.add(BitmapFactory.decodeResource(getResources(), R.drawable.addimage));
        Idproof_recycler =  view.findViewById(R.id.recyclerView_ID_Proof);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        Idproof_recycler.setLayoutManager(layoutManager);
        Idproof_recycler.setAdapter(new ID_ContentAdapter());
        workPhoto_recycler =  view.findViewById(R.id.recyclerView_workphoto);
        workPhoto_recycler.setLayoutManager(layoutManager2);
        workPhoto_recycler.setAdapter(new workphoto_ContentAdapter());
        name = MySharedData.getGeneralSaveSession("vendor_name");
        profile = MySharedData.getGeneralSaveSession("vendor_profile");
        vendor_name.setText(name);
        vendor_profile.setText(profile);
        vendor_image = view.findViewById(R.id.vendor_image);
        camera.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) return 1;
        else return k;
    }

    @Override
    public void onClick(View v)
    {
        if (v==camera)
        {
            selectVendorImage();
        }
        if (v==submit_data)
        {
            lattitude = MySharedData.getGeneralSaveSession("Lattitude");
            longitude = MySharedData.getGeneralSaveSession("Longitude");
            vendor_id = MySharedData.getGeneralSaveSession("vendor_id");
            vendorid = Integer.parseInt(vendor_id);
//            profile_pic = MySharedData.getGeneralSaveSession("vendor_image");
            profileData = new ProfileData(vendorid,idproofs,work_photos,profile_pic,lattitude,longitude);


//            Toast.makeText(getActivity(), "id is" +vendorid, Toast.LENGTH_SHORT).show();
            upload_to_Api(profileData);
        }

    }

    public class ID_ContentAdapter extends RecyclerView.Adapter<ID_ContentAdapter.MyViewHolder>

    {
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView id_Image, id_image_delete;

            public MyViewHolder(View itemView) {
                super(itemView);
                id_Image = itemView.findViewById(R.id.content_image);
                id_image_delete = itemView.findViewById(R.id.content_image_delete);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view1 = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.idproof_customview, parent, false);
            return new MyViewHolder(view1);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.id_Image.setImageBitmap(contentImageList.get(position));
            holder.id_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == contentImageList.size() - 1) {
                        imagetype = "ID";
                        selectImage();
                    }
                }
            });
            if (position == contentImageList.size() - 1)
                holder.id_image_delete.setVisibility(View.GONE);
            else holder.id_image_delete.setVisibility(View.VISIBLE);
            holder.id_image_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contentImageList.remove(position);
//                    contentFileName.remove(position);
                    idproofs.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return contentImageList.size();
        }
    }


    public class workphoto_ContentAdapter extends RecyclerView.Adapter<workphoto_ContentAdapter.MyViewHolder> {
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView workPhoto, workPhoto_delete;

            public MyViewHolder(View itemView) {
                super(itemView);
                workPhoto = (ImageView) itemView.findViewById(R.id.content_image);
                workPhoto_delete = (ImageView) itemView.findViewById(R.id.content_image_delete);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view2 = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.idproof_customview, parent, false);
            return new workphoto_ContentAdapter.MyViewHolder(view2);

        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.workPhoto.setImageBitmap(workImagesList.get(position));
            holder.workPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == workImagesList.size() - 1) {
                        imagetype = "work";
                        selectImage();
                    }
                }
            });
            if (position == workImagesList.size() - 1)
                holder.workPhoto_delete.setVisibility(View.GONE);
            else holder.workPhoto_delete.setVisibility(View.VISIBLE);
            holder.workPhoto_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    workImagesList.remove(position);
                    //workpicsFile.remove(position);
                    work_photos.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return workImagesList.size();
        }


    }

    public void selectImage() {
        dialog_image = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog_image.setContentView(R.layout.imagepicker);
        dialog_image.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        final TextView imgcamera = (TextView) dialog_image.findViewById(R.id.img_camera);
        final TextView imggallery = (TextView) dialog_image.findViewById(R.id.img_gallery);
        final ImageView close = (ImageView) dialog_image.findViewById(R.id.close_thank_you);
        dialog_image.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_image.dismiss();

            }
        });

        imgcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
                dialog_image.dismiss();
            }
        });
        imggallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, 2);
                dialog_image.dismiss();
            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            if (imagetype.equals("ID"))
            {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                contentImageList.add(contentImageList.size() - 1, imageBitmap);
                baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                encodedimages = Base64.encodeToString(b , Base64.DEFAULT);

                idproofs.add(encodedimages);
                Idproof_recycler.getAdapter().notifyDataSetChanged();
            }
            if (imagetype.equals("work"))
            {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                workImagesList.add(workImagesList.size() - 1,imageBitmap);
                baos  = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                encodedimages2 = Base64.encodeToString(b , Base64.DEFAULT);
                work_photos.add(encodedimages2);
                workPhoto_recycler.getAdapter().notifyDataSetChanged();
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            if (imagetype.equals("ID")) {
                Uri selectedImage = data.getData();
                try
                {
                    thumnail1 = getThumbnail(selectedImage);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                contentImageList.add(contentImageList.size() - 1, thumnail1);
                baos  = new ByteArrayOutputStream();
                thumnail1.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                encodedimages = Base64.encodeToString(b , Base64.DEFAULT);
                idproofs.add(encodedimages);
                Idproof_recycler.getAdapter().notifyDataSetChanged();
            }
            if (imagetype.equals("work")) {
                Uri selectedImage = data.getData();
                try
                {
                    thumnail1 = getThumbnail(selectedImage);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                workImagesList.add(workImagesList.size() - 1, thumnail1);
                baos  = new ByteArrayOutputStream();
                thumnail1.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                encodedimages2 = Base64.encodeToString(b , Base64.DEFAULT);
                work_photos.add(encodedimages2);
                workPhoto_recycler.getAdapter().notifyDataSetChanged();
            }
        }
        if (requestCode == 11 && resultCode == RESULT_OK && data != null)
        {
            Bitmap vendorimage = (Bitmap) data.getExtras().get("data");
            vendor_image.setImageBitmap(vendorimage);
            baos  = new ByteArrayOutputStream();
            vendorimage.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            profile_pic = Base64.encodeToString(b , Base64.DEFAULT);
//            MySharedData.setGeneralSaveSession("vendor_image",encodedimage3);
        }
        if (requestCode == 12 && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            try
            {
                thumnail1 = getThumbnail(selectedImage);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            vendor_image.setImageURI(selectedImage);
            baos  = new ByteArrayOutputStream();
            thumnail1.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            profile_pic = Base64.encodeToString(b , Base64.DEFAULT);
//            MySharedData.setGeneralSaveSession("vendor_image",encodedimage3);
        }
    }

    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException
    {
        InputStream input = getActivity().getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;
        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
        double ratio = (originalSize > 100) ? (originalSize / 100) : 1.0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        input = getActivity().getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    public void selectVendorImage()
    {
        dialog_image = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog_image.setContentView(R.layout.imagepicker);
        dialog_image.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        final TextView imgcamera = (TextView) dialog_image.findViewById(R.id.img_camera);
        final TextView imggallery = (TextView) dialog_image.findViewById(R.id.img_gallery);
        final ImageView close = (ImageView) dialog_image.findViewById(R.id.close_thank_you);
        dialog_image.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_image.dismiss();

            }
        });

        imgcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 11);
                dialog_image.dismiss();
            }
        });
        imggallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, 12);
                dialog_image.dismiss();
            }
        });

    }

    public void upload_to_Api(ProfileData profileData)
    {
        api_interface = Vendor_Home_API_Client.getApiClient().create(Vendor_Register_Interface.class);
        Call<ProfileData> call = api_interface.sendData(profileData);
        call.enqueue(new Callback<ProfileData>()
        {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body().getStatus()==200){
//                       Log.d("Response Api", "Response"+response.body().getLattitude() );
                        Toast.makeText(getActivity(), "Response", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        vendor_Image = response.body().getProfilePic();
                        MySharedData.setGeneralSaveSession("vendor_image",vendor_Image);

                    }

                }
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t)
            {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
