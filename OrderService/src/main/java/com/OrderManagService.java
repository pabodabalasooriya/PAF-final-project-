package com;

import model.Order;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;


@Path("/Order")
public class OrderManagService
{
	Order orderObj = new Order();
		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		public String readOrder()
		{
			return orderObj.readOrder();
		}
	
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String insertOrder(
			@FormParam("buyerID") String buyerID,
			@FormParam("buyerName") String buyerName,
			@FormParam("orderType") String orderType,
			@FormParam("orderDate") String orderDate,
			@FormParam("orderDescription") String orderDescription,
			@FormParam("totalAmount") String totalAmount)
			{
				String output = orderObj.insertOrder(buyerID, buyerName, orderType, orderDate, orderDescription, totalAmount);
				return output;
			}
	
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateOrder(String orderData)
		{
			//Convert the input string to a JSON object
			JsonObject orderObject = new JsonParser().parse(orderData).getAsJsonObject();
			//Read the values from the JSON object
			String orderID = orderObject.get("orderID").getAsString();
			String buyerID = orderObject.get("buyerID").getAsString();
			String buyerName = orderObject.get("buyerName").getAsString();
			String orderType = orderObject.get("orderType").getAsString();
			String orderDate = orderObject.get("orderDate").getAsString();
			String orderDescription = orderObject.get("orderDescription").getAsString();
			String totalAmount = orderObject.get("totalAmount").getAsString();
			
			String output = orderObj.updateOrder(orderID, buyerID, buyerName, orderType, orderDate, orderDescription, totalAmount);
			return output;
		}
	
		@DELETE
		@Path("/")
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteOrder(String orderData)
		{
			//Convert the input string to an XML document
			Document doc = Jsoup.parse(orderData, "", Parser.xmlParser());
			
			
			//Read the value from the element <orderID>
			String orderID = doc.select("orderID").text();
			String output = orderObj.deleteOrder(orderID);
			return output;
		}
}