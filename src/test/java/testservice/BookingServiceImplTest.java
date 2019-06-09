package testservice;
import java.util.Date;
import java.util.List;

import model.*;
import service.*;
import service.*;
import static org.junit.Assert.*;
import model.Booking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import persistence.mybatis.mappers.BookingMapper;
@RunWith(MockitoJUnitRunner.class)
public class BookingServiceImplTest {

	@Mock
	private RoomPriceListService roomPriceListService ;
	@Mock
	private SeasonService seasonService ;
	@Mock
	private BookingMapper bookingMapper ;
	@Mock
	private BookerService bookerService ;
	@Mock
	private GuestService guestService ;
	@Mock 
	private ExtraItemService extraItemService ;
	@Mock
	private AdjustmentService adjustmentService ;
	@Mock
	private PaymentService paymentService ;
	@Mock
	private RoomService roomService ;
	@Mock
	private ConventionService conventionService ;
	@Mock
	private HousedService housedService ;
	@Mock
	private GroupLeaderService groupLeaderService ;
	@Mock
	private HousedExportService housedExportService ;
	@Mock
	private CreditCardService creditCardService ;
	@InjectMocks
	BookerServiceImpl bookerServiceImpl;

	
@Test
public void test(){
	
}

}
