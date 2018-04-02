package com.nexus.trendytweetz;


import com.nexus.trendytweetz.ui.MainPresenterImpl;
import com.nexus.trendytweetz.ui.MainView;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityUnitText {

    private static final String HASH_TAG = "facebook";

    @Mock
    MainView mMockMainView;

    @Mock
    private MainPresenterImpl mMainPresenterImpl;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mMainPresenterImpl = new MainPresenterImpl(mMockMainView);
    }

    @Test
    public void fetchUsersTest() {
        mMainPresenterImpl.getTweetsWithHashTagFromServer(HASH_TAG);
    }

    @After
    public void tearDown() throws Exception {
        mMainPresenterImpl.prepareToExit();
        mMainPresenterImpl.onDetach();
    }

}
