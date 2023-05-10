package com.appliedolap.essbase;

import java.io.UnsupportedEncodingException;

import com.essbase.api.base.EssException;
import com.essbase.api.datasource.IEssCube;
import com.essbase.api.datasource.IEssOlapServer;
import com.essbase.api.session.IEssbase;

public abstract class CubeOperationTest {

	public void run(String[] args) throws Exception {
		IEssbase essbase = null;
		IEssOlapServer olapServer = null;

		String username = "admin";
		String password = "password1";
		String server = "docker1";
		String provider = "embedded";
		String application = "Sample";
		String cubeName = "Basic";

		try {
			essbase = IEssbase.Home.create(IEssbase.JAPI_VERSION);
			// parameter order: userName, password, passwordIsToken, userNameAs, providerUrl,
			// olapServer
			// olapServer = essbase.signOn(username, password, false, null, provider, server);
			olapServer = essbase.signOn("jason@appliedolap.com", "EHloxi>3SanTtAx8Z(sQ2", false, null, "https://150.136.200.200/essbase/japi", "xxx");
			// IEssCube cube = olapServer.getApplication(application).getCube(cubeName);
			IEssCube cube = olapServer.getApplication("Sample_Scenario").getCube("Basic");
			doOperations(cube);
		} finally {
			try {
				System.out.println("Signing off Essbase");
				if (olapServer != null) {
					olapServer.disconnect();
				}
				essbase.signOff();
			} catch (EssException e) {
				System.err.println("Error signing off: " + e.getMessage());
			}
		}

	}
	
	public abstract void doOperations(IEssCube cube) throws Exception;
			
}
