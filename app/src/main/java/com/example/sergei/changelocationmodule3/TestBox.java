package com.example.sergei.changelocationmodule3;

import junit.framework.Assert;

import clientGeo.GeoObject;
import clientGeo.GeoPoint;
import clientGeo.GeocoderResponse;
import clientGeo.YaGeocoder;

/**
 * Created by sergei on 07.02.18.
 */

public class TestBox {

    public void testDirectGeocode() throws Exception {

        YaGeocoder geocoder = new YaGeocoder(new DefaultHttpClient());
        String request = "Москва, Льва Толстого 16";
        GeocoderResponse response = geocoder.directGeocode(request);

        Assert.assertEquals(request, response.getRequest());
        Assert.assertEquals(1, response.getGeoObjects().size());
        GeoObject geoObject = response.getGeoObjects().get(0);
        Assert.assertEquals("улица Льва Толстого, 16", geoObject.getName());
        Assert.assertEquals("Москва, улица Льва Толстого, 16", geoObject.getAddress());
        Assert.assertEquals("улица Льва Толстого", geoObject.getThoroughfare());
        Assert.assertEquals("16", geoObject.getPremise());
        Assert.assertEquals(new GeoPoint(37.587937, 55.733771), geoObject.getPoint());

    }
}
