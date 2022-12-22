package com.boot.bookingrestaurantapi.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.RestaurantController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.RestaurantRest;
import com.boot.bookingrestaurantapi.jsons.TurnRest;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.RestaurantService;

public class RestaurantControllerTest {

	private static final Long  RESTAURANT_ID=1L;
	private static final String SUCCESS_STATOS="Succes";
	private static final String SUCCESS_CODE="200 OK";
	private static final String OK="OK";
	
	private static final String NAME="Burguer";
	private static final String DESCRIPTION="Todo tipo de amburguesas";
	private static final String ADDRESS="Street, Adrees etc";
	private static final String IMAGE="www.google.images/10";
	public static final List<TurnRest> TURN_LIST= new  ArrayList<>();
	public static final List<RestaurantRest> RESTAURANT_LIST= new  ArrayList<>();
	
	public static final RestaurantRest RESTAURANT_REST= new  RestaurantRest();

	
	@Mock
	RestaurantService rs;
	
	@InjectMocks
	RestaurantController rc;
	
	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);
		RESTAURANT_REST.setId(RESTAURANT_ID);
		RESTAURANT_REST.setName(NAME);		
		RESTAURANT_REST.setDescription(DESCRIPTION);
		RESTAURANT_REST.setAddress(ADDRESS);
		RESTAURANT_REST.setImage(IMAGE);
		RESTAURANT_REST.setTurns(TURN_LIST);
		
		RESTAURANT_LIST.add(RESTAURANT_REST);
		
		Mockito.when(rs.getRestaurantById(RESTAURANT_ID)).thenReturn(RESTAURANT_REST);
		Mockito.when(rs.getRestaurants()).thenReturn(RESTAURANT_LIST);
	}
	
	@Test
	public void getRestaurantById() throws BookingException {
		final BookingResponse<RestaurantRest> response = rc.getRestaurantById(RESTAURANT_ID);
		
		assertEquals(response.getStatus(), SUCCESS_STATOS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), RESTAURANT_REST); //refactor rename 
	}
	
	@Test
	public void getRestaurantsTest() throws BookingException {
		final BookingResponse<List<RestaurantRest>> response = rc.getRestaurants();

		assertEquals(response.getStatus(), SUCCESS_STATOS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), RESTAURANT_LIST); //refactor rename 
	}
	
		
}
