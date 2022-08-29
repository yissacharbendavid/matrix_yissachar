package il.co.matrix_yissachar.repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import il.co.matrix_yissachar.model.BenefitsResponse;
import il.co.matrix_yissachar.model.DataObject;

public class BenefitsRepository {

    static String jsonString;
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();

    public DataObject getJsonFromAssets(Context context, String fileName) {

        DataObject dataObject = new DataObject();

        try {
            InputStream inputstream = context.getAssets().open(fileName);
            int size = inputstream.available();
            byte[] buffer = new byte[size];
            inputstream.read(buffer);
            inputstream.close();

            jsonString = new String(buffer, "UTF-8");

            FileOutputStream outputStream = context.openFileOutput("savedBenefits.json",Context.MODE_PRIVATE);
            outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
            outputStream.close();

            BenefitsResponse benefits = gson.fromJson(jsonString, BenefitsResponse.class);
            dataObject = benefits.DataObject;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataObject;
    }

    public DataObject getLocalJson(Context context){

        DataObject dataObject = new DataObject();
        Log.d("BenefitRepository","no internet mode");
        try {
            FileInputStream inputStream = context.openFileInput("savedBenefits.json");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, "UTF-8");
            BenefitsResponse benefits = gson.fromJson(jsonString, BenefitsResponse.class);
            dataObject = benefits.DataObject;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataObject;
    }
}
