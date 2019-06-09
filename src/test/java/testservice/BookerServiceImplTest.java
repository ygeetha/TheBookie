package testservice;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import persistence.mybatis.mappers.BookerMapper;
import service.*;
import model.*;
@RunWith(MockitoJUnitRunner.class)
public class BookerServiceImplTest {

	@Mock
	private GuestService guestService;
	@Mock
	private BookerMapper bookerMapper;
	@InjectMocks
	BookerServiceImpl bookerServiceImpl;

	@Test
	public void testInsert() {
		Mockito.when(bookerMapper.insert(Mockito.anyMap())).thenReturn(30);
		Integer testdata = bookerServiceImpl.insert(10, 20);
		assertNotNull(testdata);
		assertTrue(testdata.equals(30));
	}

	
}
