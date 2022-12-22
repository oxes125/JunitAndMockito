package com.boot.bookingrestaurantapi.controller.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.InternalServerErrorException;
import com.boot.bookingrestaurantapi.exceptions.NotFountException;
import com.boot.bookingrestaurantapi.repositories.ReservationRespository;
import com.boot.bookingrestaurantapi.services.impl.CancelReservationServiceImpl;

public class CancelReservationServiceImplTest {
	
	public static final Reservation RESERVATION = new Reservation();
	public static final Optional<Reservation> OPTIONAL_RESERVATION=Optional.of(RESERVATION);
	public static final String LOCATOR="Burguer 7";
	
	private static final Date DATE = new Date();
	private static final Long RESERVATION_ID = 1L;
	
	@Mock
	private ReservationRespository reservationRespository;
	
	@InjectMocks
	@Spy
	private CancelReservationServiceImpl cancelReservationServiceImpl;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		RESERVATION.setDate(DATE);
		RESERVATION.setId(RESERVATION_ID);
		
	}
	
	@Test
	public void deleteReservationTest() throws BookingException{
		
		Mockito.when(reservationRespository.findByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
		
		Mockito.when(reservationRespository.deleteByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
		final String response=cancelReservationServiceImpl.deleteReservation(LOCATOR);
		assertEquals(response, "LOCATOR_DELETED");
	}
	
	@Test(expected = NotFountException.class)
	public void deleteReservationErrorTest() throws BookingException{
		
		Mockito.when(reservationRespository.findByLocator(LOCATOR)).thenReturn(Optional.empty());
		
		Mockito.when(reservationRespository.deleteByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
		cancelReservationServiceImpl.deleteReservation(LOCATOR);
		fail();
	}
	
	@Test(expected = Exception.class)
	public void internalServerErrorTest() throws BookingException{
		
		Mockito.when(reservationRespository.findByLocator(LOCATOR)).thenReturn(Optional.empty());
		//Mockito.when(reservationRespository.deleteByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
		Mockito.doThrow(Exception.class).when(reservationRespository).deleteByLocator(LOCATOR);
		final String response=cancelReservationServiceImpl.deleteReservation(LOCATOR);

		assertEquals(response, "LOCATOR_DELETED");
		fail();
	}	
}
