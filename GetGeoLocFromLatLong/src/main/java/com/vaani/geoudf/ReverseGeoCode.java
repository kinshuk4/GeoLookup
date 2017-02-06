
package com.vaani.geoudf;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.vaani.geoudf.kdtree.KDTree;


public class ReverseGeoCode {
    KDTree<GeoName> kdTree;

    // Get placenames from http://download.geonames.org/export/dump/



    private static class Holder {
        static final ReverseGeoCode INSTANCE = new ReverseGeoCode();
    }


    private ReverseGeoCode(){

    }

    public static ReverseGeoCode getInstance() {
        return Holder.INSTANCE;
    }
    public  boolean isInit(){
    	return kdTree!=null;
    }
    public  void init(){
        if(kdTree==null){

            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream is = loader.getResourceAsStream("./EU.txt");
            try {
                createKdTree(is);
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }

    }



    private void createKdTree(InputStream placenames)
            throws IOException {
        ArrayList<GeoName> arPlaceNames;
        arPlaceNames = new ArrayList<GeoName>();
        // Read the geonames file in the directory
        BufferedReader in = new BufferedReader(new InputStreamReader(placenames , "UTF-8"));
        String str;
        try {
            while ((str = in.readLine()) != null) {
                GeoName newPlace = new GeoName(str);
                arPlaceNames.add(newPlace);
            }
        } catch (IOException ex) {
            throw ex;
        }finally{
            in.close();
        }
        kdTree = new KDTree<GeoName>(arPlaceNames);
    }

    public GeoName nearestPlace(double latitude, double longitude) {
        return kdTree.findNearest(new GeoName(latitude,longitude));
    }

    public GeoName nearestPlace(String latitude, String longitude) {
        double lat = Double.parseDouble(latitude);
        double lng = Double.parseDouble(longitude);
        return kdTree.findNearest(new GeoName(lat,lng));
    }
}