package com.weidian.plugin.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import com.weidian.plugin.core.ctx.ContextProxy;
import com.weidian.plugin.core.ctx.Module;
import com.weidian.plugin.core.ctx.Plugin;

/**
 * @author: wyouflf
 * @date: 2014/11/14
 */
public class PluginActivity extends Activity {

	private ContextProxy contextProxy = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PageHelper.initTitleBar(this, contextProxy);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		PageHelper.setLastActivity(this);
		this.contextProxy = new ContextProxy(newBase, this);
		super.attachBaseContext(this.contextProxy);
	}

	@Override
	protected void onResume() {
		super.onResume();
		PageHelper.setLastActivity(this);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public void applyOverrideConfiguration(Configuration overrideConfiguration) {
		this.contextProxy.applyOverrideConfiguration(overrideConfiguration);
		super.applyOverrideConfiguration(overrideConfiguration);
	}

	private boolean firstSetTheme = true;

	@Override
	public void setTheme(int resid) {
		if (firstSetTheme) {
			firstSetTheme = false;
			if (Plugin.getPlugin(this) instanceof Module) {
				resid = PageHelper.getThemeResId(this, contextProxy);
			}
		}
		if (resid > 0) {
			super.setTheme(resid);
		}
	}

	@Override
	public void startActivity(final Intent intent) {
		PageHelper.startActivity(intent, new Runnable() {
			@Override
			public void run() {
				PluginActivity.super.startActivity(intent);
			}
		});
	}

	@Override
	public void startActivityForResult(final Intent intent, final int requestCode) {
		PageHelper.startActivity(intent, new Runnable() {
			@Override
			public void run() {
				PluginActivity.super.startActivityForResult(intent, requestCode);
			}
		});
	}
}
