package ru.autokit.android;

import android.content.Context;
import com.squareup.okhttp.HttpUrl;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import ru.autokit.android.di.DaggerTestComponent;
import ru.autokit.android.di.Module;
import ru.autokit.android.di.NetworkModule;
import ru.autokit.android.di.TestComponent;
import ru.autokit.android.screens.BaseContract;
import ru.autokit.android.screens.BasePresenter;
import ru.rsprm.utils.ServerRule;

@RunWith(RobolectricTestRunner.class)
public class BaseTest {

    public class TestNetworkModule extends NetworkModule {

        public TestNetworkModule(@NotNull String url, Context context) {
            super(url, context);
        }

        @NotNull
        @Override
        public Scheduler provideNetworkScheduler() {
            return Schedulers.trampoline();
        }

        @NotNull
        @Override
        public Scheduler provideUIScheduler() {
            return Schedulers.trampoline();
        }

        @NotNull
        @Override
        public OkHttpClient provideOkHttpClient(@NotNull Interceptor interceptor) {
            return super.provideOkHttpClient(provideDefaultInterceptor());
        }
    }

    @Mock
    protected BasePresenter<BaseContract.View> presenter;

    @Rule
    public ServerRule serverRule = new ServerRule();

    protected TestComponent component;

    @Before
    public void setUp() throws Exception {
        Context app = RuntimeEnvironment.application;

        HttpUrl url = serverRule.getUrl();

        component = DaggerTestComponent
                .builder()
                .module(new Module(app))
                .networkModule(new TestNetworkModule(url.toString(), app))
                .build();
    }
}
