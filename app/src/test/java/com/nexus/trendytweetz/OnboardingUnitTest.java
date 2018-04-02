package com.nexus.trendytweetz;

import com.nexus.trendytweetz.ui.onboarding.SplashPresenterImpl;
import com.nexus.trendytweetz.ui.onboarding.SplashView;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class OnboardingUnitTest {

    @Mock
    SplashView mOnboardingView;

    @Mock
    private SplashPresenterImpl mOnboardingPresenter;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mOnboardingPresenter = new SplashPresenterImpl(mOnboardingView);
    }

    @Test
    public void testServerLoginSuccess() {
        mOnboardingPresenter.onLoginSuccess();
    }

    @After
    public void tearDown() throws Exception {
        mOnboardingPresenter.onDetach();
    }
}