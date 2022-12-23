package com.boot.bookingrestaurantapi.controller.services;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Ignore;
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
import com.boot.bookingrestaurantapi.exceptions.NotFountException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.repositories.ReservationRespository;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.repositories.TurnRepository;
import com.boot.bookingrestaurantapi.services.impl.ReservationServiceImpl;

public class ReservationServiceImplTest {

	@InjectMocks
	private ReservationServiceImpl reservationService;
	
	@Mock
	private RestaurantRepository restaurantRepository;
	
	@Mock
	private TurnRepository turnRepository;
	
	@Mock
	private ReservationRespository reservationRespository;
	
	
	CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
	Restaurant restaurant = new Restaurant();
	Turn turn = new Turn();
	
	private static final Date DATE= new Date();
	private static final Long PERSON=1L;
	private static final Long TURN=1L;
	
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
	
	private static final Long TURN_ID =1L;
	private static final String TURN_NAME ="Burguer 2";
	
	private static final Reservation RESERVATION = new Reservation();
	

	
	private static final Optional<Reservation> OPTIONAL_RESERVATION_EMPTY= Optional.empty();
	private static final Optional<Reservation> OPTIONAL_RESERVATION= Optional.of(RESERVATION);
	

	private static final String LOCATOR="TURNO_12";

	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURN);
		
		RESTAURANT.setId(RESTAURANT_ID);
		RESTAURANT.setName(NAME);		
		RESTAURANT.setDescription(DESCRIPTION);
		RESTAURANT.setAddress(ADDRESS);
		RESTAURANT.setImage(IMAGE);
		RESTAURANT.setTurns(TURN_LIST);
		RESTAURANT.setBoards(BOARD_LIST);
		RESTAURANT.setReservations(RESERVATION_LIST);
		
		RESTAURANT_LIST.add(RESTAURANT);
		
		turn.setId(TURN_ID);
		turn.setName(TURN_NAME);
		turn.setRestaurant(RESTAURANT);
		
		RESERVATION.setDate(DATE);
		RESERVATION.setId(RESTAURANT_ID);
		RESERVATION.setLocator(LOCATOR);
		RESERVATION.setPerson(PERSON);
		RESERVATION.setRestaurant(RESTAURANT);
		RESERVATION.setTurn(TURN_NAME);
		
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
		Mockito.when(turnRepository.findById(turn.getId())).thenReturn(Optional.of(turn));
		Mockito.when(reservationRespository.findByTurnAndRestaurantId(
				turn.getName(),restaurant.getId()))
				.thenReturn(OPTIONAL_RESERVATION_EMPTY);
		
		Mockito.when(reservationRespository.save(Mockito.any(Reservation.class))).thenReturn(new Reservation());	
	}
	
	@Test
	public void CreateReservationRestTest() throws BookingException   {
	
		reservationService.createReservation(CREATE_RESERVATION_REST);
		
	}
	
	@Test(expected = NotFountException.class)
	public void RestaurantNotFoundTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.empty());
		reservationService.createReservation(CREATE_RESERVATION_REST);
		fail();
	}
	
	@Test(expected = NotFountException.class)
	public void turnNotFoundTest() throws BookingException {
		Mockito.when(turnRepository.findById(CREATE_RESERVATION_REST.getTurnId())).thenReturn(Optional.empty());
		reservationService.createReservation(CREATE_RESERVATION_REST);
		fail();
	}	
	
	@Test(expected = Exception.class)
	public void saveErrorTest() throws BookingException {

		Mockito.doThrow(Exception.class).when(reservationRespository).save(Mockito.any(Reservation.class));
		reservationService.createReservation(CREATE_RESERVATION_REST);
		fail();
	}	
	
	

	@Test(expected = NotFountException.class)
	public void turnAndRestaurantNotFoundTest() throws BookingException {
		
		Mockito.when(reservationRespository.findByTurnAndRestaurantId(
				turn.getName(),restaurant.getId()))
				.thenReturn(OPTIONAL_RESERVATION);
		
		reservationService.createReservation(CREATE_RESERVATION_REST);
		fail();
	}	
	

}
