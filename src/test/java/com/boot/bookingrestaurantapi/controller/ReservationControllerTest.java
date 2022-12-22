package com.boot.bookingrestaurantapi.controller;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.ReservationController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.jsons.ReservationRest;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.ReservationService;

public class ReservationControllerTest {

	
	@Mock
	ReservationService rs;
	
	@InjectMocks
	ReservationController rc;
	
	private static final Long RESERVATION_ID=1L;
	
	private static final String STATUS="Succes";
	private static final String CODE="200 OK";
	private static final String OK="OK";
	
	private static final String LOCATOR="BURGUER 2";
			
	ReservationRest reservationRest = new ReservationRest();
	CreateReservationRest	createReservationRest = new  CreateReservationRest();
	
	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);
		
		//lo correcto es hacer con variables staticas final
		reservationRest.setDate(new Date());
		reservationRest.setLocator("Locator");
		reservationRest.setPerson(1L);
		reservationRest.setRestaurantId(1L);
		reservationRest.setTurnId(1L);
		
		createReservationRest.setDate(new Date());
		createReservationRest.setPerson(1L);
		createReservationRest.setRestaurantId(1L);
		createReservationRest.setTurnId(1L);
		
		Mockito.when(rs.getReservation(RESERVATION_ID)).thenReturn(reservationRest);
		
		Mockito.when(rs.createReservation(createReservationRest)).thenReturn(LOCATOR);
		
	}
	
	@Test
	public void getReservationByIdTest()  throws BookingException{

		
		
		BookingResponse<ReservationRest> response =rc.getReservationById(RESERVATION_ID);
		assertEquals( STATUS,response.getStatus());
		assertEquals(OK,response.getMessage());
		assertEquals(CODE,response.getCode());
		
		assertEquals(response.getData(),reservationRest);
	}	
	
	@Test
	public void createReservationTest()  throws BookingException{
		
		
		BookingResponse<String> response=rc.createReservation(createReservationRest);
		
		assertEquals( STATUS,response.getStatus());
		assertEquals(OK,response.getMessage());
		assertEquals(CODE,response.getCode());
		
		assertEquals(LOCATOR,response.getData());
	}	
	
}
