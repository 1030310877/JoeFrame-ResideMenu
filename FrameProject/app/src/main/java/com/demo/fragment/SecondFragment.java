package com.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.demo.activity.DemoSecondActivity;
import com.demo.frameproject.R;

import org.simple.eventbus.Subscriber;

import joe.frame.annotations.ViewInject;
import joe.frame.fragment.FrameBaseFragment;

/**
 * Description
 * Created by chenqiao on 2015/7/23.
 */
public class SecondFragment extends FrameBaseFragment {

    @ViewInject(R.id.button)
    private Button startBtn;

    @Override
    protected void onMyFragmentCreate(Bundle savedInstanceState) {
        registerEventBus();
        setMyContentView(R.layout.demo_second);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DemoSecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreateMyToolbarMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected boolean onMyToolbarMenuItemClicked(MenuItem item) {
        return false;
    }

    /**
     * 接受EventBus的消息传递
     *
     * @param text
     */
    @Subscriber(tag = "setTxt")
    private void settext(String text) {
        ((TextView) findViewById(R.id.mytitle)).setText(text);
    }
}