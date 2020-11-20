import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



public class Jellyday02
{
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	
	Scanner sc = new Scanner(System.in);
	
	
	Connection con = null; 
	PreparedStatement pstmt1;
	PreparedStatement pstmt2;
	PreparedStatement pstmt3;
	PreparedStatement pstmt4;
	PreparedStatement pstmt5;
	PreparedStatement pstmt6;
	PreparedStatement pstmt7;
	

	ResultSet rs = null;
	
	public static void main(String[] args)
	{
		
		Jellyday02 mpb = new Jellyday02();
		mpb.menuRun();
	}
	
	public void menuRun() {
		
		connectDatabase();
		int nChoice;
		
		while(true) {
			
			showMenu();
			nChoice =sc.nextInt();
			sc.nextLine();
			switch(nChoice) {
			case 1:
				addSchedule();
				break;
			case 2:
				selSchedule();
				break;
			case 3:
				allSelSchedule();
				break;
			case 4:
				delSchedule();
				break;
			case 5:
				selGuest();
				break;
			case 6:
			reTPlan();
				break;
			case 7:
				allselGuest();
				break;
			case 8:
				guestTirpPlan();
				break;
			case 9:
				System.out.println("프로그램을 종료합니다.");
				try {
					if (rs !=null) rs.close();
					if (pstmt1 !=null) pstmt1.close();
					if (pstmt2 !=null) pstmt2.close();
					if (pstmt2 !=null) pstmt3.close();
					if (pstmt2 !=null) pstmt4.close();
					if (pstmt2 !=null) pstmt5.close();
					if (pstmt2 !=null) pstmt6.close();
					if (con !=null) con.close();
					
				}catch(SQLException sqle) {
					sqle.printStackTrace();
				}
				return;	
				
			default:
				System.out.println("잘못 입력하셨습니다.");
				break;
			}
		}
	}

	public static void showMenu( ) {
	
		System.out.println("[메뉴 선택]");
		System.out.println("1. 항공 스케줄 등록");
		System.out.println("2. 항공 스케줄 조회");
		System.out.println("3. 항공 스케줄 전체 조회");
		System.out.println("4. 항공 스케줄 삭제");
		System.out.println("5. 고객 조회");
		System.out.println("6. 고객 TravelPlan 변경");
		System.out.println("7. 전체 리스트 조회");
		System.out.println("8. 여행계획 설정 ");
		System.out.println("9. 종료"); 
		
		System.out.print("선택 : ");
	}
	
	public void addSchedule() {
		
		System.out.print("도시 : ");
		String city = sc.nextLine();
		System.out.print("tNumber : ");
		String tNum  = sc.nextLine();
		System.out.print("출발기간 :");
		String Eff = sc.nextLine();
		System.out.print("최종기간 :");
		String Until = sc.nextLine();
		System.out.print("가격 :");
		String Price  = sc.nextLine();
		
		System.out.println("---------------------");
		
		String sql = "insert into ehscheldul values(?, ?, ?, ?, ?)";
		try {
			pstmt1 = con.prepareStatement(sql);
			pstmt1.setString(1, city);
			pstmt1.setString(2, tNum);
			pstmt1.setString(3, Eff);
			pstmt1.setString(4, Until);
			pstmt1.setString(5, Price);
			
			int updateCount = pstmt1.executeUpdate(); //데이터 정보를 업로드해준다. 
			System.out.println("데이터베이스에 추가완료");
			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void connectDatabase() {
		
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
														"scott",
														"tiger");
		}catch(Exception sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void selSchedule() {
		   //연락처 출력하기, 조회기능
		System.out.print("도시코드 : ");
		String citycode = sc.nextLine();
		System.out.println("---------------------");
		
			
		String sql = "select*from ehscheldul" +
					 " where citycode = ? ";
		try {
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setString(1, citycode);
			
			rs = pstmt2.executeQuery();
			if(rs.next()) {
				
				System.out.print( "도시코드 : "+ rs.getString(1) + '\t'  );
				System.out.print( "tNumber : " + rs.getString(2)+ '\t');
				System.out.print( "출발기간 : " + rs.getString(3) + '\t');
				System.out.print( "최종기간 : " + rs.getString(4) + '\t');
				System.out.print( "가격 : " + rs.getString(5));
				System.out.println();
				
				
			}else {
				System.out.println("해당 값이 없습니다.");
			}
			rs.close();
			
		}catch(Exception sqle) {
			System.out.println("알수 없는 에러가 발생했습니다.");
		}
	}
	
	public void allSelSchedule()  {
		String sql = "select* FROM ehscheldul" +
				 " order by tnum ";
		
		
		try
		{
			pstmt3 = con.prepareStatement(sql);
			rs = pstmt3.executeQuery();
			System.out.println("도시코드" + "\t" + "tNumber" + "\t\t" + "출발기간" + "\t"+ "최종기간" + "\t" +"가격");
			
			while(rs.next()) {
				System.out.print( rs.getString(1) + '\t');
				System.out.print( '\t'+ rs.getString(2)+ '\t');
				System.out.print( '\t'+rs.getString(3)+ '\t');
				System.out.print( rs.getString(4)+ '\t');
				System.out.print( rs.getString(5)+ '\t');
				System.out.println();
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void delSchedule() {
		System.out.print("삭제할 도시코드 : ");
		String citycode = sc.nextLine();
		
		
		String sql = "DELETE FROM ehscheldul" +
					 " where citycode = ? ";
		try {
			pstmt4 = con.prepareStatement(sql);
			pstmt4.setString(1, citycode);
			int updateCount = pstmt4.executeUpdate();
			System.out.println("항공 스케줄에서 삭제완료");
			
		}catch(SQLException sqle) {
			System.out.println("삭제 에러");
		}
	}
	
	public void selGuest() {
		System.out.print("회원성함 : ");
		String name = sc.nextLine();
		System.out.println("---------------------");
		
			
		String sql = "select*from guestinfo" +
					 " where 이름 = ? ";
		try {
			pstmt5 = con.prepareStatement(sql);
			pstmt5.setString(1, name);
			
			rs = pstmt5.executeQuery();
			System.out.println("회원번호" + "\t" + "이름" + "\t\t" + "국가/도시" + "\t"+ "희망출발/도착" + "\t\t" +"가격");
			if(rs.next()) {
				
				System.out.print(rs.getString(1) + '\t'  );
				System.out.print('\t' + rs.getString(2)+ '\t');
				System.out.print('\t' +rs.getString(3) );
				if(rs.getString(4) != null){
					System.out.print( "/"+ rs.getString(4)+ '\t');
				}else { System.out.print("/미정");}
//				System.out.print( rs.getString(4) + '\t');
				System.out.print('\t' + rs.getString(5));
				System.out.print( "/"+ rs.getString(6) + '\t');
				System.out.print(  rs.getString(7));
				System.out.println();
				
			
			}else {
				System.out.println("해당 값이 없습니다.");
			}
			System.out.println("-----------------------------------------");
			rs.close();
			
		}catch(Exception sqle) {
			System.out.println("알수 없는 에러가 발생했습니다.");
		}
	}
	
	public void allselGuest()  {
		String sql = "select* FROM guestinfo" +
				 " order by 이름 ";
		try
		{
			pstmt3 = con.prepareStatement(sql);
			rs = pstmt3.executeQuery();
			System.out.println("이름" + "\t" + "대륙/도시" + "\t\t" + "희망날짜/도착"  + "\t\t" +"가격");
			System.out.println("---------------------------------------------------------------");
			while(rs.next()) {
				
				System.out.print( rs.getString(2)+ '\t');
				System.out.print( rs.getString(3));
				System.out.print( "/" +rs.getString(4) + "\t");
				System.out.print(  '\t' + rs.getString(5));
				System.out.print(  "~" + rs.getString(6)+ '\t');
				System.out.print( rs.getString(7));
				System.out.println(); 
			}
			
			System.out.println("---------------------------------------------------------------");
			System.out.println();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void guestTirpPlan() {
		
		System.out.print("회원번호 : ");
		String menbership  = sc.nextLine();
		System.out.print("이름 : ");
		String name  = sc.nextLine();
		
		System.out.print("동북아시아, 동남아시아, 미주, 유럽 중 국가 택1 : ");
		String country = sc.nextLine();
		
		if(country.equals("동북아시아")) {
			System.out.print("상하이, 베이징, 홍콩, 대만, 오사카, 오키나와 중  도시 택1 :  ");
//			System.out.print("*택하지 않을 경우, 해당 도시의 가격을 모두 받음");
		}
		if(country.equals("동남아시아")) {
			System.out.print("방콕, 세부, 싱가포르 중  도시 택1  : ");
		}
		if(country.equals("미주")) {
			System.out.print("뉴욕, 샌프란시스코, 벤쿠버 중  도시 택1 :  ");
		}
		if(country.equals("유럽")) {
			System.out.print("파리 , 바르셀로나, 로마, 런던, 제네바, 프라하 중  도시 택1 : ");
		}
		String city = sc.nextLine();
		System.out.print("희망출발 : ");
		String apart  = sc.nextLine();
		System.out.print("희망도착 : ");
		String depart  = sc.nextLine();
		System.out.print("가격 : ");
		String price  = sc.nextLine();
		
		System.out.println("-----------------------------------------");
		
		String sql = "insert into guestinfo values(?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt5 = con.prepareStatement(sql);
			pstmt5.setString(1, menbership);
			pstmt5.setString(2, name);
			pstmt5.setString(3, country);
			pstmt5.setString(4, city);
			pstmt5.setString(5, apart);
			pstmt5.setString(6, depart);
			pstmt5.setString(7, price);
			
			
			int updateCount = pstmt5.executeUpdate(); //데이터 정보를 업로드해준다. 
			System.out.println("데이터베이스에 추가완료");
			System.out.println();
			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
	}
	
	public void reTPlan() {
		
		System.out.print(" 변경할 고객 이름을 입력하세요. : " );
		String guestname = sc.nextLine();
		System.out.print(" 변경 내용을 입력하세요(도시or가격) : ");
		String changecol = sc.nextLine();
		System.out.print( changecol+"의 변경 내용을 입력하세요. : ");
		String content = sc.nextLine();
		System.out.println("-------------------------------------");
		

		if (changecol.equals("도시")) {
		
			try
			{
				String sql = "UPDATE guestinfo"
						+ " set "
						+ " 도시 = ? " 
						+ " where 이름 =  ? ";
				
				PreparedStatement pstmt6 = con.prepareStatement(sql);
				
				pstmt6.setString(1, content);
				pstmt6.setString(2, guestname);
				pstmt6.executeUpdate(); 
				System.out.println("도시변경이 완료되었습니다.");
				
			} catch (SQLException e)
			{
				e.printStackTrace();
				System.out.println(" 도시 변경 에러");
			}
		}
		else {
			try
			{
				String sql = "UPDATE guestinfo"
						+ " set "
						+ " 가격 = ? " 
						+ " where 이름 =  ? ";
				
				PreparedStatement pstmt6 = con.prepareStatement(sql);
				
				pstmt6.setString(1, content);
				pstmt6.setString(2, guestname);
				pstmt6.executeUpdate(); 
				System.out.println(" 가격변경이 완료되었습니다.");
				
			} catch (SQLException e)
			{
				e.printStackTrace();
				System.out.println("변경 에러");
			}
		}
	}
}
// 다음주 추가 메소드 *****
/*푸시알림 설정 및 손님의 가격과 항공권 가격 일치 관련 --> 파일을 저장할 수 있도록하기 ?????  메소드 추가 */ 
/* 희망 날짜가 지난 고객 명단의 자동으로 삭제되고, 푸시 알림으로 고객에게 다시 설정 제안하기 */

