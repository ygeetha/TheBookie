package testservice;

import static org.junit.Assert.*;
import model.CreditCard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import persistence.mybatis.mappers.CreditCardMapper;
import service.CreditCardServiceImpl;
@RunWith(MockitoJUnitRunner.class)
public class CreditCardServiceImplTest {
	
	@Mock
	private CreditCardMapper creditCardMapper ;

	@InjectMocks
	CreditCardServiceImpl creditCardServiceImpl;
	
	
	@Test
	public void test() {
		//fail("Not yet implemented");
		//CreditCardServiceImpl creditCardServiceImpl=new CreditCardServiceImpl();
		CreditCard creditCard=new CreditCard();
		
		creditCard.setId(25);
		creditCard.setCardType("PLatinum");
		creditCard.setCardNumber("1234567812345678");
		creditCard.setExpMonth(2);
		creditCard.setExpYear(2020);
		creditCard.setSecurityCode("348");
		creditCard.setFirstName("Wipro");
		creditCard.setLastName("Poc");
		creditCard.setId_booking(25);
		
		Mockito.when(creditCardMapper.insertCreditCard(creditCard)).thenReturn(1);
		Integer number=creditCardServiceImpl.insertCreditCard(creditCard);
			assertEquals(new Integer(1), number);
		}
	@Test
        public void updateCreditCardTest()
        {
        	 CreditCard creditCard=new CreditCard();

             creditCard.setId(25);
             creditCard.setCardType("PLatinum");
             creditCard.setCardNumber("1234567812345678");
             creditCard.setExpMonth(2);
             creditCard.setExpYear(2020);
             creditCard.setSecurityCode("348");
             creditCard.setFirstName("Wipro");
             creditCard.setLastName("Poc");
             creditCard.setId_booking(25);

             Mockito.when(creditCardMapper.updateCreditCard(creditCard)).thenReturn(1);
             Integer number=creditCardServiceImpl.updateCreditCard(creditCard);
                     assertEquals(new Integer(1), number);

        }		
         @Test
        public void deleteCreditCardTest()
        {
                 CreditCard creditCard=new CreditCard();

             creditCard.setId(25);
             creditCard.setCardType("PLatinum");
             creditCard.setCardNumber("1234567812345678");
             creditCard.setExpMonth(2);
             creditCard.setExpYear(2020);
             creditCard.setSecurityCode("348");
             creditCard.setFirstName("Wipro");
             creditCard.setLastName("Poc");
             creditCard.setId_booking(25);

             Mockito.when(creditCardMapper.deleteCreditCard(new Integer(1))).thenReturn(1);
             Integer number=creditCardServiceImpl.deleteCreditCard(1);
                     assertEquals(new Integer(1), number);

        }

	 @Test
        public void findCreditCardByIdTest()
        {
                 CreditCard creditCard=new CreditCard();

             creditCard.setId(25);
             creditCard.setCardType("PLatinum");
             creditCard.setCardNumber("1234567812345678");
             creditCard.setExpMonth(2);
             creditCard.setExpYear(2020);
             creditCard.setSecurityCode("348");
             creditCard.setFirstName("Wipro");
             creditCard.setLastName("Poc");
             creditCard.setId_booking(25);

             Mockito.when(creditCardMapper.findCreditCardById(new Integer(1))).thenReturn(creditCard);
             CreditCard newcreditCard=creditCardServiceImpl.findCreditCardById(1);
                     assertEquals(newcreditCard,creditCard);

        }

	 @Test
        public void findCreditCardByIdBookingTest()
        {
                 CreditCard creditCard=new CreditCard();

             creditCard.setId(25);
             creditCard.setCardType("PLatinum");
             creditCard.setCardNumber("1234567812345678");
             creditCard.setExpMonth(2);
             creditCard.setExpYear(2020);
             creditCard.setSecurityCode("348");
             creditCard.setFirstName("Wipro");
             creditCard.setLastName("Poc");
             creditCard.setId_booking(25);

//             Mockito.when(creditCardMapper.findCreditCardById(new Integer(1))).thenReturn(creditCard);
	     Mockito.when(creditCardMapper.findCreditCardByIdBooking(new Integer(1))).thenReturn(creditCard);
             CreditCard newcreditCard=creditCardServiceImpl.findCreditCardByIdBooking(1);
                     assertEquals(newcreditCard,creditCard);

        }

 
}


