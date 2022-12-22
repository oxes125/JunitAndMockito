package com.boot.bookingrestaurantapi.controller.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.entities.Board;
import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.RestaurantRest;
import com.boot.bookingrestaurantapi.jsons.TurnRest;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.services.RestaurantService;
import com.boot.bookingrestaurantapi.services.impl.RestaurantServiceImpl;


public class RestaurantServiceTest {

	private static final Restaurant  RESTAURANT= new  Restaurant();
	private static final Long RESTAURANT_ID=1L;
	private static final String NAME="Burguer";
	private static final String DESCRIPTION="Todo tipo de amburguesas";
	private static final String ADDRESS="Street, Adrees etc";
	private static final String IMAGE="www.google.images/10";
	private static final List<Turn> TURN_LIST= new  ArrayList<>();
	private static final List<Board> BOARD_LIST= new  ArrayList<>();
	private static final List<Reservation> RESERVATION_LIST= new  ArrayList<>();
	private static final List<Restaurant> RESTAURANT_LIST= new  ArrayList<>();
	
	@Mock
	RestaurantRepository rr;
	
	@InjectMocks
	RestaurantServiceImpl rs; //implementacion no interface
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		RESTAURANT.setId(RESTAURANT_ID);
		RESTAURANT.setName(NAME);		
		RESTAURANT.setDescription(DESCRIPTION);
		RESTAURANT.setAddress(ADDRESS);
		RESTAURANT.setImage(IMAGE);
		RESTAURANT.setTurns(TURN_LIST);
		RESTAURANT.setBoards(BOARD_LIST);
		RESTAURANT.setReservations(RESERVATION_LIST);
		
		RESTAURANT_LIST.add(RESTAURANT);
	}
	
	@Test
	public void getRestaurantByIdTest() throws BookingException {
		
		Mockito.when(rr.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));//Optional.of( important		
		rs.getRestaurantById(RESTAURANT_ID); //simepre servicio

	}
	
	@Test(expected =BookingException.class) //importante
	public void getRestaurantByIdNotFoundTest() throws BookingException {
		
		Mockito.when(rr.findById(RESTAURANT_ID)).thenReturn(Optional.empty());//Optional.of( important		
		rs.getRestaurantById(RESTAURANT_ID); //simepre servicio
		fail();

	}	
	
	@Test
	public void getRestaurantsTest() throws BookingException {
		final Restaurant rest = new Restaurant();
		Mockito.when(rr.findAll()).thenReturn(RESTAURANT_LIST);
		
		final List<RestaurantRest>response= rs.getRestaurants();
		assertNotNull(response);
		assertFalse(response.isEmpty());
		assertEquals(1,response.size());
	}
}
