package devdreamers.crudtasks.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by JoseLuis on 07/02/2016.
 */

/**
 * Request de Volley que implementa una autenticación a un servicio
 * web Http Basic y regresa en el cuerpo de la respuesta una cadena Json.
 *
 * @author Luis Alberto Gómez Rodríguez {@code alberto.gomez@geoenlace.com}
 * @version 1.0.0 20/11/2014
 * @since 1.0.0 13/10/2014
 */
public class BasicAuthRequest extends Request<JSONObject> {
    /**
     * Listener para procesar la respuesta de volley
     */
    private Response.Listener<JSONObject> listener;
    // cabecera de la aplicacion
    private String header;


    /**
     * Parámetros para la petición
     */
    private Map<String, String> params;

    /**
     * Constructor
     *
     * @param method          El método HTTP {@link Request.Method}
     * @param url             La url del recurso HTTP
     * @param params          {@link Map<String,String>} El arreglo de parámetros NameValuePair para enviar en la petición
     * @param reponseListener La instancia de {@link Response.Listener<JSONObject>} para procesar la respuesta
     * @param errorListener   Una instancia de {@link  Response.ErrorListener} para procesar los errores de HTTP
     * @since 3.0.0
     */
    public BasicAuthRequest(int method, String url, Map<String, String> params,
                            Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }


    /**
     * Obtiene el {@link Map<String, String>} con los parámetros de la petición.
     *
     * @return Una instancia de  {@link Map<String, String>} con los parámetros de la petición
     * @throws com.android.volley.AuthFailureError Cuando las credenciales de autenticación son incorrectas
     * @since 3.0.0
     */
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params;
    }



    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            volleyError = new VolleyError(new String(volleyError.networkResponse.data));
        }

        return volleyError;
    }
}