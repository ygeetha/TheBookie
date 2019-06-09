package testservice;

import static org.junit.Assert.*;
import service.*;
import java.util.Date;
import java.util.List;

import model.Facility;
import model.Image;
import model.Structure;
import model.User;

import org.apache.solr.client.solrj.beans.Field;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import persistence.mybatis.mappers.UserMapper;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Mock
	private UserMapper userMapper ;
	
	 @InjectMocks
	 UserServiceImpl userServiceImpl;
	 
	@Test
	public void insertUsertest() {
		
		User user= new User();
		user.setId(new Integer(1));
		user.setName("Nazriya");
		user.setSurname("Shaik");
		user.setEmail("wipropoc@wipro.com");
		user.setPhone("9876543213");
		user.setPassword("redhat1!");
		user.setCreationDate( new Date());
		Structure structure=new Structure();
		structure.setAddress("");
		structure.setId(1);
		structure.setCity("Bangalore");
		structure.setCountry("india");
		structure.setName("Nazriya");
		structure.setZipCode("560100");
		structure.setPhone("9876543213");
		structure.setFax("Nazriya");
		structure.setUrl("www.wipro.com");
		structure.setNotes("The future Light");
		structure.setId_user(1);
		structure.setMobile("9876543213");
		user.setStructure(structure);
		Mockito.when(userMapper.insertUser(user)).thenReturn(1);
		Integer numInteger = userServiceImpl.insertUser(user);
		assertEquals(new Integer(1), numInteger);
				
	}
        
	@Test
	public void updateUsertest() {
		
		User user= new User();
		user.setId(new Integer(1));
		user.setName("Nazriya");
		user.setSurname("Shaik");
		user.setEmail("wipropoc@wipro.com");
		user.setPhone("9876543213");
		user.setPassword("redhat1!");
		user.setCreationDate( new Date());
		Structure structure=new Structure();
		structure.setAddress("");
		structure.setId(1);
		structure.setCity("Bangalore");
		structure.setCountry("india");
		
		structure.setName("Nazriya");
		structure.setZipCode("560100");
		structure.setPhone("9876543213");
		structure.setFax("Nazriya");
		structure.setUrl("www.wipro.com");
		structure.setNotes("The future Light");
		structure.setId_user(1);
		
		structure.setMobile("9876543213");
		user.setStructure(structure);
		
		Mockito.when(userMapper.updateUser(user)).thenReturn(1);
		Integer numInteger = userServiceImpl.updateUser(user);
		assertEquals(new Integer(1), numInteger);
				
	}

}

