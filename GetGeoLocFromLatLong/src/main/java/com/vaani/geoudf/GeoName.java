package com.vaani.geoudf;

import java.util.Arrays;
import java.util.Comparator;

import com.vaani.geoudf.kdtree.KDNodeComparator;

import static java.lang.Math.*;



public class GeoName extends KDNodeComparator<GeoName> {

    public String city;
    public String state;
    public String zip;
    public double latitude;
    public double longitude;
    public double point[] = new double[3]; // The 3D coordinates of the point

    GeoName(String data) {
        String[] names = data.split("\t");
        state = names[3];
        city = names[2];
        zip = names[1];

        latitude = Double.parseDouble(names[9]);
        longitude = Double.parseDouble(names[10]);
        setPoint();
    }

    GeoName(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        setPoint();
    }

    private void setPoint() {
        point[0] = cos(toRadians(latitude)) * cos(toRadians(longitude));
        point[1] = cos(toRadians(latitude)) * sin(toRadians(longitude));
        point[2] = sin(toRadians(latitude));
    }


    public String toString() {
        return "GeoName [city=" + city + ", latitude=" + latitude
                + ", longitude=" + longitude + ", point="
                + Arrays.toString(point) + "]";
    }

    protected double squaredDistance(GeoName other) {
        double x = this.point[0] - other.point[0];
        double y = this.point[1] - other.point[1];
        double z = this.point[2] - other.point[2];
        return (x*x) + (y*y) + (z*z);
    }


    protected double axisSquaredDistance(GeoName other, int axis) {
        double distance = point[axis] - other.point[axis];
        return distance * distance;
    }


    protected Comparator<GeoName> getComparator(int axis) {
        return GeoNameComparator.values()[axis];
    }

    protected static enum GeoNameComparator implements Comparator<GeoName> {
        x {
            public int compare(GeoName a, GeoName b) {
                return Double.compare(a.point[0], b.point[0]);
            }
        },
        y {

            public int compare(GeoName a, GeoName b) {
                return Double.compare(a.point[1], b.point[1]);
            }
        },
        z {

            public int compare(GeoName a, GeoName b) {
                return Double.compare(a.point[2], b.point[2]);
            }
        }
    }
}
