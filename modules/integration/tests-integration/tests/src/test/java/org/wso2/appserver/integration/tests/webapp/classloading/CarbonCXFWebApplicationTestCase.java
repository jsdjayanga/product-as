/*
 *Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *WSO2 Inc. licenses this file to you under the Apache License,
 *Version 2.0 (the "License"); you may not use this file except
 *in compliance with the License.
 *You may obtain a copy of the License at
 *
 *http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing,
 *software distributed under the License is distributed on an
 *"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *KIND, either express or implied.  See the License for the
 *specific language governing permissions and limitations
 *under the License.
 */
package org.wso2.appserver.integration.tests.webapp.classloading;

import org.testng.annotations.BeforeClass;
import org.wso2.appserver.integration.common.clients.WebAppAdminClient;



public class CarbonCXFWebApplicationTestCase extends
        WebApplicationClassloadingTestCase {
	private final String webAppFileName = "appServer-cxf-carbon-cl-app-1.0.0.war";
	private final String webAppName = "appServer-cxf-carbon-cl-app-1.0.0";
	private final String webAppLocalURL ="/appServer-cxf-carbon-cl-app-1.0.0";
	WebAppAdminClient webAppAdminClient;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		super.init();
		setWebAppFileName(webAppFileName);
		setWebAppName(webAppName);
		setWebAppURL(getWebAppURL() + webAppLocalURL);

	}

}
