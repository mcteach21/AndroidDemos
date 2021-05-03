package mc.apps.demos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import mc.apps.demos.ui.fragments.MainFragment;
import mc.apps.demos.ui.fragments.MainViewModel;

public class FragmentsActivity extends AppCompatActivity {
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragments_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }

        viewModel =  new ViewModelProvider(this).get(MainViewModel.class);
    }
    public void click(View view) {
        viewModel.setShared("Hello fragment!", this);
    }
}