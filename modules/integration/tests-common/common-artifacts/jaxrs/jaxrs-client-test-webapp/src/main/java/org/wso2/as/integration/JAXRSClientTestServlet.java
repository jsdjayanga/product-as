/*
*Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.as.integration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class JAXRSTestServlet
 */
@WebServlet("/JAXRSTestServlet")
public class JAXRSClientTestServlet extends HttpServlet {
    private String host = "http://localhost:9763/";
    private String serviceEndPoint =
            "jaxrs_starbucks_service/services/Starbucks_Outlet_Service";
    private String endPointURL = host + serviceEndPoint;

    private Client client = ClientBuilder.newClient();

    /**
     * Default constructor.
     */
    public JAXRSClientTestServlet() {
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String hostIp = request.getParameter("Host");
        String port = request.getParameter("Port");
        if (hostIp != null && port != null) {
            host = "http://" + hostIp + ":" + port + "/";
            endPointURL = host + serviceEndPoint;
        }

        String method = request.getParameter("HTTPMethod");
        if ("GET".equals(method)) {
            testGETMethodWithJAXRSClientApi(request, response);
        } else if ("POST".equals(method)) {
            testPOSTMethodWithJAXRSClientApi(request, response);
        } else if ("PUT".equals(method)) {
            testPUTMethodWithJAXRSClientApi(request, response);
        } else if ("DELETE".equals(method)) {
            testDELETEMethodWithJAXRSClientApi(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void testGETMethodWithJAXRSClientApi(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderId = request.getParameter("OrderId");
        // Sent HTTP GET request to query customer info
        System.out.println("Sent HTTP GET request to query order info of " + orderId);
        String orderURL = endPointURL + "/orders/" + orderId;
        WebTarget target = client.target(orderURL);
        Response response1 = target.request(MediaType.APPLICATION_JSON_TYPE).get();
        printOutput(response, response1);
    }

    private void testPOSTMethodWithJAXRSClientApi(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderURL = endPointURL + "/orders";
        WebTarget target = client.target(orderURL);
        // Sent HTTP POST request to add customer order
        System.out.println("\n");
        System.out.println("Sent HTTP POST request to add an order");
        String postData = "<Order>\n" +
                "    <drinkName>Mocha Flavored Coffee</drinkName>\n" +
                "    <additions>Caramel</additions>\n" +
                "</Order>\n";
        Entity<String> entity = Entity.entity(postData, MediaType.TEXT_XML_TYPE);
        Response response1 = target.request().post(entity);
        printOutput(response, response1);
    }

    private void testPUTMethodWithJAXRSClientApi(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderURL = endPointURL + "/orders";
        WebTarget target = client.target(orderURL);

        String orderId = request.getParameter("OrderId");

        // Sent HTTP PUT request to update customer order
        System.out.println("\n");
        System.out.println("Sent HTTP PUT request to change an order");

        String putData = "{\n" +
                "    \"Order\":{\n" +
                "        \"orderId\":\"" + orderId + "\",\n" +
                "        \"additions\":\"Chocolate Chip Cookies\"\n" +
                "    }\n" +
                "}\n";
        Entity<String> entity = Entity.entity(putData, MediaType.APPLICATION_JSON_TYPE);
        Response response1 = target.request().put(entity);
        printOutput(response, response1);
    }

    private void testDELETEMethodWithJAXRSClientApi(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderId = request.getParameter("OrderId");
        String orderURL = endPointURL + "/orders/" + orderId;
        WebTarget target = client.target(orderURL);
        Response response1 = target.request().delete();
        printOutput(response, response1);
    }

    private void printOutput(HttpServletResponse response, Response response1) throws IOException {
        if (response1.getStatus() == 200) {
            response.setContentType(MediaType.APPLICATION_JSON);
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            out.write(response1.readEntity(String.class));
            out.flush();
        }
    }
}
