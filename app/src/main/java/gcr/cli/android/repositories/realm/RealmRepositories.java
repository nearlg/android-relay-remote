package gcr.cli.android.repositories.realm;

import android.content.Context;

import gcr.cli.android.repositories.IRepositories;
import gcr.cli.android.repositories.IServerRepository;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmRepositories implements IRepositories {

    private IServerRepository serverRepository;

    @Override
    public IServerRepository getServerRepository() {
        if(serverRepository == null) {
            Realm realm = Realm.getDefaultInstance();
            serverRepository = new RealmServerRepository(realm);
        }
        return serverRepository;
    }

    @Override
    public void closeConnection() {
        Realm realm = Realm.getDefaultInstance();
        realm.close();
    }

    @Override
    public void openConnection(Context context) {
        Realm.init(context);
        setUpConfiguration();
    }

    private void setUpConfiguration() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
