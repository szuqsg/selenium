
public class vmtVO {
	String seqno;
	
	//use the color to display the container status; 
	//01:grey; 02:green; 03:orange;
	String status;

	String bay;
	String row;
	String tier;
	String position;
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	
	public String getSeqno() {
		return seqno;
	}
	
	public void setBay(String bay) {
		this.bay = bay;
	}
	
	public String getBay() {
		return bay;
	}
	
	public void setRow(String row) {
		this.row = row;
	}
	
	
	public String getRow() {
		return row;
	}
	
	public void setTier(String tier) {
		this.tier = tier;
	}
	
	public String getTier() {
		return tier;
	}
	
}
