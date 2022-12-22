package com.boot.bookingrestaurantapi.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.CancelReservationController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.CancelReservationService;

public class CancelReservationControllerTest {

	@Mock
	CancelReservationService cancelReservationService;
	
	@InjectMocks
	CancelReservationController cancelReservationController;
	
	private static final String LOCATION="Burguer 7";
	private static final String STATUS="Succes";
	private static final String CODE="200 OK";
	private static final String OK="OK";
	
	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(cancelReservationService.deleteReservation(LOCATION)).thenReturn(LOCATION);
	}
	
	@Test
	public void deleteReservationTest() throws BookingException {
		final BookingResponse<String>  response=cancelReservationController.deleteReservation(LOCATION);
		assertEquals( STATUS,response.getStatus());
		assertEquals(OK,response.getMessage());
		assertEquals(CODE,response.getCode());
		
		assertEquals(LOCATION,response.getData());
	}
}
