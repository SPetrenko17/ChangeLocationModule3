package clientGeo;

import android.preference.PreferenceActivity;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @author Dmitry Andreev <a href="mailto:AndreevDm@yandex-team.ru"/>
 * @date 08.04.2014
 */
public class YaGeocoder {

    private static final String GEOCODER_HOST = "https://geocode-maps.yandex.ru/1.x/";
    private static final int HTTP_OK = 200;

    private XmlResponseParser responseParser = new XmlResponseParser();
//ОГО ЭТОТ КОД СЛИШКОМ ДРЕВНИЙ И НАДО МЕНЯТЬ ВС НА URL CONNECTION
    private URLConnection httpClient;
    private PreferenceActivity.Header refererHeader; //был header


    public YaGeocoder(URLConnection httpClient) {
        this.httpClient = httpClient;
    }

    public YaGeocoder(URLConnection httpClient, String referer) {
        this.httpClient = httpClient;
        refererHeader = new BasicHeader("Referer", referer);
    }


    public GeocoderResponse directGeocode(String geocode) throws IOException {
        String url = GEOCODER_HOST + "?geocode=" + URLEncoder.encode(geocode, "UTF-8");
        HttpUriRequest request = new HttpGet(url);
        request.addHeader(refererHeader);
        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() != HTTP_OK) {
            throw new IOException(response.getStatusLine().toString());
        }
        return responseParser.parse(response.getEntity().getContent());
    }
}
