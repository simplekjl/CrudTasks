package devdreamers.crudtasks.Network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by JoseLuis on 07/02/2016.
 */

public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private Context mContex;

    /**
     * Constructor
     *
     * @param context El contexto de la aplicación o activity
     * @since 3.0.0
     */
    private VolleySingleton(Context context) {
        this.mContex = context;
    }

    /**
     * Factoria para construir el Singleton de la cola de Volley
     *
     * @param context El contexto de la aplicación o activity
     * @return la instancia única de {@link VolleySingleton}
     * @since 3.0.0
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    /**
     * Obtiene la cola de peticiones de Volley
     *
     * @return Una instancia de {@link RequestQueue}
     * @since 3.0.0
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContex.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Agrega una peticion Http (Request) a la cola de Volley para ser ejecutada
     * en un Thread async.
     *
     * @param req La petición HTTP
     * @param <T> El tipo de petición Volley
     * @since 3.0.0
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
