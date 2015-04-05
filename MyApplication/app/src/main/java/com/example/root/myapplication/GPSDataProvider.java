package com.example.root.myapplication;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by buddhima on 3/2/15.
 */
public class GPSDataProvider {

    GoogleApiClient client;
    Location location;

    /*
    It is assumed that this will get a LocationClient
     */
    GPSDataProvider(GoogleApiClient client){
        this.client = client;
        this.location = LocationServices.FusedLocationApi.getLastLocation(client);
    }

    public double getCurrentLongitude(){
        return location.getLongitude();
    }

    public double getCurrentLatitude() {
        return location.getLatitude();
    }

    /**
     * Distance in meters from current position
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public int getDistanceFormCurrentLocation(double longitude, double latitude){
        double currentLongitude = this.getCurrentLongitude();
        double currentLatitude = this.getCurrentLatitude();

        GeodataCalculator gdc = this.new GeodataCalculator();

        double distance = gdc.distance(latitude, longitude, currentLatitude, currentLongitude, 'M');

        return (int)distance;
    }



    private class GeodataCalculator {
/*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::                                                                         :*/
/*::  This routine calculates the distance between two points (given the     :*/
/*::  latitude/longitude of those points). It is being used to calculate     :*/
/*::  the distance between two locations using GeoDataSource (TM) prodducts  :*/
/*::                                                                         :*/
/*::  Definitions:                                                           :*/
/*::    South latitudes are negative, east longitudes are positive           :*/
/*::                                                                         :*/
/*::  Passed to function:                                                    :*/
/*::    lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)  :*/
/*::    lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)  :*/
/*::    unit = the unit you desire for results                               :*/
/*::           where: 'M' is meters                                          :*/
/*::                  'K' is kilometers                                      :*/
/*::                  'N' is nautical miles & default miles                  :*/
/*::  Worldwide cities and other features databases with latitude longitude  :*/
/*::  are available at http://www.geodatasource.com                          :*/
/*::                                                                         :*/
/*::  For enquiries, please contact sales@geodatasource.com                  :*/
/*::                                                                         :*/
/*::  Official Web site: http://www.geodatasource.com                        :*/
/*::                                                                         :*/
/*::           GeoDataSource.com (C) All Rights Reserved 2015                :*/
/*::                                                                         :*/
/*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/

        public double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
            double theta = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            if ('K' == (unit)) {
                dist = dist * 1.609344;
            } else if ('M' == unit){
                dist = dist * 1.609344 * 1000;
            }else if ('N' == unit) {
                dist = dist * 0.8684;
            }
            return (dist);
        }

        /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::  This function converts decimal degrees to radians             :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
        private double deg2rad(double deg) {
            return (deg * Math.PI / 180.0);
        }

        /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::  This function converts radians to decimal degrees             :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
        private double rad2deg(double rad) {
            return (rad * 180 / Math.PI);
        }

    }
}
