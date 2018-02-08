package com.example.sergei.changelocationmodule3;

import junit.framework.Assert;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

//import clientGeo.GeoObject;
//import clientGeo.GeoPoint;
//import clientGeo.GeocoderResponse;
//import clientGeo.YaGeocoder;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by sergei on 07.02.18.
 */

public class TestBox {
    public void testDirectGeocode() throws Exception {
        String str = null;

        try {
            URL url = new URL("https://geocode-maps.yandex.ru/1.x/?geocode=E134.854,S25.828");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            str = connection.getInputStream().toString();
            //res = BitmapFactory.decodeStream((connection.getInputStream()));
        }  catch (IOException e) {
            e.printStackTrace();
        }
//        java.net.URL url = new URL("https://gweb-earth.storage.googleapis.com/assets/earth_desktop.jpg");
//        HttpsURLConnection connection = new HttpsURLConnection() {
//            @Override
//            public String getCipherSuite() {
//                return null;
//            }
//
//            @Override
//            public Certificate[] getLocalCertificates() {
//                return new Certificate[0];
//            }
//
//            @Override
//            public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
//                return new Certificate[0];
//            }
//
//            @Override
//            public void disconnect() {
//
//            }
//
//            @Override
//            public boolean usingProxy() {
//                return false;
//            }
//
//            @Override
//            public void connect() throws IOException {
//
//            }
//        };


//        YaGeocoder geocoder = new YaGeocoder(new DefaultHttpClient());
//        String request = "Москва, Льва Толстого 16";
//        GeocoderResponse response = geocoder.directGeocode(request);
//
//        Assert.assertEquals(request, response.getRequest());
//        Assert.assertEquals(1, response.getGeoObjects().size());
//        GeoObject geoObject = response.getGeoObjects().get(0);
//        Assert.assertEquals("улица Льва Толстого, 16", geoObject.getName());
//        Assert.assertEquals("Москва, улица Льва Толстого, 16", geoObject.getAddress());
//        Assert.assertEquals("улица Льва Толстого", geoObject.getThoroughfare());
//        Assert.assertEquals("16", geoObject.getPremise());
//        Assert.assertEquals(new GeoPoint(37.587937, 55.733771), geoObject.getPoint());

    }
}
