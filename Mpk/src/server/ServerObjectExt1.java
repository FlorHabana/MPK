package server;

/*
COPYRIGHT 1995-2012 ESRI
TRADE SECRETS: ESRI PROPRIETARY AND CONFIDENTIAL
Unpublished material - all rights reserved under the 
Copyright Laws of the United States and applicable international
laws, treaties, and conventions.
 
For additional information, contact:
Environmental Systems Research Institute, Inc.
Attn: Contracts and Legal Services Department
380 New York Street
Redlands, California, 92373
USA
 
email: contracts@esri.com
*/
import java.io.IOException;

import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.interop.extn.ArcGISExtension;
import com.esri.arcgis.server.IServerObjectExtension;
import com.esri.arcgis.server.IServerObjectHelper;
import com.esri.arcgis.system.ILog;
import com.esri.arcgis.system.ServerUtilities;
import com.esri.arcgis.interop.extn.ServerObjectExtProperties;
import com.esri.arcgis.carto.IMapServerDataAccess;
import com.esri.arcgis.system.IObjectActivate;
import com.esri.arcgis.server.json.JSONArray;
import com.esri.arcgis.server.json.JSONException;
import com.esri.arcgis.server.json.JSONObject;
import com.esri.arcgis.system.IRESTRequestHandler;
import java.util.HashMap;

@ArcGISExtension
@ServerObjectExtProperties(displayName = "ServerObjectExt1 Display Name", description = "ServerObjectExt1 Description")

public class ServerObjectExt1 implements IServerObjectExtension, IObjectActivate, IRESTRequestHandler {
	private ILog serverLog;
	private IMapServerDataAccess mapServerDataAccess;

	public ServerObjectExt1() throws Exception {
		super();
	}

	/****************************************************************************************************************************
	 * IServerObjectExtension methods:
	 * This is a mandatory interface that must be supported by all SOEs. 
	 * This interface is used by the Server Object to manage the lifecycle of the SOE and includes 
	 * two methods: init() and shutdown(). 
	 * The Server Object cocreates the SOE and calls the init() method handing it a back reference 
	 * to the Server Object via the Server Object Helper argument. The Server Object Helper implements 
	 * a weak reference on the Server Object. The extension can keep a strong reference on the Server 
	 * Object Helper (for example, in a member variable) but should not. 
	 *    
	 * The log entries are merely informative and completely optional. 
	 ****************************************************************************************************************************/
	/**
	 * init() is called once, when the instance of the SOE is created. 
	 */
	public void init(IServerObjectHelper soh) throws IOException, AutomationException {
		/*
		  * An SOE should retrieve a weak reference to the Server Object from the Server Object Helper in
		  * order to make any method calls on the Server Object and release the
		  * reference after making the method calls.
		 */
		this.serverLog = ServerUtilities.getServerLogger();
		this.mapServerDataAccess = (IMapServerDataAccess) soh.getServerObject();
		this.serverLog.addMessage(3, 200, "Initialized " + this.getClass().getName() + " SOE.");
	}

	/**
	 * shutdown() is called once when the Server Object's context is being shut down and is 
	 * about to go away.
	 */
	public void shutdown() throws IOException, AutomationException {
		/*
		 * The SOE should release its reference on the Server Object Helper.
		 */
		this.serverLog.addMessage(3, 200, "Shutting down " + this.getClass().getName() + " SOE.");
		this.serverLog = null;
		this.mapServerDataAccess = null;
	}

	/****************************************************************************************************************************
	 * IObjectActivate:
	 * This is an optional interface for SOEs. 
	 * If your SOE requires special logic to run each time a request is made to the SOE and response is returned, you need to 	 * implement IObjectActivate. 
	 * 
	 * This interface includes 2 methods, activate() and deactivate().
	 ****************************************************************************************************************************/
	/**
	  * activate() is called each time a request is made to the SOE. Any logic implemented in this
	  * method should be trivial if possible, for best performance. 
	 */
	public void activate() {
		//TODO: add logic to handle your SOE's activation here
	}

	/**
	 * deactivate() is called each time a response is sent to the client. Any logic implemented in this
	 * method should be trivial if possible, for best performance.
	 */
	public void deactivate() {
		//TODO: add logic to handle your SOE's deactivation here
	}

	/**
	* Returns JSON representation of the root resource.
	* @return String JSON representation of the root resource.
	*/
	private byte[] getRootResource(String outputFormat, JSONObject requestPropertiesJSON,
			java.util.Map<String, String> responsePropertiesMap) throws Exception {
		JSONObject json = new JSONObject();
		json.put("name", "root resource");
		json.put("description", "ServerObjectExt1 description");
		return json.toString().getBytes("utf-8");
	}

	/**
	* Returns JSON representation of specified resource.
	* @return String JSON representation of specified resource.
	*/
	private byte[] getResource(String capabilitiesList, String resourceName, String outputFormat,
			JSONObject requestPropertiesJSON, java.util.Map<String, String> responsePropertiesMap) throws Exception {
		if (resourceName.equalsIgnoreCase("") || resourceName.length() == 0) {
			return getRootResource(outputFormat, requestPropertiesJSON, responsePropertiesMap);
		}

		return null;
	}

	/**
	* Invokes specified REST operation on specified REST resource
	* @param capabilitiesList
	* @param resourceName
	* @param operationName
	* @param operationInput
	* @param outputFormat
	* @param requestPropertiesMap
	* @param responsePropertiesMap
	* @return byte[]
	*/
	private byte[] invokeRESTOperation(String capabilitiesList, String resourceName, String operationName,
			String operationInput, String outputFormat, JSONObject requestPropertiesJSON,
			java.util.Map<String, String> responsePropertiesMap) throws Exception {
		JSONObject operationInputAsJSON = new JSONObject(operationInput);
		byte[] operationOutput = null;

		//permitted capabilities list can be used to allow/block access to operations

		//This REST resource and/or its sub-resources have no operations.

		return operationOutput;
	}

	/**
	 * Handles REST request by determining whether an operation or resource has been invoked and then forwards the
	 * request to appropriate Java methods, along with request and response properties
	 */
	public byte[] handleRESTRequest(String capabilities, String resourceName, String operationName,
			String operationInput, String outputFormat, String requestProperties, String[] responseProperties)
			throws IOException, AutomationException {
		// parse request properties, create a map to hold request properties
		JSONObject requestPropertiesJSON = new JSONObject(requestProperties);

		// create a response properties map to hold properties of response
		java.util.Map<String, String> responsePropertiesMap = new HashMap<String, String>();

		try {
			// if no operationName is specified send description of specified
			// resource
			byte[] response;
			if (operationName.length() == 0) {
				response = getResource(capabilities, resourceName, outputFormat, requestPropertiesJSON,
						responsePropertiesMap);
			} else
			// invoke REST operation on specified resource
			{
				response = invokeRESTOperation(capabilities, resourceName, operationName, operationInput, outputFormat,
						requestPropertiesJSON, responsePropertiesMap);
			}

			// handle response properties
			JSONObject responsePropertiesJSON = new JSONObject(responsePropertiesMap);
			responseProperties[0] = responsePropertiesJSON.toString();

			return response;
		} catch (Exception e) {
			String message = "Exception occurred while handling REST request for SOE " + this.getClass().getName() + ":"
					+ e.getMessage();
			this.serverLog.addMessage(1, 500, message);
			return ServerUtilities.sendError(0, message, null).getBytes("utf-8");
		}
	}

	/**
	 * This method returns the resource hierarchy of a REST based SOE in JSON format.
	 */
	public String getSchema() throws IOException, AutomationException {
		JSONObject _ServerObjectExt1 = ServerUtilities.createResource("ServerObjectExt1",
				"ServerObjectExt1 description", false, false);
		return _ServerObjectExt1.toString();
	}

}