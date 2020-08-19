/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.infonline;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.annotations.Kroll.module;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;

import android.app.Activity;
import android.widget.Toast;

import de.infonline.lib.IOLEvent;
import de.infonline.lib.IOLSessionPrivacySetting;
import de.infonline.lib.IOLSessionType;
import de.infonline.lib.IOLSession;

import org.appcelerator.titanium.TiProperties;

@module(name = "Infonline", id = "ti.infonline")
public class InfonlineModule extends KrollModule {

	// Standard Debugging variables
	private static final String LCAT = "IVWMod";

	private boolean isSessionOpened = false;
	private String customerData;
	private String offerIdentifier;
	private boolean isOptIn = false;
	private boolean dbg = false;

	@Kroll.method
	public void optIn() {
		isOptIn = true;
	}

	@Kroll.method
	public void optOut() {
		isOptIn = false;
		IOLSession.getSessionForType(IOLSessionType.SZM).terminateSession();
	}

	@Kroll.method
	public void sendLoggedEvents() {
		IOLSession.getSessionForType(IOLSessionType.SZM).sendLoggedEvents();
	}

	@Kroll.method
	public void logEvent(Object _event) {
		IOLEvent event = ((InfonlineEventProxy) _event).event;

		if (!isSessionOpened) {
			IOLSession.getSessionForType(IOLSessionType.SZM).startSession();
		}

		IOLSession.getSessionForType(IOLSessionType.SZM).logEvent(event);
	}

	// Methods
	@Kroll.method
	public void startSession() {
		String KEY = "IVW_OFFER_ID_ANDROID";
		TiProperties props = TiApplication.getInstance().getAppProperties();
		String offerId = props.hasProperty(KEY) ? props.getString(KEY, "") : this.offerIdentifier;

		if (offerId != null) {
			IOLSession.getSessionForType(IOLSessionType.SZM).initIOLSession(offerId, false, IOLSessionPrivacySetting.LIN);
			Log.d(LCAT,
					"****************************************************************\n"
							+ "IOLSession started with: " + offerId +
							"\n****************************************************************");
		} else {
			Toast.makeText(TiApplication.getAppCurrentActivity(), "The mandatory offerId is missing.\nPlease read log",
					Toast.LENGTH_LONG).show();
			Log.e(LCAT,
					"***************************************************************************\n"
							+ "You need to add a property with name 'IVW_OFFER_ID_ANDROID' to tiapp.xml"+
							"  <property name=\"" + KEY + "\" type=\"string\">###YOUR_KEY###</property>" +
							"  or set the \"offerIdentifier\" property of this module!" +
							"\n***************************************************************************");
		}

		IOLSession.getSessionForType(IOLSessionType.SZM).startSession();
		isSessionOpened = true;
	}

	// Methods
	@Kroll.method
	public void stopSession() {
		IOLSession.getSessionForType(IOLSessionType.SZM).terminateSession();
		isSessionOpened = false;
	}

	@Kroll.method
	public void setDbg(Boolean dbg) {
		this.dbg = dbg;
		IOLSession.setDebugModeEnabled(dbg);
	}

	@Kroll.method
	public void enableDebug() {
		this.dbg = true;
		IOLSession.setDebugModeEnabled(true);
	}

	@Kroll.method
	public void disableDebug() {
		this.dbg = false;
		IOLSession.setDebugModeEnabled(false);
	}

	@Kroll.method
	public String getVersion() {
		Log.d(LCAT, "Version of Ti.InfOnline: " + IOLSession.getVersion());
		return IOLSession.getVersion();
	}

	@Kroll.method
	public void setDeviceIdEnabled(Boolean enabled) {
		IOLSession.setDeviceIDsEnabled(enabled);
	}

	@Kroll.setProperty
	@Kroll.method
	public void setCostumerData(String data) {
		customerData = data;
	}

	@Kroll.setProperty
	@Kroll.method
	public void setOfferIdentifier(String identifier) {
		offerIdentifier = identifier;
	}

	@Kroll.method
	public void onStart() {
		// IOLSession.onActivityStart();
	}

	@Kroll.method
	public void onStop() {
		// IOLSession.onActivityStop();
	}

	public void onStart(Activity activity) {
		super.onStart(activity);
		// IOLSession.onActivityStart();
	}

	public void onStop(Activity activity) {
		// IOLSession.onActivityStop();
		super.onStop(activity);
	}
}
