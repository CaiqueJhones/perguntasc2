package io.cjdeveleper.perguntasc;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import io.cjdeveleper.perguntasc.adapters.StageAdapter;
import io.cjdeveleper.perguntasc.backend.Database;
import io.cjdeveleper.perguntasc.backend.FirebaseImpl;
import io.cjdeveleper.perguntasc.backend.OnlyAddAdapter;
import io.cjdeveleper.perguntasc.entities.Info;
import io.cjdeveleper.perguntasc.entities.Stage;

@EActivity(R.layout.activity_start)
public class StartActivity extends BaseActivity {

    @ViewById
    ProfilePictureView imageProfile;
    @ViewById
    TextView textName;
    @ViewById
    TextView textPoints;
    @ViewById
    RecyclerView recyclerStages;

    @Bean
    StageAdapter stageAdapter;

    @InstanceState
    @Extra
    String facebookUserId;

    private Database<Stage> stageDatabase;
    private Database<Info> infoDatabase;
    private List<Info> infos = new ArrayList<>();

    @AfterInject
    void initBeans() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        stageDatabase = new FirebaseImpl<>(database.getReference("stages"), Stage.class);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        infoDatabase = new FirebaseImpl<>(database.getReference("information").child(user.getUid()),
                Info.class);

        stageDatabase.registerObserver(stageAdapter);
        infoDatabase.registerObserver(new OnlyAddAdapter<Info>() {
            @Override
            public void onChildAdded(Info value) {
                infos.add(value);
                setText();
            }

            @Override
            public void onChildChanged(Info value) {
                int i = infos.indexOf(value);
                if (i > -1) {
                    Info info = infos.get(i);
                    info.setHits(value.getHits());
                    info.setErrors(value.getErrors());
                }
                setText();
            }

            private void setText() {
                int points = 0;
                for (Info info : infos) {
                    int sum = (info.getHits() + info.getErrors());
                    if (sum > 0)
                        points += Math.round(info.getHits() * 10 - info.getErrors() * 5) / sum;
                }
                textPoints.setText(getString(R.string.points, Math.max(0, points)));
            }
        });
    }

    @AfterViews
    void initViews() {
        this.toolbar.setTitle(R.string.app_name);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            textName.setText(user.getDisplayName());
        }

        imageProfile.setProfileId(facebookUserId);
        textPoints.setText(getString(R.string.points, 0));

        recyclerStages.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerStages.setAdapter(stageAdapter);
    }

    @Click
    void signOutClicked() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        LoginActivity_.intent(this).start();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        stageAdapter.clear();
        infos.clear();
        if (stageDatabase != null)
            stageDatabase.onStart();
        if (infoDatabase != null)
            infoDatabase.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (stageDatabase != null)
            stageDatabase.onStop();
        if (infoDatabase != null)
            infoDatabase.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
