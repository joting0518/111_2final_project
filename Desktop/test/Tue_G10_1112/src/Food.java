
public class Food {
	int amount = 0;
	String name = "";
	int ID = 0;
	/*(這三個應該在constructor中依據不同物件被賦予不同值，但因為會牽涉到GUI那邊create food object的情況，所以之後合併程式碼再解決，先記錄下來以免忘記)*/
	int rsID=-1;//database 會存取reserveListID
	int rcID=-1;//database 會存取receiveListID
	int pID=-1;//database 會存取provideListID
	DataBaseManager dbManager;
	public Food(String name, int amount) {
		this.name=name;
		this.amount=amount;
//		dbManager.createFood(this);
//		ID = dbManager.getFoodID(this);
	}
	public Food(String name, int amount, int fID, int pID, int rsID, int rcID) {
		this.name=name;
		this.amount=amount;
//		dbManager.createFood(this);
		this.ID = fID;
		this.pID = pID;
		this.rsID = rsID;
		this.rcID = rcID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public void setRsID(int ID) {
		this.rsID = ID;
	}
	public void setRcID(int ID) {
		this.rcID = ID;
	}public void setPID(int ID) {
		this.pID = ID;
	}
	public int getID() {
		return ID;
	}
	public int getAmount() {
		return amount;
	}
	public String getName() {
		return name;
	}
	public int getRSID() {
		return rsID;
	}
	public int getRCID() {
		return rcID;
	}
	public int getPID() {
		return pID;
	}
	public void deduce(int a) {
		amount -= a;
	}
	public void add(int a) {
		amount+=a;
	}
}

//, ReserveList rs, ProvideList p, ReceiveList rc
//if(rs!=null) {
//	rsID=rs.getID();
//}
//if(p!=null) {
//	pID=p.getID();
//}
//if(rc!=null) {
//	rcID=rc.getID();
//}