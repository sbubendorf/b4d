package beans;

public class Email {
	
	private String eMail;

	public Email(String eMail) {
		super();
		this.eMail = eMail;
	}
	
	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public boolean validate() {
		
		if ( eMail == null || eMail.length() == 0) {
			// No eMail address set!
			return false;
		}

		if ( !eMail.matches("\\w+@\\w+\\.\\w+") ) {
			// Invalid eMail address!
			return false;
		}

		return true;
	}
}
