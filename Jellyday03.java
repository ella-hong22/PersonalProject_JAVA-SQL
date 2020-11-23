import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Jellyday03
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
	PreparedStatement pstmt8;
	ResultSet rs = null ;
	
	public static void main(String[] args)
	{
		
		Jellyday03 mpb = new Jellyday03();
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
				delSchedule();
				break;
			case 4:
				detailSchedule();
				break;
			case 5:
				selGuest();
				break;
			case 6:
				needsMeet();
				break;
			case 7:
				detailSchedule();
				break;
			case 8:
				reTPlan();
				break;
			case 9:
				guestTirpPlan();
				break;
			case 10:
				System.out.println("프로그램을 종료합니다.");
				try {
					if (rs !=null) rs.close();
					if (pstmt1 !=null) pstmt1.close();
					if (pstmt2 !=null) pstmt2.close();
					if (pstmt3 !=null) pstmt3.close();
					if (pstmt4 !=null) pstmt4.close();
					if (pstmt5 !=null) pstmt5.close();
					if (pstmt6 !=null) pstmt6.close();
					if (pstmt7 !=null) pstmt7.close();
					if (pstmt8 !=null) pstmt8.close();
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
	
		System.out.println("   [메뉴 선택]  ");
		System.out.println(" -- 항공 스케줄 관리 -- ");
		System.out.println(" 1. 항공 스케줄 등록");
		System.out.println(" 2. 항공 스케줄 조회 or 전체조회");
		System.out.println(" 3. 항공 스케줄 삭제");
		System.out.println(" 4. 항공 스케줄 세부사항");
		System.out.println(" --  고객  관리 --  ");
		System.out.println(" 5. 고객 조회 or 전체조회");
		System.out.println(" 6. Needs 충족 고객 리스트 확인 ");
		System.out.println(" 7. 기간 만료된 스케줄 삭제");
		System.out.println(" --  그 외 기능  --  ");
		System.out.println(" 8. 여행계획 설정 ");
		System.out.println(" 9. 고객 TravelPlan 변경 ");
		System.out.println(" 10. 종료 ");
		System.out.println();
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
			Statement stmt = con.createStatement();
		}catch(Exception sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void selSchedule() {
		   //연락처 출력하기, 조회기능
		System.out.print("전체조회 or 개별조회 : ");
		String choice = sc.nextLine();
		
		if(choice.equals("개별조회")) {
			System.out.print("도시 : ");
			String city = sc.nextLine();
			System.out.println("---------------------");
			
			String sql = "select*from ehscheldul" +
						 " where city = ? ";
			try {
				pstmt2 = con.prepareStatement(sql);
				pstmt2.setString(1, city);
				
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
				System.out.println("해당 도시의 항공스케줄이 존재하지 않습니다.");
			}
		}
		if(choice.equals("전체조회")) {
			String sql = "select* FROM ehscheldul" +
//					"where to_char(eff, 'yy/mm/dd') as eff, to_char(untill, 'yy/mm/dd') as untill" +
					 " order by tnum ";
			
			try
			{
				pstmt3 = con.prepareStatement(sql);
				rs = pstmt3.executeQuery();
				System.out.println("도시" + "\t" + "tNumber" + "\t\t" + "출발기간" + "\t"+ "최종기간" + "\t" +"가격");
				
				while(rs.next()) {
					System.out.print( rs.getString(1) + '\t');
					System.out.print( rs.getString(2)+ '\t');
					System.out.print( '\t' + rs.getString(3)+ '\t');
					System.out.print( rs.getString(4)+ '\t');
					System.out.print( rs.getString(5)+ '\t');
					System.out.println();
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			
		}
		
	}
	
		
	public void delSchedule() {
		System.out.print("삭제할 도시 : ");
		String city = sc.nextLine();
		
		String sql = "DELETE FROM ehscheldul" +
					" where city = ? ";
		
		try {
			 
			
			pstmt4 = con.prepareStatement(sql);
			pstmt4.setString(1, city);
			int updateCount = pstmt4.executeUpdate();
			System.out.println("총" + updateCount +" 개 삭제완료");
			
		}catch(SQLException sqle) {
			System.out.println("해당도시의 스케줄이 존재하지 않습니다. 다시시도해주세요. ");
			
		}
	}
	
	public void selGuest() {
		
		System.out.print("고객별조회 or 전체조회 : ");
		String choice = sc.nextLine();
		
		if(choice.equals("고객별조회")) {
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
		if(choice.equals("전체조회")) {
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
		System.out.print("재설정 푸시알람받기(Y/N) : ");
		String repush  = sc.nextLine();
		
		System.out.println("-----------------------------------------");
		
		String sql = "insert into guestinfo values(?, ?, ?, ?, ?, ?, ?,?)";
		try {
			pstmt5 = con.prepareStatement(sql);
			pstmt5.setString(1, menbership);
			pstmt5.setString(2, name);
			pstmt5.setString(3, country);
			pstmt5.setString(4, city);
			pstmt5.setString(5, apart);
			pstmt5.setString(6, depart);
			pstmt5.setString(7, price);
			pstmt5.setString(8, repush);
			
			
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
	
	public void needsMeet() {
		System.out.println();
		System.out.println("[고객 Needs에 충족 명단]");
		System.out.println();
		String sql = "select  b.city, c.이름 , c.희망날짜, c.도착날짜, c.희망가격, b.price"
				+ " from  ehscheldul b , guestinfo c "
				+ " where b.city = c.희망도시 " 
				+ "  and c.희망날짜 >= b.eff  "
				+ "  and c.도착날짜 <= b.untill  "
				+ "  and b.city = c.희망도시 "
				+ "  and c.희망가격 <= price ";
		try
		{
			pstmt7 = con.prepareStatement(sql);
			rs = pstmt7.executeQuery();
			System.out.println( "고객성함" + "\t" + "희망출발/도착날짜"  + "\t\t" +"고객희망가격" + "\t" +"도시" + "\t\t" +"항공권가격");
			System.out.println("--------------------------------------------------------------------------------------------");
			while(rs.next()) {
				
				System.out.print( rs.getString(2)+ '\t');
				System.out.print( '\t'+rs.getString(3));
				System.out.print( "~" +rs.getString(4) + "\t");
				System.out.print(  '\t' + rs.getString(5)+ '\t');
				System.out.print(  '\t'+rs.getString(1)+ '\t');
				System.out.print(  '\t'+rs.getString(6));
				System.out.println(); 
			}
			System.out.println("--------------------------------------------------------------------------------------------");
			System.out.println("해당 고객에게 푸시알림 성공!!");
			System.out.println();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println(" 도시 변경 에러");
		}
		
		
	}
	public void detailSchedule() {
		 
		try
		{
			String sql = "select a.도시, a.출발도시, a.도착도시, a.도시코드, b.tnum, b.eff, b.untill, b.price"
					+ " from cityinfo a, ehscheldul b "
					+ "  where a.tnum = b.tnum  ";
			
			pstmt8 = con.prepareStatement(sql);
			rs = pstmt8.executeQuery();
			System.out.println( "지역" + "\t\t" + "출발/도착도시"  + "\t\t" +"도시코드" + "\t" +"TNUM" +"\t"+"EFF/UNTILL" + "\t\t" +"PRICE");
			System.out.println(" ----------------------------------------------------------------------------------------------");
			while(rs.next()) {
				
				System.out.print( "|"+ rs.getString(1)+ '\t');
				System.out.print( rs.getString(2));
				System.out.print( "/" + rs.getString(3)+'\t');
				System.out.print( rs.getString(4) + "\t");
				System.out.print(  '\t' + rs.getString(5)+ '\t');
				System.out.print(  rs.getString(6));
				System.out.print(  '~'+rs.getString(7));
				System.out.print(  '\t'+rs.getString(8)+"|");
				System.out.println(); 
			}
			System.out.println(" ----------------------------------------------------------------------------------------------");
			System.out.println();
			
			
		} catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println(" 도시 변경 에러");
		}
	}
	
	public void delFunction() {
		
		
		System.out.print(" 날짜 지난 리스트 삭제 (항공스케줄 or 고객스케줄 중 택1) : " );
		String delname = sc.nextLine();
		System.out.println();
		
		if(delname.equals("항공스케줄")) {
			
		 
			String sql = "delete from ehscheldul"
					+ " where untill < sysdate  " ;
					
			try
			{
				pstmt7 = con.prepareStatement(sql);
				int updateCount = pstmt7.executeUpdate();
				System.out.println(" 총 " + updateCount + "개의 항공 스케줄 리스트가 삭제되었습니다. ");
				
				
			} catch (SQLException e)
			{
				e.printStackTrace();
				System.out.println(" 삭제 에러");
			}
		}	

		if(delname.equals("고객스케줄")) {
			
			try
			{
				String sql = "select* from guestinfo"
						+ " where 희망날짜 < sysdate + 7  " ;
				pstmt7 = con.prepareStatement(sql);
				int updateCount = pstmt7.executeUpdate();
				System.out.print("총" + updateCount+"의 삭제고객리스트와 ");
				
				rs = pstmt7.executeQuery();
				String push = null ; 
				Object i = null ;
				
				while(rs.next()) {
					push = rs.getString(8);
					if( push.equals("Y")) {
						i =+ 1;
					}
				}
				System.out.println( "총 " + i +"명의 날짜 재설정 푸시알림이 있습니다. ");
					
			} catch (SQLException e)
			{
				e.printStackTrace();
				System.out.println("삭제 에러");
			}
			
			try {
				String sql = "delete from guestinfo"
						+ " where 희망날짜 < sysdate + 7  " ;
				pstmt7 = con.prepareStatement(sql);
				pstmt7.executeUpdate();
				System.out.println("삭제 및 푸시알림 완료!!");
				System.out.println();
				
			}catch (SQLException e)
			{
				e.printStackTrace();
				System.out.println(" 삭제 에러");
			}
		}		
	}
}
// 다음주 추가 메소드 *****
/*항공금액에 따른 좌석 클레스 세부사항 추가하기 */ 

