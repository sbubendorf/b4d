package price;

import beans.Mover;

public class PriceOffer {
	
	Mover mover = null;
	int price = 0;

	public PriceOffer() {
		// TODO Auto-generated constructor stub
	}

	public Mover getMover() {
		return mover;
	}

	public void setMover(Mover mover) {
		this.mover = mover;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
}
