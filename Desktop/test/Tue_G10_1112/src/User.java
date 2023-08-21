import java.util.ArrayList;

public class User {
	private int ID;
	private String password;
	private DataBaseManager dbManager = new DataBaseManager();
	private ArrayList<ProvideList> provide;
	private ArrayList<ReceiveList> receive;
	private ArrayList<ReserveList> reserve;
	public User(int ID, String password) {
		this.ID = ID;
		this.password = password;
		provide = new ArrayList<>();
		receive = new ArrayList<>();
		reserve = new ArrayList<>();
	}
	public User(int ID, String password, ArrayList<ProvideList> provide, ArrayList<ReceiveList> receive, ArrayList<ReserveList> reserve) {
		this.ID = ID;
		this.password = password;
		this.provide = provide;
		this.receive = receive;
		this.reserve = reserve;
	}
	public void provide(ArrayList<Food> foods,String generalLocation, String detailLocation, String description){
		//大小地點也要設為""
		//GUI要handle food name、amount的exception
		ProvideList prov = new ProvideList(foods, generalLocation, detailLocation, description, this.getID());
		provide.add(prov);
	}
	public void deleteList(List l) {
		if(l instanceof ProvideList) {
			provide.remove(l);
			dbManager.deleteL((ProvideList)l);//如果被預約、被領取要有不同的處理
		}else if(l instanceof ReserveList) {
			ProvideList p = dbManager.getPRsed((ReserveList)l);
			p.foodBack(((ReserveList)l).getFoods());
			reserve.remove(l);
			dbManager.deleteL((ReserveList)l);
		}else if(l instanceof ReceiveList) {
			receive.remove(l);
			dbManager.deleteL((ReceiveList)l);//把領取的食物的rcID設為null
		}
	}
	public void cancelReserve(ReserveList rs) {
		dbManager.deleteL(rs);
		reserve.remove(rs);
		ProvideList p = dbManager.getPRsed(rs);
		p.foodBack(rs.getFoods());
	}
	public void reserve(ArrayList<Food> food, ProvideList p) throws Exception{//點新增訂單之後
		p.foodTaken(food);
		for(int i=0; i<p.getFoods().size(); i++) {
			System.out.println("提供者剩下食物： "+p.getFoods().get(i).getName()+" "+p.getFoods().get(i).getAmount());
		}
		ReserveList r = new ReserveList(food, p.getGLocation(), p.getDLocation(), this.getID());
		System.out.println("reserveID: "+r.getID());
		for(int i=0; i<r.getFoods().size(); i++) {
			r.getFoods().get(i).setRsID(r.getID());
		}
		for(int i=0; i<p.getFoods().size(); i++) {
			if(p.getFoods().get(i).getID() == food.get(i).getID()) {
				p.getFoods().get(i).setRsID(food.get(i).getRSID());
			}
		}
		dbManager.updatePF(r.getFoods(), r.getID(),"rs");
		reserve.add(r);
	}
	public void receive(ReserveList rs) {
		dbManager.deleteL(rs);
		reserve.remove(rs);
		ReceiveList rc = new ReceiveList(rs.getFoods(), rs.getGLocation(), rs.getDLocation(), this.getID());
		for(int i=0; i<rc.getFoods().size(); i++) {
			rc.getFoods().get(i).setRsID(-1);
			rc.getFoods().get(i).setRcID(rc.getID());
			System.out.println("rc food name: "+rc.getFoods().get(i).getName()+"amount: "+rc.getFoods().get(i).getAmount()+"rsID: "+rc.getFoods().get(i).getRSID()+"rcID: "+rc.getFoods().get(i).getRCID()+"pID: "+rc.getFoods().get(i).getPID());
		}
		dbManager.updatePF(rc.getFoods(), rs.getID(),"rs");
		receive.add(rc);
	}
	public String getPassword() {
		return password;
	}
	public int getID() {
		return ID;
	}
	public ArrayList<ReserveList> getReserves() {
		return reserve;
	}
	public ArrayList<ReceiveList> getReceives() {
		return receive;
	}
	public ArrayList<ProvideList> getProvides() {
		return provide;
	}
	public ArrayList<List> getLists(int i) {
		ArrayList<List> l = new ArrayList<>();
		if(i==0) {
			for(ReserveList rs:reserve) {
				l.add(rs);
			}
		}else if(i==1) {
			for(ProvideList p:provide) {
				l.add(p);
			}
		}else if(i==2) {
			for(ReceiveList rc:receive) {
				l.add(rc);
			}
		}
		return l;
	}
}
class FoodError extends Exception{
	public FoodError(String error) {
		super(error);
	}
}