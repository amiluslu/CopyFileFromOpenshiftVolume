package com.amilus.filecopy.service;


import okhttp3.*;
import org.apache.http.entity.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Service
public class FileCopyService {

    private Logger fileCopyServiceLogger = LogManager.getLogger(FileCopyService.class);

    @Value("${ocstorage.ocdirectory}")
    private String folderPath;

    @Value("${$storage-file-app.url}")
    private String storageFileAppUrl;

    public boolean copyFile(String file) throws IOException {
        fileCopyServiceLogger.info("Searching file with name: "+file);
        File directory = new File(folderPath);
        File [] f =  directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(file) && name.endsWith(".mp4");
            }
        });

        if( f.length<1){
            fileCopyServiceLogger.error("File NOT FOUND !!");
            return false;
        }
        else {
            int uploadProcessCode = uploadVideoToStorage(f[0]);
            if(uploadProcessCode >= 200 && uploadProcessCode <= 299){
                fileCopyServiceLogger.info("Video file successfully UPLOADED to storage Disk..");
                fileCopyServiceLogger.info("Now, video file is deleting from openshift volume..");
                return deleteVideoFromOpenshift(f[0]);
            }
            else {
                return false;
            }
        }
    }

    public boolean copyAllVideos() throws IOException {
        File directory = new File(folderPath);
        File [] f =  directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".mp4");
            }
        });

        if(f.length<1){
            fileCopyServiceLogger.warn("There is NO FILE ends with .mp4");
            return false;
        }
        else {
            boolean result = false;
            for (int i = 0; i < f.length ; i++) {
                int uploadProcessCode = uploadVideoToStorage(f[i]);
                if(uploadProcessCode >= 200 && uploadProcessCode <= 299){
                    fileCopyServiceLogger.info("Video file: "+f[i].getName()+" successfully UPLOADED to storage Disk..");
                    fileCopyServiceLogger.info("Now, video file is deleting from openshift volume..");
                    result = deleteVideoFromOpenshift(f[i]);
                }
            }
            return result;
        }
    }

    public int uploadVideoToStorage(File f) throws IOException {
        String videoFileName = "zalenium_Test_test_Linux_Chrome_20201203202020_FAILED.mp4";

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("videoFile",videoFileName,
                        RequestBody.create(MediaType.parse("multipart/form-data"),
                                new File(f.getAbsolutePath())))
                .build();
        String url = storageFileAppUrl + "videos/"+videoFileName;
        fileCopyServiceLogger.info("Storage File App Service URL: "+url);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        fileCopyServiceLogger.info("Response : "+response);
        response.close();
        return response.code();
    }

    public boolean deleteVideoFromOpenshift(File file){
        if(file.delete()){
            fileCopyServiceLogger.info(file.getName() + " is deleted from openshift volume successfully..");
            return true;
        }
        else
        {
            fileCopyServiceLogger.error(file.getName() + " CAN NOT DELETED FROM OPENSHIFT VOLUME !!");
            return false;
        }
    }
}
