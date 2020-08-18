package com.dynm.api;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ML_GET_NOTEBOOK {
	private static String myId;
	
	
	@Test
	public void a_GET_NOTEBOOK_ID(){
		//Query usando 'Notebook' como parametro de busqueda guardado en variable tipo Response
		Response getResponse = RestAssured.get("https://api.mercadolibre.com/sites/MLA/search?q=notebook");
		
		//Me guardo el ID del 4to item en la lista de resultados
		myId =
		getResponse.then().contentType(ContentType.JSON).extract().path("results.id[3]");
		
		//Check Print
		System.out.println("ID del 4 item: "+myId);
		
		//Assertion de status code y dado el path chequear que el ID nos esté llegando bien.
		getResponse.
		then().statusCode(200).
		body("results.id[3]",equalTo(myId));
		
	}
	@Test
	public void b_GET_NOTEBOOK_DETAILS(){
		
		//Consulta a la segunda API con el ID que nos guardamos previamente
		Response getResponse = RestAssured.get("https://api.mercadolibre.com/items/"+myId);
		
		//Extracción de los atributos solicitados: nombre, precio y fecha de publicacion
		String nombre = getResponse.then().extract().path("title");
		Float precio = getResponse.then().extract().path("price");
		String fechaPubli = getResponse.then().extract().path("start_time");
		
		System.out.println("---------------------------------------------");
		System.out.println("Nombre de la laptop: "+nombre);
		System.out.println("Precio de la laptop: "+precio);
		System.out.println("Fecha de Inicio de Publicacion: "+fechaPubli);
		System.out.println("---------------------------------------------");

		
		getResponse.
		then().statusCode(200).
		body("title",equalTo(nombre)).
		body("price",equalTo(precio)).
		body("start_time",equalTo(fechaPubli));

		
	}
	
	
}
