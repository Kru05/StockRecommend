package hello.vo;

import java.math.BigDecimal;

public class Stock {
	String Code    ;
	String Date    ;  
	Double  Open   ;
	Double  High   ;
	Double  Low    ;
	Double   Last ;
	Double Close   ;
	 BigDecimal Total_Trade_Quantity ;
	 BigDecimal Turnover;
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public Double getOpen() {
		return Open;
	}
	public void setOpen(Double open) {
		Open = open;
	}
	public Double getHigh() {
		return High;
	}
	public void setHigh(Double high) {
		High = high;
	}
	public Double getLow() {
		return Low;
	}
	public void setLow(Double low) {
		Low = low;
	}
	public Double getLast() {
		return Last;
	}
	public void setLast(Double last) {
		Last = last;
	}
	public Double getClose() {
		return Close;
	}
	public void setClose(Double close) {
		Close = close;
	}
	public BigDecimal getTotal_Trade_Quantity() {
		return Total_Trade_Quantity;
	}
	public void setTotal_Trade_Quantity(BigDecimal total_Trade_Quantity) {
		Total_Trade_Quantity = total_Trade_Quantity;
	}
	public BigDecimal getTurnover() {
		return Turnover;
	}
	public void setTurnover(BigDecimal turnover) {
		Turnover = turnover;
	}

}
