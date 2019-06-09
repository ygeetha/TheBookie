package testservice;

import static org.junit.Assert.*;

import java.util.List;

import service.*;
import model.questura.Country;

import org.junit.Test;

import persistence.mybatis.mappers.CountryMapper;

public class CountryServiceImplTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		CountryServiceImpl countryServiceImpl=new CountryServiceImpl();
//		Country country=(Country)countryServiceImpl.findById(0) ;
		//List<Country> country=countryServiceImpl.findAll();
		CountryMapper countryMapper= countryServiceImpl.getCountryMapper();
		//System.out.println(country.toString());
		
	}

}
