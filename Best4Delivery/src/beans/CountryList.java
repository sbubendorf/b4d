package beans;

import java.util.ArrayList;
import java.util.List;

public class CountryList {
	
	private List<Country> countries = new ArrayList<Country>();
	private List<Country> supportedCountriesB4D = null;
	private List<Country> supportedCountriesMover = null;

	public CountryList() {
		// TODO Auto-generated constructor stub
		countries = new ArrayList<Country>();
	}
	
	public void addCountry(Country country) {
		countries.add(country);
	}
	
	public List<Country> getAllCountries() {
		return countries;
	}
	
	public List<Country> getSupportedCountriesB4D() {
		if ( supportedCountriesB4D == null || supportedCountriesB4D.size() == 0 ) {
			supportedCountriesB4D = new ArrayList<Country>();
			for ( Country country : countries ) {
				if ( country.isSupportedB4D() ) {
					supportedCountriesB4D.add(country);
				}
			}
		}
		return supportedCountriesB4D;
	}
	
	public List<Country> getSupportedCountriesMover() {
		if ( supportedCountriesMover == null || supportedCountriesMover.size() == 0 ) {
			supportedCountriesMover = new ArrayList<Country>();
			for ( Country country : countries ) {
				if ( country.isSupportedMover() ) {
					supportedCountriesMover.add(country);
				}
			}
		}
		return supportedCountriesMover;
	}
	
	public Country getCountry(String countryIso2) {
		for ( Country country : countries ) {
			if ( country.getIso2().equals(countryIso2) ) {
				return country;
			}
		}
		return null;
	}
	
}
