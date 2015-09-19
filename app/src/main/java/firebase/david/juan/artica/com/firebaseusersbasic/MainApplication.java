package firebase.david.juan.artica.com.firebaseusersbasic;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;

/**
 * Created by interoperabilidad on 19/09/15.
 */
public class MainApplication extends Application {
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        FacebookSdk.sdkInitialize(this);
    }
}
